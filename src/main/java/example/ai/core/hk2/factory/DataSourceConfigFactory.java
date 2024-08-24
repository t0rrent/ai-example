package example.ai.core.hk2.factory;

import org.glassfish.hk2.api.Factory;

import example.ai.core.model.DataSourceConfig;
import example.ai.core.util.SimpleConfigUtil;

public class DataSourceConfigFactory implements Factory<DataSourceConfig> {

	@Override
	public DataSourceConfig provide() {
		final DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setHostName(SimpleConfigUtil.getConfig("dataSourceHostName", "localhost"));
		dataSourceConfig.setDatabase(SimpleConfigUtil.getConfig("dataSourceDatabase", "ai_example"));
		dataSourceConfig.setUser(SimpleConfigUtil.getConfig("dataSourceUser", "postgres"));
		dataSourceConfig.setPassword(SimpleConfigUtil.getConfig("dataSourcePassword", "postgres"));
		return dataSourceConfig;
	}

	@Override
	public void dispose(final DataSourceConfig instance) {
	}

}
