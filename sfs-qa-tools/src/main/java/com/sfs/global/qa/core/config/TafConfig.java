package com.sfs.global.qa.core.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.sfs.global.qa.utils.EnvPropertiesUtils;

@Slf4j
@AllArgsConstructor
public enum TafConfig {

    // DATA
    DATA_DIRPATH (List.of("data.dir"), "src/main/resources", false),

    // (DEPLOYMENT) ENVIRONMENT
    ENV_NAME (List.of("env", "env.name"), "", true),

    // (AUT) APP CONFIG
    APP_CONFIG_NAME (List.of("app.config", "app.config.name"), "", true),

    // (TEST) EXECUTION ENVIRONMENT
    EXEC_ENV_NAME (List.of("exec.env", "exec.env.name"), "", true),

    OS_NAME (List.of(execEnvKey("os.name"), execEnvKey("platform.name")), "Windows", false),
    OS_VERSION (List.of(execEnvKey("os.version"), execEnvKey("platform.version")), "10", false),


    // TEST RUN
    TEST_RUN_NAME (List.of("test.run.name" , "testRunName"), genericTestRunName(), false),
    TEST_RUN_RETRIES_COUNT (List.of("test.run.retries.count"), "0", false),
    TEST_REPORTS_DIRPATH (List.of("test.reports.dirpath"), "target/reports", false),
    ASSERTJ_ASSERTIONS_LOG_DESCRIPTIONS (List.of("assertj.assertions.log.descriptions"), "true", false), //log all assertion descriptions on execution
    EXTENTREPORTS_LOG_LEVELS_COLORING (List.of("extentreports.log.levels.coloring"), "debug,info,warn,error", false), //list of debug,info,warn,error


    // AWS S3
//    AWS_S3_ENABLED (List.of("aws.s3.enabled"), "false", false),
//    AWS_S3_ENDPOINT_URL (List.of("aws.s3.config.endpoint.url"), "https://s3-eu.behera.farm", false),
//    AWS_S3_REGION_NAME (List.of("aws.s3.config.region.name"), "aws-global", false),
//    AWS_S3_ACCESS_KEY (List.of("aws.s3.config.accessKey"), "gdc_qa-admin", false),
//    AWS_S3_SECRET_KEY (List.of("aws.s3.config.secretKey"), "k1v5Bg6KQy1NIuZ8fJ8BmdCUdD55jzLxcPgRaQ4M", false),
//    AWS_S3_BUCKET_NAME (List.of("aws.s3.config.bucketName"), "gdc-qa-temp", false),
//    AWS_S3_FILES_DOWNLOAD_FROM_DIRPATH (List.of("aws.s3.files.download.from.dirpath"), "", TafConfig.AWS_S3_ENABLED.isTrue()),
//    AWS_S3_FILES_DOWNLOAD_TO_FOLDER (List.of("aws.s3.files.download.to.folder"), "target/files/s3", false),
//    AWS_S3_REPORTS_UPLOAD_FROM_FOLDER (List.of("aws.s3.reports.upload.from.folder"), "target/reports", false),
//    AWS_S3_REPORTS_UPLOAD_TO_DIRPATH (List.of("aws.s3.reports.upload.to.dirpath"), "", TafConfig.AWS_S3_ENABLED.isTrue()),
//    AWS_S3_REPORTS_SUITE_NAME (List.of("aws.s3.reports.suite.name"), "", TafConfig.AWS_S3_ENABLED.isTrue()),

    // TESTRAIL
//    TESTRAIL_ENABLED (List.of("testrail.enabled"), "false", false),
//    TESTRAIL_ENDPOINT_URL (List.of("testrail.config.endpoint.url"), "https://axn-testrail01-p.axadmin.net", false),
//    TESTRAIL_USERNAME (List.of("testrail.config.username"), "auto@behera.com", false),
//    TESTRAIL_PASSWORD (List.of("testrail.config.password"), "uncommon", false),
//    TESTRAIL_PROJECT_NAME (List.of("testrail.project.name"), "", TafConfig.TESTRAIL_ENABLED.isTrue()),
//    TESTRAIL_PLAN_NAME (List.of("testrail.plan.name"), "", TafConfig.TESTRAIL_ENABLED.isTrue()),
//    TESTRAIL_SUITE_NAME (List.of("testrail.suite.name"), "", TafConfig.TESTRAIL_ENABLED.isTrue()),
//    TESTRAIL_CONFIGURATIONS_VALUES (List.of("testrail.testrun.config.values"), "", false),
//
//    // SAUCELABS
//    SAUCELABS_ENDPOINT_URL (List.of("saucelabs.config.endpoint.url"), "https://ondemand.us-west-1.saucelabs.com:443/wd/hub", false), //https://ondemand.saucelabs.com/wd/hub
//    SAUCELABS_USERNAME (List.of("saucelabs.config.username"), "sso-SAUCELAB-AUTO", false),
//    SAUCELABS_ACCESS_KEY (List.of("saucelabs.config.accessKey"), "Asses KEY", false),
//    SAUCELABS_TUNNEL_NAME (List.of("saucelabs.config.tunnel.name"), "", false),

