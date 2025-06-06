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

import java.util.Arrays;

/**
 * Class for all utility methods related to String objects manipulation.
 *
 * @author Monalis Behera
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Checks if a String is null.
     *
     * @param str String to check
     * @return true if the String is null, false otherwise
     */
    public static boolean isNull(final String str) {
        return str == null;
    }

    /**
     * Checks if a String is null or empty.
     *
     * @param str String to check
     * @return true if the String is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(final String str) {
        return isNull(str) || isEmpty(str);
    }

    /**
     * Checks if a String is null or blank (empty or whitespace only).
     *
     * @param str String to check
     * @return true if the String is null or blank, false otherwise
     */
    public static boolean isNullOrBlank(final String str) {
        return isNull(str) || isBlank(str);
    }

    /**
     * Replaces the last substring of this string that matches the given regular expression with the given replacement.
     *
     * @param str         String to replace from
     * @param regex       regular expression to which this string is to be matched
     * @param replacement the string to be substituted for each match
     * @return resulting String
     * @see String#replaceAll(String, String)
     */
    public static String replaceLast(String str, String regex, String replacement) {
        return str.replaceAll("[" + regex + "$]", replacement);
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str String to capitalize
     * @return resulting String with capitalized first letter
     */
    public static String capitalizeFirst(final String str) {
        char[] c = str.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }

    /**
     * Decapitalizes the first letter of a string.
     *
     * @param str String to decapitalize
     * @return resulting String with decapitalized first letter
     */
    public static String decapitalizeFirst(final String str) {
        char[] c = str.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    /**
     * Checks if a string contains all the search words.
     *
     * @param str string to check
     * @param words substrings to find within the string
     * @return true if the input contains all the search words, false otherwise
     */
    public static boolean containsWords(String str, String... words) {
        return Arrays.stream(words).allMatch(str::contains);
    }
}