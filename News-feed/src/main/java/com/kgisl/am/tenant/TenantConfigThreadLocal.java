package com.kgisl.am.tenant;


public class TenantConfigThreadLocal {

	private static ThreadLocal<SettingsTenant> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(SettingsTenant tenant) {
        currentTenant.set(tenant);
    }

    public static SettingsTenant getCurrentTenant() {
        return currentTenant.get();
    }
    
    

}
