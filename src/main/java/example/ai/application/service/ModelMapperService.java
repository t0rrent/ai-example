package example.ai.application.service;

import example.ai.core.model.ModelCommit;
import example.ai.mapper.ModelMapper;
import jakarta.inject.Inject;

public class ModelMapperService implements ModelService {
	
	private final ModelMapper modelMapper;
	
	@Inject
	public ModelMapperService(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public void commit(final String model, final double f1Score) {
		final ModelCommit modelCommit = new ModelCommit();
        modelCommit.setModel(model);
        modelCommit.setF1Score(f1Score);
		modelMapper.insert(modelCommit);
	}

	@Override
	public ModelCommit getBestModel() {
		return modelMapper.getBestModel();
	}

}