    ;


    @Setter
    private static Properties envProperties;

    @Getter
    private final List<String> keys;
    private final String defaultValue;
    private final boolean isMandatory;

    @AllArgsConstructor
    public enum AppConfig {

        WEBAPPS_CUSTOM_APP_URL (List.of("webapps.%s.app.url", appConfigKey("webapps.%s.app.url")), "", false),
        SERVICES_CUSTOM_API_URL (List.of("services.%s.api.url", appConfigKey("services.%s.api.url")), "", false),
        DATABASES_CUSTOM_DB_CONFIG_PATH (List.of("databases.%s.db.config.path", appConfigKey("databases.%s.db.config.path")), "", false),
        DATABASES_CUSTOM_DB_URL (List.of("databases.%s.db.url", appConfigKey("databases.%s.db.url")), "", false),
        DATABASES_CUSTOM_DB_TYPE (List.of("databases.%s.db.type", appConfigKey("databases.%s.db.type")), "", false),
        DATABASES_CUSTOM_DB_DRIVER (List.of("databases.%s.db.driver", appConfigKey("databases.%s.db.driver")), "", false),
        DATABASES_CUSTOM_DB_USERNAME (List.of("databases.%s.db.username", appConfigKey("databases.%s.db.username")), "", false),
        DATABASES_CUSTOM_DB_PASSWORD (List.of("databases.%s.db.password", appConfigKey("databases.%s.db.password")), "", false),
        DATABASES_CUSTOM_DB_SCHEMA (List.of("databases.%s.db.schema", appConfigKey("databases.%s.db.schema")), "", false),
        DATABASES_CUSTOM_DB_MAPPERS_PACKAGE (List.of("databases.%s.db.mappers.package", appConfigKey("databases.%s.db.mappers.package")), "", false),
        ;

        private final List<String> keys;
        private final String defaultValue;
        private final boolean isMandatory;

        public String getValue(final String customName) {
            List<String> modifiedKeys = keys.stream().map(key -> String.format(key, customName)).collect(Collectors.toList());
            return EnvPropertiesUtils.getValue(modifiedKeys, getEnvProperties(), defaultValue, isMandatory);
        }

        public boolean isTrue(final String customKeyName) {
            List<String> modifiedKeys = keys.stream().map(key -> String.format(key, customKeyName)).collect(Collectors.toList());
            return EnvPropertiesUtils.isTrue(modifiedKeys, getEnvProperties(), defaultValue, isMandatory);
        }
    }

    @AllArgsConstructor
    public enum Web {

        HUB_URL (List.of("hub.url", execEnvKey("hub.url")), "", false),
        BROWSER_NAME (List.of("browser", "browser.name", execEnvKey("browser.name")), "chrome", true),
        BROWSER_VERSION (List.of("browser.version", execEnvKey("browser.version")), "124", false),
        BROWSER_LANGUAGE (List.of("browser.language", execEnvKey("browser.language")), "en", false),
        BROWSER_IMPLICITLYWAIT_TIMEOUT (List.of("browser.timeouts.implicitlywait.seconds", execEnvKey("browser.timeouts.implicitlywait.seconds")), "5", false),
        BROWSER_PAGELOAD_TIMEOUT (List.of("browser.timeouts.pageload.seconds", execEnvKey("browser.timeouts.pageload.seconds")), "15", false),
        BROWSER_ELEMENTLOAD_TIMEOUT (List.of("browser.timeouts.elementload.seconds", execEnvKey("browser.timeouts.elementload.seconds")), "5", false),
        BROWSER_OPTIONS (List.of("browser.options", execEnvKey("browser.options")), "", false),
        ;

        private final List<String> keys;
        private final String defaultValue;
        private final boolean isMandatory;

