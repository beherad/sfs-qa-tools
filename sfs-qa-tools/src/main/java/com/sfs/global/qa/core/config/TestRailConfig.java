package com.sfs.global.qa.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TestRailConfig {

    private static final String USERNAME = "auto@behera.com";
    private static final String PASSWORD = "uncommon";

    private String endpointUrl;
    private String username;
    private String password;
    private String instanceConfig;

    /*public static TestRailConfig PRODUCTION() {
        return TestRailConfig.builder()
                .region("emea")
                .endpoint("https://axn-testrail01-p.axadmin.net")
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static TestRailConfig DEVELOPMENT() {
        return TestRailConfig.builder()
                .region("emea")
                .endpoint("https://axn-testrail01-d.axadmin.net")
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static TestRailConfig AUDATARGET() {
        return TestRailConfig.builder()
                .region("emea")
                .endpoint("https://audatarget.testrail.io")
                .username("read.only@behera.com")
                .password("read.only")
                .build();
    }

    public static TestRailConfig UK() {
        return TestRailConfig.builder()
                .region("emea")
                .endpoint("https://audatexuktest.testrail.io")
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static TestRailConfig NA() {
        return TestRailConfig.builder()
                .region("na")
                .endpoint("https://beheranorthamerica.testrail.com")
                .username("SA-WW-automation.dealerfire@behera.com")
                .password("Dealer22!ds")
                .build();
    }*/
}
