package com.kgisl.am.tenant;

public class SettingsTenant {

	private SettingsTenantConfig tenantConfig;
	private SettingsTenantDbConfig dbConfig;

	public SettingsTenant() {
	}

	public SettingsTenantConfig getTenantConfig() {
		return tenantConfig;
	}

	public void setTenantConfig(SettingsTenantConfig tenantConfig) {
		this.tenantConfig = tenantConfig;
	}

	public SettingsTenantDbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(SettingsTenantDbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}
}
