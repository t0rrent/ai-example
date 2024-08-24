package example.ai.pipeline.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import example.ai.core.model.DrawingData;
import example.ai.core.model.DrawingDataEntry;

public class SimpleDataEncodingService implements DataEncodingService {
	
	@Override
	public DrawingData decodeDrawing(final DrawingDataEntry drawingDataEntry) {
		final DrawingData drawingData = new DrawingData();
		drawingData.setCharacter(drawingDataEntry.getCharacter());
		
		final List<Boolean> decodedBitArray = new ArrayList<>();
		final List<Boolean> encodedBitArray = drawingDataEntry.getCompressedDrawing();
		for (int groupIndex = 0; groupIndex < encodedBitArray.size() / 6; groupIndex++) {
			final boolean value = encodedBitArray.get(groupIndex * 6);
			int runSize = 1;
			int factor = 1;
			for (int power = 0; power < 5; power++) {
				if (encodedBitArray.get(groupIndex * 6 + power + 1)) {
					runSize += factor;
				}
				factor *= 2;
			}
			for (int runIndex = 0; runIndex < runSize; runIndex++) {
				decodedBitArray.add(value);
			}
		}
		drawingData.setDrawing(decodedBitArray);
		
		return drawingData;
	}

	@Override
	public String base64EncodedModel(MultiLayerNetwork model) throws IOException {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ModelSerializer.writeModel(model, outputStream, false);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}

	@Override
	public MultiLayerNetwork base64DecodedModel(String encodedModel) throws IOException {
        final byte[] asByteArray = Base64.getDecoder().decode(encodedModel);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(asByteArray);
        return ModelSerializer.restoreMultiLayerNetwork(inputStream, false);
	}

}
