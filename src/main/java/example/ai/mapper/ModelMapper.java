package example.ai.mapper;

import example.ai.core.model.ModelCommit;

public interface ModelMapper {

	void insert(ModelCommit modelCommit);

	ModelCommit getBestModel();

}
