package example.ai.mapper;

import java.util.Collection;

import example.ai.core.model.DrawingDataEntry;

public interface DataRetrievalMapper {

	Collection<DrawingDataEntry> getTrainingData();

}
