package example.ai.application.api;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import example.ai.application.service.AuthService;
import example.ai.application.service.ContributionService;
import example.ai.core.model.DrawingDataEntry;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;

@Path(ContributionAPI.PATH)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ContributionAPI {
	
	protected static final String PATH = "contribution";
	
	private final ContributionService contributionService;
	
	private final AuthService authService;
		
	@Inject
	public ContributionAPI(final ContributionService contributionService, final AuthService auditService) {
		this.contributionService = contributionService;
		this.authService = auditService;
	}
	
	@POST
	public void commitDrawing(@Context final HttpServletRequest request, final DrawingDataEntry drawing) {
		final long contributorId = authService.getContributor(request.getRemoteAddr());
		contributionService.commitDrawing(drawing, contributorId);
		authService.auditRecentDrawings(contributorId, contributionService.getContributions(contributorId, 0, 5));
	}
	
}
