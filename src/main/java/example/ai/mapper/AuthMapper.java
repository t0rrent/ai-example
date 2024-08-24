package example.ai.mapper;

import example.ai.application.model.SetBanParam;

public interface AuthMapper {

	boolean isContributorBanned(String ipAddress);

	Long getContributorByIpAddress(String ipAddress);

	long createContributor(String ipAddress);

	boolean checkAuthorization(String adminPAT);

	void setBanStatus(SetBanParam setBanParam);

}
