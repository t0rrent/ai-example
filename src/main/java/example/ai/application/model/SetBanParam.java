package example.ai.application.model;

public class SetBanParam {
	
	private long contributorId;
	
	private boolean newStatus;

	public long getContributorId() {
		return contributorId;
	}

	public void setContributorId(final long contributorId) {
		this.contributorId = contributorId;
	}

	public boolean isNewStatus() {
		return newStatus;
	}

	public void setNewStatus(final boolean newStatus) {
		this.newStatus = newStatus;
	}

}
