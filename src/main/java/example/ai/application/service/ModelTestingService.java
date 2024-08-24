package example.ai.application.service;

import java.io.IOException;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.ai.application.model.TestResult;
import example.ai.core.model.DrawingData;
import example.ai.core.model.DrawingDataEntry;
import example.ai.core.model.ModelCommit;
import example.ai.core.service.LifeCycle;
import example.ai.pipeline.service.DataEncodingService;
import jakarta.inject.Inject;

public class ModelTestingService implements TestingService, LifeCycle {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelTestingService.class);

	private final ModelService modelService;
	
	private final DataEncodingService dataEncodingService;
	
	private MultiLayerNetwork model;

	private Double bestScore;
	
	@Inject
	public ModelTestingService(final ModelService modelService, final DataEncodingService dataEncodingService) {
		this.modelService = modelService;
		this.dataEncodingService = dataEncodingService;
	}

	@Override
	public synchronized TestResult testDrawing(final DrawingDataEntry drawing) {
		if (model == null) {
			final TestResult testResult = new TestResult();
			testResult.setResult(' ');
			return testResult;
		}
		
		final double[] asVector = getAsVector(dataEncodingService.decodeDrawing(drawing));
		final INDArray result = model.output(Nd4j.create(asVector, new int[] {1, asVector.length}));
		final double[] resultVector = result.data().asDouble();
		
		Character bestResult = null;
		Double resultStrength = null;
		for (int vectorIndex = 0; vectorIndex < resultVector.length; vectorIndex++) {
			if (resultStrength == null || resultVector[vectorIndex] > resultStrength) {
				bestResult = (char) ('A' + vectorIndex);
				resultStrength = resultVector[vectorIndex];
			}
		}
		final TestResult testResult = new TestResult();
		testResult.setResult(bestResult);
		return testResult;
	}

	@Override
	public void start() {
		loadBestModel();
	}

	@Override
	public void stop() {
	}
	
	@Override
	public void onNewModelAvailable(final double score) {
		if (bestScore == null || bestScore < score) {
			loadBestModel();
		}
	}
	
	private synchronized void loadBestModel() {
		final ModelCommit encodedModel = modelService.getBestModel();
		if (encodedModel != null) {
	        try {
				model = dataEncodingService.base64DecodedModel(encodedModel.getModel());
				bestScore = encodedModel.getF1Score();
			} catch (final IOException exception) {
				LOGGER.error("Error loading model", exception);
			}
		} else {
			LOGGER.warn("No applicable models exist");
		}
	}

	private double[] getAsVector(final DrawingData drawingData) {
		final double[] vector = new double[drawingData.getDrawing().size()];
		for (int index = 0; index < vector.length; index++) {
			vector[index] = drawingData.getDrawing().get(index) ? 1 : 0;
		}
		return vector;
	}

}
