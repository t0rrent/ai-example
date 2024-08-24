package example.ai.mapper;

import java.util.List;

import example.ai.application.model.Contribution;
import example.ai.application.model.ContributionsQueryParam;
import example.ai.application.model.Contributor;
import example.ai.application.model.ContributorsQueryParam;
import example.ai.application.model.DrawingCommitParam;

public interface ContributionMapper {

	void insertTrainingResource(DrawingCommitParam drawing);

	List<Contributor> getContributors(ContributorsQueryParam contributorsQueryParam);
	
	long getContributorCount();

	List<Contribution> getContributions(ContributionsQueryParam contributionsQueryParam);

	Contributor getContributorById(long contributorId);

}
