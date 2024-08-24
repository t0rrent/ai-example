package example.ai.pipeline.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.Pair;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.ai.application.service.ModelService;
import example.ai.core.model.DrawingData;
import example.ai.core.service.LifeCycle;
import jakarta.inject.Inject;

public class TrainingLifeCycle implements LifeCycle {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingLifeCycle.class);

	private static final int CLASSES_COUNT = 26;

	private static final int BATCH_SIZE = 1000;
	
	private static final double TRAINING_TO_TESTING_RATIO = 0.8;

	private static final int EPOCHS = 50;
	
	private final DataEncodingService dataEncodingService;
	
	private final DataRetrievalService dataRetrievalService;
	
	private final MultiLayerConfiguration multiLayerConfiguration;
	
	private final ScheduledExecutorService scheduledExecutorService;
	
	private final TrainingEventService trainingEventService;
	
	private final ModelService modelService;
	
	private final Random random;
	
	private MultiLayerNetwork model;

	private ScheduledFuture<?> schedule;
	
	@Inject
	public TrainingLifeCycle(
			final DataEncodingService dataService,
			final ScheduledExecutorService scheduledExecutorService,
			final MultiLayerConfiguration multiLayerConfiguration,
			final DataRetrievalService dataRetrievalService,
			final ModelService modelService,
			final TrainingEventService trainingEventService
	) {
		this.dataEncodingService = dataService;
		this.scheduledExecutorService = scheduledExecutorService;
		this.multiLayerConfiguration = multiLayerConfiguration;
		this.dataRetrievalService = dataRetrievalService;
		this.modelService = modelService;
		this.trainingEventService = trainingEventService;
		random = new Random();
	}

	@Override
	public void start() {
		initModel();
        schedule(0);
	}

	@Override
	public synchronized void stop() {
		if (schedule != null) {
			schedule.cancel(true);
		}
	}

	private void initModel() {
        model = new MultiLayerNetwork(multiLayerConfiguration);
        model.init();
	}

	private Pair<List<DataSet>, List<DataSet>> createTrainingBatches(final List<DrawingData> trainingDataSet, final int batchSize) {
		final List<DataSet> trainingBatches = new ArrayList<>();
		final List<DataSet> testingBatches = new ArrayList<>();
		final List<DrawingData> scrambledTrainingDataSet = randomizeAndUniform(trainingDataSet);
		final int batchCount = (int) Math.ceil(scrambledTrainingDataSet.size() * 1.0 / batchSize);
		
		for (int batchIndex = 0; batchIndex < batchCount; batchIndex++) {
			final int thisBatchSize = batchIndex == batchCount - 1 ? scrambledTrainingDataSet.size() - batchIndex * batchSize : batchSize;
			final double[][] inputs = new double[thisBatchSize][];
			final double[][] expectedOutputs = new double[thisBatchSize][];
			for (int setIndex = 0; setIndex < thisBatchSize; setIndex++) {
				final DrawingData drawing = scrambledTrainingDataSet.get(setIndex + batchIndex * batchSize);
				final double[] input = new double[drawing.getDrawing().size()];
				for (int vectorIndex = 0; vectorIndex < drawing.getDrawing().size(); vectorIndex++) {
					input[vectorIndex] = drawing.getDrawing().get(vectorIndex) ? 1 : 0;
				}
				inputs[setIndex] = input;
				expectedOutputs[setIndex] = getResultVector(drawing.getCharacter());
			}
			final DataSet batch = new DataSet(Nd4j.create(inputs), Nd4j.create(expectedOutputs));
			if (random.nextDouble() < TRAINING_TO_TESTING_RATIO) {
				trainingBatches.add(batch);
			} else {
				testingBatches.add(batch);
			}
		}
		
		return Pair.create(trainingBatches, testingBatches);
	}

	private List<DrawingData> randomizeAndUniform(final List<DrawingData> trainingDataSet) {
		final Map<Character, List<DrawingData>> characterBasedMap = new HashMap<>();
		Integer smallestList = null;
		for (int charInteger = 'A'; charInteger <= 'Z'; charInteger++) {
			final int charIntegerFinal = charInteger;
			final List<DrawingData> list = new ArrayList<>(trainingDataSet.stream()
					.filter((drawing) -> drawing.getCharacter() == (char) charIntegerFinal)
					.toList());
			characterBasedMap.put((char) charInteger, list);
			if (smallestList == null || smallestList > list.size()) {
				smallestList = list.size();
			}
		}
		
		final List<DrawingData> result = new ArrayList<>();
		for (int alphabetCycle = 0; alphabetCycle < smallestList; alphabetCycle++) {
			for (int charInteger = 'A'; charInteger <= 'Z'; charInteger++) {
				final List<DrawingData> list = characterBasedMap.get((char) charInteger);
				result.add(list.remove(random.nextInt(list.size())));
			}
		}
		return result;
		
	}

	private double[] getResultVector(final char character) {
		final int outputIndex = Character.toUpperCase(character) - 'A';
		final double[] expected = new double[26];
		expected[outputIndex] = 1;
		return expected;
	}

	private synchronized void schedule(final int delayMinutes) {
		schedule = scheduledExecutorService.schedule(handleErrors(this::train), delayMinutes, TimeUnit.MINUTES);
	}
	
	private Runnable handleErrors(final Runnable action) {
		return () -> {
			try {
				action.run();
			} catch (final Throwable throwable) {
				LOGGER.error("Error during training", throwable);
			}
		};
	}

	private void train() {
		final Pair<List<DataSet>, List<DataSet>> trainingData = createTrainingBatches(getTrainingData(), BATCH_SIZE);
		final List<DataSet> trainingBatches = trainingData.getFirst();
		final List<DataSet> testingBatches = trainingData.getSecond();

		LOGGER.debug("Beginning training session of " + trainingBatches.size() + " batches in " + EPOCHS + " epochs");
		for (int epoch = 1; epoch <= EPOCHS; epoch++) {
			int batchIndex = 0;
			for (final DataSet batch : trainingBatches) {
				model.fit(batch);
				batchIndex++;
				if (batchIndex % 10 == 0 || batchIndex == trainingBatches.size()) {
					LOGGER.debug("Finished fitting batch number " + batchIndex + " of " + trainingBatches.size());
				}
			}
			LOGGER.debug("Finished epoch " + epoch + " of " + EPOCHS + ", model currently has an f1 score of " + evaluateModel(testingBatches));
		}
			
		try {
			final double score = evaluateModel(testingBatches);
			modelService.commit(dataEncodingService.base64EncodedModel(model), score);
			trainingEventService.pushNewModelEvent(score);
		} catch (final IOException exception) {
			LOGGER.error("Error committing model", exception);
		}
		schedule(60);
	}

	private double evaluateModel(final List<DataSet> testData) {
        final Evaluation evaluation = new Evaluation(CLASSES_COUNT);
		LOGGER.debug("Running evaluation");
		for (final DataSet testBatch : testData) {
	        final INDArray output = model.output(testBatch.getFeatures());
	        evaluation.eval(testBatch.getLabels(), output);
		}
		return evaluation.f1();
	}

	private List<DrawingData> getTrainingData() {
		return dataRetrievalService.getCompressedTrainingDataSet()
				.stream()
				.map(dataEncodingService::decodeDrawing)
				.toList();
	}

}
