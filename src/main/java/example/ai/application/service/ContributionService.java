package example.ai.application.service;

import java.util.List;

import example.ai.application.model.Contribution;
import example.ai.application.model.Contributor;
import example.ai.core.model.DrawingDataEntry;

public interface ContributionService {

	void commitDrawing(DrawingDataEntry drawing, final long contributorId);

	List<Contributor> getContributors(long skip, long limit);

	long getContributorCount();

	List<Contribution> getContributions(long contributorId, long skip, long limit);

	Contributor getContributorById(long contributorId);

}
