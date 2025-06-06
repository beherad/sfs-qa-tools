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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Utility class for date formatting.<br>
 * Extends {@link org.apache.commons.lang3.time.DateFormatUtils}
 *
 * @author Monalis Behera
 */
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

    public static String getTimestamp(final String formatPattern) {
        final DateFormat df = new SimpleDateFormat(formatPattern, Locale.US);
        final Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }
}
