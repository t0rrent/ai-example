package example.ai.pipeline.service;

import java.util.Collection;

import example.ai.core.model.DrawingDataEntry;

public interface DataRetrievalService {

	Collection<DrawingDataEntry> getCompressedTrainingDataSet();

}
