package example.ai.application.api;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import example.ai.application.model.TestResult;
import example.ai.application.service.TestingService;
import example.ai.core.model.DrawingDataEntry;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path(TestingAPI.PATH)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class TestingAPI {
	
	protected static final String PATH = "testing";
	
	private final TestingService testingService;
	
	@Inject
	public TestingAPI(final TestingService testingService) {
		this.testingService = testingService;
	}
	
	@POST
	public TestResult testingDrawing(final DrawingDataEntry drawing) {
		return testingService.testDrawing(drawing);
	}

}
