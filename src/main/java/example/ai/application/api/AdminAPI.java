package example.ai.application.api;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import example.ai.application.model.Contribution;
import example.ai.application.model.Contributor;
import example.ai.application.service.AuthService;
import example.ai.application.service.ContributionService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@Path(AdminAPI.PATH)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AdminAPI {
	
	protected static final String PATH = "admin";
	
	private final AuthService authService;
	
	private final ContributionService contributionService;
	
	@Inject
	public AdminAPI(final AuthService authService, final ContributionService contributionService) {
		this.authService = authService;
		this.contributionService = contributionService;
	}
	
	@GET
	@Path("contributors")
	public List<Contributor> getContributors(@Context final HttpServletRequest request, @QueryParam("skip") Long skip, @QueryParam("limit") Long limit) {
		authService.checkAuthorization(request.getHeader(HttpHeaders.AUTHORIZATION));
		if (skip == null) {
			skip = 0l;
		}
		if (limit == null) {
			limit = 100l;
		}
		return contributionService.getContributors(skip, limit);
	}
	
	@GET
	@Path("contributor-count")
	public long getContributorCount(@Context final HttpServletRequest request) {
		authService.checkAuthorization(request.getHeader(HttpHeaders.AUTHORIZATION));
		return contributionService.getContributorCount();
	}
	
	@GET
	@Path("audit-contributions")
	public List<Contribution> getContributions(
			@Context final HttpServletRequest request,
			@QueryParam("contributor-id") final long contributorId,
			@QueryParam("skip") Long skip,
			@QueryParam("limit") Long limit
	) {
		if (skip == null) {
			skip = 0l;
		}
		if (limit == null) {
			limit = 100l;
		}
		authService.checkAuthorization(request.getHeader(HttpHeaders.AUTHORIZATION));
		return contributionService.getContributions(contributorId, skip, limit);
	}
	
	@GET
	@Path("ban")
	public Contributor banContributor(
			@Context final HttpServletRequest request,
			@QueryParam("contributor-id") final long contributorId
	) {
		authService.checkAuthorization(request.getHeader(HttpHeaders.AUTHORIZATION));
		authService.banContributor(contributorId);
		return contributionService.getContributorById(contributorId);
	}
	
	@GET
	@Path("unban")
	public Contributor unbanContributor(
			@Context final HttpServletRequest request,
			@QueryParam("contributor-id") final long contributorId
	) {
		authService.checkAuthorization(request.getHeader(HttpHeaders.AUTHORIZATION));
		authService.unbanContributor(contributorId);
		return contributionService.getContributorById(contributorId);
	}
	
}
