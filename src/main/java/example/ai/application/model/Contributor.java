package example.ai.application.model;

public class Contributor {
	
	private long id;
	
	private String ipAddress;
	
	private boolean banned;
	
	private long lastContributionDate;
	
	private long contributionCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(final boolean banned) {
		this.banned = banned;
	}

	public long getLastContributionDate() {
		return lastContributionDate;
	}

	public void setLastContributionDate(final long lastContributionDate) {
		this.lastContributionDate = lastContributionDate;
	}

	public long getContributionCount() {
		return contributionCount;
	}

	public void setContributionCount(final long contributionCount) {
		this.contributionCount = contributionCount;
	}

}
