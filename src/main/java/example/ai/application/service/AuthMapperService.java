package example.ai.application.service;

import java.util.List;

import example.ai.application.model.Contribution;
import example.ai.application.model.SetBanParam;
import example.ai.mapper.AuthMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

public class AuthMapperService implements AuthService {
	
	private static final int ILLEGAL_CONTRIBUTION_PERIOD = 200;
	private final AuthMapper authMapper;
	
	@Inject
	public AuthMapperService(final AuthMapper authMapper) {
		this.authMapper = authMapper;
	}
	

	@Override
	public long getContributor(final String ipAddress) {
		if (authMapper.isContributorBanned(ipAddress)) {
			throw new NotAuthorizedException("You have been banned from contributing to this service.");
		} else {
			Long maybeId = authMapper.getContributorByIpAddress(ipAddress);
			if (maybeId == null) {
				return authMapper.createContributor(ipAddress);
			} else {
				return maybeId;
			}
		}
	}


	@Override
	public void checkAuthorization(final String authToken) {
		if (authToken == null
				|| !authToken.startsWith("Bearer ")
				|| !authMapper.checkAuthorization(authToken.substring("Bearer".length()).trim())) {
			throw new NotAuthorizedException("Invalid authorization");
		}
	}


	@Override
	public void banContributor(final long contributorId) {
		final SetBanParam setBanParam = new SetBanParam();
		setBanParam.setContributorId(contributorId);
		setBanParam.setNewStatus(true);
		authMapper.setBanStatus(setBanParam);
	}


	@Override
	public void unbanContributor(final long contributorId) {
		final SetBanParam setBanParam = new SetBanParam();
		setBanParam.setContributorId(contributorId);
		setBanParam.setNewStatus(false);
		authMapper.setBanStatus(setBanParam);
	}


	@Override
	public void auditRecentDrawings(final long contributorId, final List<Contribution> recentContributions) {
		Long minTime = null;
		Long maxTime = null;
		for (final Contribution contribution : recentContributions) {
			if (minTime == null || minTime > contribution.getDateAdded()) {
				minTime = contribution.getDateAdded();
			}
			if (maxTime == null || maxTime < contribution.getDateAdded()) {
				maxTime = contribution.getDateAdded();
			}
		}
		if (recentContributions.size() == 5 && maxTime - minTime < ILLEGAL_CONTRIBUTION_PERIOD * 5) {
			final SetBanParam setBanParam = new SetBanParam();
			setBanParam.setContributorId(contributorId);
			setBanParam.setNewStatus(true);
			authMapper.setBanStatus(setBanParam);
		}
	}

}
