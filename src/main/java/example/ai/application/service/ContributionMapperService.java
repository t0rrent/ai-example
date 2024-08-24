package example.ai.application.service;

import java.util.List;

import example.ai.application.model.Contribution;
import example.ai.application.model.ContributionsQueryParam;
import example.ai.application.model.Contributor;
import example.ai.application.model.ContributorsQueryParam;
import example.ai.application.model.DrawingCommitParam;
import example.ai.core.model.DrawingDataEntry;
import example.ai.mapper.ContributionMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

public class ContributionMapperService implements ContributionService {

	private final ContributionMapper contributionMapper;
	
	@Inject
	public ContributionMapperService(final ContributionMapper contributionMapper) {
		this.contributionMapper = contributionMapper;
	}

	@Override
	public void commitDrawing(final DrawingDataEntry drawing, final long contributorId) {
		if (isDrawingEmpty(drawing.getCompressedDrawing())) {
			throw new BadRequestException("Missing drawing");
		}
		final DrawingCommitParam drawingCommitParam = new DrawingCommitParam();
		drawingCommitParam.setCharacter(drawing.getCharacter());
		drawingCommitParam.setCompressedDrawing(drawing.getCompressedDrawing());
		drawingCommitParam.setContributorId(contributorId);
		contributionMapper.insertTrainingResource(drawingCommitParam);
	}

	private boolean isDrawingEmpty(final List<Boolean> compressedDrawing) {
		for (int bitGroupIndex = 0; bitGroupIndex < compressedDrawing.size() / 6; bitGroupIndex++) {
			if (compressedDrawing.get(bitGroupIndex * 6)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Contributor> getContributors(final long skip, final long limit) {
		final ContributorsQueryParam contributorsQueryParam = new ContributorsQueryParam();
		contributorsQueryParam.setSkip(skip);
		contributorsQueryParam.setLimit(limit);
		return contributionMapper.getContributors(contributorsQueryParam);
	}

	@Override
	public long getContributorCount() {
		return contributionMapper.getContributorCount();
	}

	@Override
	public List<Contribution> getContributions(final long contributorId, final long skip, final long limit) {
		final ContributionsQueryParam contributionsQueryParam = new ContributionsQueryParam();
		contributionsQueryParam.setContributorId(contributorId);
		contributionsQueryParam.setSkip(skip);
		contributionsQueryParam.setLimit(limit);
		return contributionMapper.getContributions(contributionsQueryParam);
	}

	@Override
	public Contributor getContributorById(final long contributorId) {
		return contributionMapper.getContributorById(contributorId);
	}

}
