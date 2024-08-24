package example.ai.application.service;

import java.util.List;

import example.ai.application.model.Contribution;

public interface AuthService {

	long getContributor(String ipAddress);

	void checkAuthorization(String authToken);

	void banContributor(long contributorId);

	void unbanContributor(long contributorId);

	void auditRecentDrawings(long contributorId, List<Contribution> recentContributions);

}
