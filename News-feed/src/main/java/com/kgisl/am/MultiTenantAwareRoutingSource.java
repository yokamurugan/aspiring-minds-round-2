package com.kgisl.am;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgisl.am.tenant.SettingsTenant;
import com.kgisl.am.tenant.SettingsTenantDbConfig;
import com.kgisl.am.tenant.TenantConfigThreadLocal;
import com.zaxxer.hikari.HikariDataSource;

public class MultiTenantAwareRoutingSource extends AbstractRoutingDataSource {

    private final Map<String, HikariDataSource> tenantsDataSource;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public MultiTenantAwareRoutingSource(){
        log.info("Loading tenant files...");
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        File[] files = new File(currentDir+"/config/").listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.startsWith("settingsTenant") && filename.endsWith(".json");}
        });


        ObjectMapper jsonMapper = new ObjectMapper();

        SettingsTenant[] settingsTenantsArray = new SettingsTenant[files.length];
        for(int i=0; i<files.length; i++){
            try {
                settingsTenantsArray[i] = jsonMapper.readValue(files[i].getAbsoluteFile(), new TypeReference<SettingsTenant>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.tenantsDataSource = getAvailableTenantDataSource(settingsTenantsArray);
        Map<Object,Object> targetDataSources = new HashMap<>();

        tenantsDataSource.forEach((tenantKey,tenantDs)-> {
            try {
                log.debug(String.format("Getting database connection for [%s]",tenantKey));
                tenantDs.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(String.format("Error getting database connection for [%s]",tenantKey));
                log.error(e.getMessage());
            }
        });
        this.setTargetDataSources(targetDataSources);
    }


    private Map<String, HikariDataSource> getAvailableTenantDataSource(SettingsTenant[] settingsTenants){
        return Arrays.stream(settingsTenants)
                .collect(Collectors.toMap
                        (x -> x.getTenantConfig().getTenantCode(),x -> buildDataSource(x.getDbConfig())));
    }

    private HikariDataSource buildDataSource(SettingsTenantDbConfig dbConfig) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setInitializationFailTimeout(0);
        String url="jdbc:"+dbConfig.getDbType().toLowerCase()+"://"+dbConfig.getHost()+":"+dbConfig.getPort()+"/"+dbConfig.getDatabase()+ "?useSSL=false&serverTimezone=UTC";
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setMaximumPoolSize(dbConfig.getPoolMax());
        dataSource.setPoolName(dbConfig.getDatabase());
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(dbConfig.getUser());
        dataSource.setPassword((dbConfig.getPassword()));
        dataSource.setMaximumPoolSize(dbConfig.getPoolMax());

        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        // Nothing to do (skipping this)..
    }

    @Override
    protected HikariDataSource determineTargetDataSource() {
        String lookupKey = (String) determineCurrentLookupKey();
        return tenantsDataSource.get(lookupKey);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantConfigThreadLocal.getCurrentTenant();
    }

    @Override
    public Connection getConnection() throws SQLException {

        Map.Entry<String, HikariDataSource> entry = tenantsDataSource.entrySet().iterator().next();
        String key = entry.getKey();

        SettingsTenant settingsTenant = TenantConfigThreadLocal.getCurrentTenant();


        if(settingsTenant != null){
            log.info("Loading datasource for ["+settingsTenant.getTenantConfig().getTenantCode()+"] ...");
            try{
                return tenantsDataSource.get(settingsTenant.getTenantConfig().getTenantCode()).getConnection();
            } catch (Exception e){
                try {
					throw new Exception(String.format("Error getting database connection for [%s]",settingsTenant.getTenantConfig().getTenantCode()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        } else {
            log.info("Loading default ["+key+"] datasource...");
            try{
                return tenantsDataSource.get(key).getConnection();
            } catch (Exception e){
                log.error(e.getMessage());
                try {
					throw new Exception(String.format("Error getting default database connection for [%s]",key));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }
		return null;
    }
}
