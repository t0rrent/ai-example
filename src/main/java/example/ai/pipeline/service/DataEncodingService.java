package example.ai.pipeline.service;

import java.io.IOException;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import example.ai.core.model.DrawingData;
import example.ai.core.model.DrawingDataEntry;

public interface DataEncodingService {

	DrawingData decodeDrawing(DrawingDataEntry drawingDataEntry);

	String base64EncodedModel(MultiLayerNetwork model) throws IOException;

	MultiLayerNetwork base64DecodedModel(String encodedModel) throws IOException;

}
