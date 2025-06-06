package com.sfs.global.qa.core.test;

import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.utils.FileUtils;
import com.sfs.global.qa.utils.PropertiesUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Properties;

@Slf4j
public class TestConfig extends TestContext {

    private static final String ENV_NAME = TafConfig.ENV_NAME.getValue();
    private static final String ENV_PROPERTIES_FILEPATH = lookupEnvProperties(
            List.of(System.getProperty("data.dir", "src/main/resources"), "src/main/resources", "src/test/resources"),
            List.of("env", "environment"),
            ENV_NAME);

    /**
     * Lookup environment properties file in the specified directories and subdirectories
     * @param dirpaths list of directories
     * @param subdirNames list of subdirectories
     * @param envName environment name
     * @return environment properties file path or null if not found
     */
    private static String lookupEnvProperties(final List<String> dirpaths, final List<String> subdirNames, final String envName) {
        for (String dirpath : dirpaths) {
            for (String subdirName : subdirNames) {
                String filepath = String.format("%s/%s/%s/%s.properties", dirpath, subdirName, envName, envName);
                if (FileUtils.isFileExists(filepath)) {
                    return filepath;
                }
            }
        }
        log.error("Environment properties file at the specified filepath is NOT found!");
        return null;
    }

    private static final Properties ENV_PROPERTIES = PropertiesUtils.readProperties(ENV_PROPERTIES_FILEPATH)
            .replaceInnerVariables(List.of(TafConfig.ENV_NAME.getKeys(), TafConfig.APP_CONFIG_NAME.getKeys(),
                    TafConfig.EXEC_ENV_NAME.getKeys(), TafConfig.TEST_RUN_NAME.getKeys()))
            .overrideWith(System.getProperties(), "System properties")
            .getProperties();

    public static Properties getEnvProperties() {
        return ENV_PROPERTIES;
    }

    public static String getEnvProperty(final String key) {
        return PropertiesUtils.getProperty(getEnvProperties(), key);
    }

}
