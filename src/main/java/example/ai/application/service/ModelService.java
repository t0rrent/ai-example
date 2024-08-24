package example.ai.application.service;

import example.ai.core.model.ModelCommit;

public interface ModelService {

	void commit(String model, double f1Score);

	ModelCommit getBestModel();

}
