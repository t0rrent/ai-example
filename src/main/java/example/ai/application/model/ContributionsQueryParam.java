package example.ai.application.model;

public class ContributionsQueryParam {
	
	private long contributorId;
	
	private long skip;
	
	private long limit;

	public long getContributorId() {
		return contributorId;
	}

	public void setContributorId(final long contributorId) {
		this.contributorId = contributorId;
	}

	public long getSkip() {
		return skip;
	}

	public void setSkip(final long skip) {
		this.skip = skip;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(final long limit) {
		this.limit = limit;
	}
	
}
