package example.ai.pipeline.service;

import java.util.Collection;

import example.ai.core.model.DrawingDataEntry;
import example.ai.mapper.DataRetrievalMapper;
import jakarta.inject.Inject;

public class DataRetrievalMapperService implements DataRetrievalService {
	
	private final DataRetrievalMapper dataRetrievalMapper;
	
	@Inject
	public DataRetrievalMapperService(final DataRetrievalMapper dataRetrievalMapper) {
		this.dataRetrievalMapper = dataRetrievalMapper;
	}

	@Override
	public Collection<DrawingDataEntry> getCompressedTrainingDataSet() {
		return dataRetrievalMapper.getTrainingData();
	}

}