        public String getValue() {
            return EnvPropertiesUtils.getValue(keys, getEnvProperties(), defaultValue, isMandatory);
        }

        public boolean isTrue() {
            return EnvPropertiesUtils.isTrue(keys, getEnvProperties(), defaultValue, isMandatory);
        }
    }

    @AllArgsConstructor
    public enum Mobile {

        DEVICE_URL (List.of("device.url", execEnvKey("device.url")), "", false),
        DEVICE_NAME (List.of("device.name", execEnvKey("device.name")), "Samsung Galaxy Tab S9", false),
        DEVICE_UDID (List.of("device.udid", execEnvKey("device.udid")), "", false),
        DEVICE_OS_NAME (List.of("device.os.name", execEnvKey("device.os.name"), "device.platform.name", execEnvKey("device.platform.name")), "Android", Mobile.DEVICE_NAME.isMandatory),
        DEVICE_OS_VERSION (List.of("device.os.version", execEnvKey("device.os.version"), "device.platform.version", execEnvKey("device.platform.version")), "13", Mobile.DEVICE_NAME.isMandatory),
        DEVICE_BROWSER_NAME (List.of("device.browser.name", execEnvKey("device.browser.name")), "chrome", false),
        DEVICE_BROWSER_VERSION (List.of("device.browser.version", execEnvKey("device.browser.version")), "124", false),
        DEVICE_BROWSER_LANGUAGE (List.of("device.browser.language", execEnvKey("device.browser.language")), "en", false),
        DEVICE_BROWSER_IMPLICITLYWAIT_TIMEOUT (List.of("device.browser.timeouts.implicitlywait.seconds", execEnvKey("device.browser.timeouts.implicitlywait.seconds")), "5", false),
        DEVICE_BROWSER_PAGELOAD_TIMEOUT (List.of("device.browser.timeouts.pageload.seconds", execEnvKey("device.browser.timeouts.pageload.seconds")), "15", false),
        DEVICE_BROWSER_ELEMENTLOAD_TIMEOUT (List.of("device.browser.timeouts.elementload.seconds", execEnvKey("device.browser.timeouts.elementload.seconds")), "5", false),
        DEVICE_ORIENTATION (List.of("device.orientation", execEnvKey("device.orientation")), "portrait", false),
        DEVICE_APP_PATH (List.of("device.app.path", execEnvKey("device.app.path")), "", false),
        DEVICE_APP_PACKAGE (List.of("device.app.package", execEnvKey("device.app.package")), "", false)
        ;

        private final List<String> keys;
        private final String defaultValue;
        private final boolean isMandatory;

        public String getValue() {
            return EnvPropertiesUtils.getValue(keys, getEnvProperties(), defaultValue, isMandatory);
        }

        public boolean isTrue() {
            return EnvPropertiesUtils.isTrue(keys, getEnvProperties(), defaultValue, isMandatory);
        }
    }

    public String getValue() {
        return EnvPropertiesUtils.getValue(keys, getEnvProperties(), defaultValue, isMandatory);
    }

    public boolean isTrue() {
        return EnvPropertiesUtils.isTrue(keys, getEnvProperties(), defaultValue, isMandatory);
    }

    public List<String> getAsList(final String splitterRegex) {
        return List.of(EnvPropertiesUtils.getValue(keys, getEnvProperties(), defaultValue, isMandatory)
                .replace(" ","")
                .split(splitterRegex));
    }

    private static String genericTestRunName() {
        return "auto-testrun-".concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
    }

    private static Properties getEnvProperties() {
        if (envProperties == null) {
            return new Properties();
        }
        return envProperties;
    }

    private static String execEnvKey(String key) {
        return String.format("exec.env.%s.%s", EXEC_ENV_NAME.getValue(), key);
    }

    private static String appConfigKey(String key) {
        return String.format("app.config.%s.%s", APP_CONFIG_NAME.getValue(), key);
    }

    /**
     * Get the custom exec env based property value - exec.env.${exec.env}.${key}
     * @param key property key name
     * @return property value
     */
    public static String getExecEnvProperty(final String key) {
        return getEnvProperties().getProperty(execEnvKey(key));
    }

    /**
     * Get the custom app config based property value - app.config.${app.config}.${key}
     * @param key property key name
     * @return property value
     */
    public static String getAppConfigProperty(final String key) {
        return getEnvProperties().getProperty(appConfigKey(key));
    }
}