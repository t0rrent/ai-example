package example.ai.pipeline.hk2.factory;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.glassfish.hk2.api.Factory;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class MultiLayerConfigurationFactory implements Factory<MultiLayerConfiguration> {

	@Override
	public MultiLayerConfiguration provide() {
		return new NeuralNetConfiguration.Builder()
	            .updater(new Adam())
	            .weightInit(WeightInit.XAVIER)
	            .list()
	            .layer(0, new ConvolutionLayer.Builder(5, 5)
	                .nIn(1)
	                .nOut(6)
	                .stride(1, 1)
	                .activation(Activation.IDENTITY)
	                .build())
	            .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.AVG)
	                .kernelSize(2, 2)
	                .stride(2, 2)
	                .build())
	            .layer(2, new ConvolutionLayer.Builder(5, 5)
	                .nOut(16)
	                .stride(1, 1)
	                .activation(Activation.IDENTITY)
	                .build())
	            .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.AVG)
	                .kernelSize(2, 2)
	                .stride(2, 2)
	                .build())
	            .layer(4, new ConvolutionLayer.Builder(1, 1)
	                .nOut(120)
	                .activation(Activation.RELU)
	                .build())
	            .layer(5, new DenseLayer.Builder()
	                .nOut(84)
	                .activation(Activation.RELU)
	                .build())
	            .layer(6, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
	                .nOut(26)
	                .activation(Activation.SOFTMAX)
	                .build())
	            .setInputType(InputType.convolutionalFlat(32, 32, 1))
	            .build();
	}

	@Override
	public void dispose(final MultiLayerConfiguration instance) {
	}

}
