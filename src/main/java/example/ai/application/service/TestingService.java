package example.ai.application.service;

import example.ai.application.model.TestResult;
import example.ai.core.model.DrawingDataEntry;

public interface TestingService {

	TestResult testDrawing(DrawingDataEntry drawing);

	void onNewModelAvailable(double score);

}
