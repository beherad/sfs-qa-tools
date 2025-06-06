package com.sfs.global.qa.connect.db.clients.mybatis;

import lombok.Builder;

import java.util.Properties;

@Builder
public class MyBatisDbConfig {

    private String driver;
    private String url;
    private String username;
    private String password;
    private String aliasesPackage;
    private String mappersPackage;

    public Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty("driver", driver);
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        properties.setProperty("aliasesPackage", aliasesPackage);
        properties.setProperty("mappersPackage", mappersPackage);
        return properties;
    }

}
