/*
 * Copyright (c) 2013-2022 CodeMinter, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sfs.global.qa.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for all properties related operations.
 *
 * @author Monalis Behera
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtils {

    /**
     * Gets properties from properties file.
     *
     * @param filepath is a path to the properties file
     * @return properties
     */
    public static Properties loadProperties(final String filepath) {
        Validate.notNullOrEmpty(filepath, "Specified properties filepath is NULL or empty!");
        log.info("Load properties from properties file [{}]", filepath);
        final Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filepath)) {
            properties.load(fis);
        } catch (SecurityException | IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

    /**
     * Gets properties from stream of bytes.
     *
     * @param inputstream is a stream of bytes
     * @return properties
     */
    public static Properties loadProperties(final InputStream inputstream) {
        final Properties properties = new Properties();
        try {
            properties.load(inputstream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

    /**
     * Gets property value by property key from properties file.
     *
     * @param filepath is a path to the properties file
     * @param key is a property key
     * @return value
     */
    public static String getProperty(final String filepath, final String key) {
        Validate.notNullOrBlank(key, "Specified property key is NULL or empty/blank!");
        log.debug("Get property value by its key [{}]", key);
        return validateProperty(key, loadProperties(filepath).getProperty(key));
    }

    /**
     * Gets property value by property key from specified properties object.
     *
     * @param properties is a Properties object
     * @param key is a property key
     * @return value
     */
    public static String getProperty(final Properties properties, final String key) {
        Validate.notNull(properties, "Specified properties is NULL object!");
        Validate.notNullOrBlank(key, "Specified property key is NULL or empty/blank!");
        log.debug("Get property value by its key [{}]", key);
        return validateProperty(key, properties.getProperty(key));
    }

    /**
     * Writes properties to file.
     *
     * @param properties is a Properties object
     * @param filepath is a path to the properties file
     * @return properties
     */
    public static Properties writeToFile(final Properties properties, final String filepath) {
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

    private static String validateProperty(final String key, final String value) {
        if (value == null) {
            log.error("Unable to find property [{}] within the provided property file or <properties> object values.", key);
            throw new IllegalArgumentException("Unable to find property [" + key + "] within the provided property file or <properties> object values.");
        }
        return value;
    }

    /**
     * Creates a new instance of PropertiesBuilder to build Properties object dynamically.
     *
     * @return PropertiesBuilder instance
     */
    public static PropertiesBuilder builder() {
        return new PropertiesBuilder(new Properties());
    }

    @AllArgsConstructor
    public static class PropertiesBuilder {

        private Properties properties;

        public PropertiesBuilder setProperty(final String key, final String value) {
            properties.setProperty(key, value);
            return this;
        }

        public Properties build() {
            return new Properties(properties);
        }
    }

    public static PropertiesCustomizer readProperties(@NonNull final String filepath) {
        return new PropertiesCustomizer(loadProperties(filepath));
    }

    @Getter
    @AllArgsConstructor
    public static class PropertiesCustomizer {

        private final Properties properties;

        public PropertiesCustomizer replaceInnerVariables() {
            final Map<String, String> vars = new HashMap<>();
            properties.forEach((key, value) -> vars.put(key.toString(), value.toString()));
            System.getProperties().forEach((key, value) -> vars.put(key.toString(), value.toString()));
            properties.forEach((key, value) -> properties.setProperty(key.toString(),
                    replaceVariables(value.toString(), ParseFormat.STRING_WITH_UNIX_VARS_CURLY_BRACKETS, vars)));
            return this;
        }

        public PropertiesCustomizer replaceInnerVariables(List<List<String>> multikeyVariables) {
            final Map<String, String> vars = new HashMap<>();
            properties.forEach((key, value) -> vars.put(key.toString(), value.toString()));
            System.getProperties().forEach((key, value) -> vars.put(key.toString(), value.toString()));
            for (List<String> keys : multikeyVariables) {
                for (String key : keys) {
                    boolean keysUpdated = false;
                    if (vars.containsKey(key)) {
                        keys.forEach(k -> vars.put(k, vars.get(key)));
                        keysUpdated = true;
                    }
                    if (keysUpdated) {
                        break;
                    }
                }
            }
            properties.forEach((key, value) -> properties.setProperty(key.toString(),
                    replaceVariables(value.toString(), ParseFormat.STRING_WITH_UNIX_VARS_CURLY_BRACKETS, vars)));
            return this;
        }

        public PropertiesCustomizer overrideWith(@NonNull final Properties properties, final String propertiesDescr) {
            if (log.isInfoEnabled()) {
                log.info("Override env properties with defined {}", propertiesDescr);
            }
            log.debug("Override env properties with defined {}: {}", propertiesDescr, properties);
            properties.entrySet().stream()
                    .filter(property -> properties.containsKey(property.getKey()))
                    .forEach(property -> properties.setProperty(property.getKey().toString(), property.getValue().toString()));
            return this;
        }

        private String replaceVariables(final String str, final ParseFormat pf, final Map<String, String> params) {
            String newStr = str;
            for (String variable : parseVariables(str, pf)) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getKey().equals(variable)) {
                        newStr = newStr.replace(pf.getStart().concat(variable).concat(pf.getEnd()), entry.getValue());
                    }
                }
            }
            return newStr.equals(str) ? newStr : replaceVariables(newStr, pf, params);
        }

        private List<String> parseVariables(final String str, final ParseFormat pf) {
            final Pattern p = Pattern.compile(pf.getRegex());
            final Matcher m = p.matcher(str);
            final List<String> matches = new ArrayList<>();
            log.debug("Parse [{}] string with using [{}] regex pattern format.", str, pf.name());
            while (m.find()) {
                log.debug("Found [{}] matching value.", m.group(1));
                matches.add(m.group(1));
            }
            if (pf.useRecursiveSearch) {
                for (int i=0; i<matches.size(); i++) {
                    matches.set(i, parseVariables(matches.get(i), pf.getVarParseFormat()).get(0));
                }
            }
            return matches;
        }

        @Getter
        @AllArgsConstructor
        private enum ParseFormat {

            UNIX_VAR_CURLY_BRACKETS ("^\\$\\{(\\w+((\\.)?(\\w+))+)\\}$", "${", "}", false, null),
            STRING_WITH_UNIX_VARS_CURLY_BRACKETS ("(\\$\\{\\w+((\\.)?(\\w+))+\\})+", "${", "}", true, UNIX_VAR_CURLY_BRACKETS);

            private final String regex;
            private final String start;
            private final String end;
            private final boolean useRecursiveSearch;
            private final ParseFormat varParseFormat;

        }

    }

}