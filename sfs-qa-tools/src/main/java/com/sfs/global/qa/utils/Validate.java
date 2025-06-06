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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for all utility methods related to objects validation.
 *
 * @author Monalis Behera
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validate extends org.apache.commons.lang3.Validate {

    /**
     * Validates that a String expression is NOT {@code null} or empty.
     * Otherwise, throwing an exception with the specified message.
     *
     * @param str String to check
     * @param message the exception message to use if the validation fails
     * @return The original string expression if it is not {@code null} or empty
     * @throws IllegalArgumentException if the String expression is {@code null} or empty
     */
    public static String notNullOrEmpty(final String str, final String message) {
        if (StringUtils.isNullOrEmpty(str)) {
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return str;
    }

    /**
     * Validates that String expression is NOT {@code null} or blank (empty or whitespace only).
     * Otherwise, throwing an exception with the specified message.
     *
     * @param str String to check
     * @param message the exception message to use if the validation fails
     * @return The original string expression if it is not {@code null} or blank (empty or whitespace only)
     * @throws IllegalArgumentException if the String expression is {@code null} or blank (empty or whitespace only)
     */
    public static String notNullOrBlank(final String str, final String message) {
        if (StringUtils.isNullOrBlank(str)) {
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return str;
    }

    /**
     * Validates that T object is NOT {@code null}.
     * Otherwise, throwing an exception with the specified message.
     *
     * @param <T> the object type
     * @param object object to check
     * @param message the exception message to use if the validation fails
     * @return The original object if it is not {@code null}
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static <T> T notNull(final T object, final String message) {
        if (object == null) {
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}