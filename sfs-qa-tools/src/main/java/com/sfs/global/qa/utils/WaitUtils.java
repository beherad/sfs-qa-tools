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

import java.util.concurrent.TimeUnit;

/**
 * WaitUtils class provides utility methods for waiting.
 * @author Monalis Behera
 */
@Slf4j
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class WaitUtils {

    public static void timeout(final int timeout, final TimeUnit timeUnit) {
        try {
            switch (timeUnit) {
            case MILLISECONDS:
                Thread.sleep(timeout);
                break;
            case SECONDS:
                Thread.sleep((long) timeout * 1000);
                break;
            case MINUTES:
                Thread.sleep((long) timeout * 1000 * 60);
                break;
            default:
                break;
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    public static void timeout(final int timeout, final TimeUnit timeUnit, final String message) {
        log.info(message);
        timeout(timeout, timeUnit);
    }
}