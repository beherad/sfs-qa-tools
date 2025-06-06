package com.sfs.global.qa.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Properties;

@Slf4j
public class EnvPropertiesUtils {

    /**
     * Get value from system properties, env properties or default value
     *
     * @param key property key
     * @param envProperties env properties
     * @param defaultValue default value
     * @param isMandatory is mandatory
     * @return property valus as a {@link String}.
     *
     * @throws IllegalArgumentException - if mandatory property is not set
     */
    public static String getValue(String key, Properties envProperties, String defaultValue, boolean isMandatory) {
        String value = System.getProperty(key);
        if (value == null) {
            log.debug("System variable [{}] is not set, checking variable in env properties... ", key);
            value = envProperties.getProperty(key);
        }
        if (value == null) {
            log.debug("Env properties variable [{}] is not set, checking specified default value... ", key);
            value = defaultValue;
        }
        if ((value == null || value.isEmpty()) && isMandatory) {
            log.error("Mandatory property [{}] is not set from command line or in env properties file & don't have default value assigned!", key);
            throw new IllegalArgumentException("Mandatory property is not set from command line or in env properties file & don't have default value assigned: " + key);
        }
        return value;
    }

    public static String getValue(List<String> keys, Properties envProperties, String defaultValue, boolean isMandatory) {
        String value;
        for (String key : keys) {
            value = System.getProperty(key);
            if (value != null) {
                return value;
            }
        }
        log.debug("System variable [{}] is not set, checking variable in env properties... ", keys);
        for (String key : keys) {
            value = envProperties.getProperty(key);
            if (value != null) {
                return value;
            }
        }
        log.debug("Env properties variable [{}] is not set, checking specified default value... ", keys);
        value = defaultValue;
        if ((value != null && !value.isEmpty()) || !isMandatory) {
            return value;
        }
        log.error("Mandatory property [{}] is NOT set from the command line or in env properties file & does NOT have default value assigned!", keys);
        throw new IllegalArgumentException("Mandatory property is NOT set from the command line or in env properties file & does NOT have default value assigned: " + keys);
    }

    /**
     * Get value from system properties, env properties or default value
     *
     * @param key          - property key
     * @param envProperties - env properties
     * @param defaultValue - default value
     * @param isMandatory  - is mandatory
     * @return - property value as boolean
     *
     * @throws IllegalArgumentException - if mandatory property is not set
     */
    public static boolean isTrue(String key, Properties envProperties, String defaultValue, boolean isMandatory) {
        return getValue(key, envProperties, defaultValue, isMandatory).equalsIgnoreCase("true");
    }

    public static boolean isTrue(List<String> keys, Properties envProperties, String defaultValue, boolean isMandatory) {
        return getValue(keys, envProperties, defaultValue, isMandatory).equalsIgnoreCase("true");
    }

}
