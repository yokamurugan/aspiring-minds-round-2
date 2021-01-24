package com.kgisl.am.tenant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Tenant info service 
 */
@Service
public class TenantService {

	private static final Logger log = LoggerFactory.getLogger(TenantService.class);

	public void loadSettingsTenant(String tenantCode) throws JsonParseException, JsonMappingException, IOException {
		SettingsTenant settingsTenant = TenantConfigThreadLocal.getCurrentTenant();
		
		if (settingsTenant == null || !settingsTenant.getTenantConfig().getTenantCode().equals(tenantCode)) {
			ObjectMapper jsonMapper = new ObjectMapper();
			String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
			File jsonFile = new File(currentDir+"/config/"+"settingsTenant"+tenantCode+".json");
			settingsTenant = jsonMapper.readValue(jsonFile, new TypeReference<SettingsTenant>() {});
			TenantConfigThreadLocal.setCurrentTenant(settingsTenant);
		}
		log.info(" * Settings tenant : "+settingsTenant.toString());
	}
    
}
