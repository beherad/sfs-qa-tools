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
package com.sfs.global.qa.core.utils.reporting.extentreports;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.service.util.ExceptionUtil;
import com.sfs.global.qa.core.config.TafConfig;
import com.sfs.global.qa.core.test.TestConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * ExtentReports HTML report logs appender based on Log4j2 events triggered during the test execution.
 *
 * @author Monalis Behera
 */
@Plugin(name = "ExtentReportsLog4j2Appender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
@Slf4j
public class ExtentReportsLog4j2Appender extends AbstractAppender {

    static {
        TafConfig.setEnvProperties(TestConfig.getEnvProperties());
    }

    protected ExtentReportsLog4j2Appender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static ExtentReportsLog4j2Appender createAppender(@PluginAttribute("name") String name,
                                                             @PluginElement("filter") Filter filter,
                                                             @PluginElement("layout") Layout<? extends Serializable> layout) {
        return new ExtentReportsLog4j2Appender(name, filter, layout, true, Property.EMPTY_ARRAY);
    }

    @Override
    public void append(final LogEvent logEvent) {
        final String eventMsg = logEvent.getMessage().getFormattedMessage();
        ExtentReportsTestNgListener.log(getStatusLevel(logEvent.getLevel()), getEventMsgEntry(logEvent.getLevel(), eventMsg));
        if (logEvent.getThrown() != null) {
            //ExtentReportsTestNgListener.log(getStatusLevel(logEvent.getLevel()), logEvent.getThrown());
            ExtentReportsTestNgListener.log(getStatusLevel(logEvent.getLevel()), getEventThrowableEntry(logEvent.getLevel(), logEvent.getThrown()));
        }
    }

    private Status getStatusLevel(final Level level) {
        for (LoggingLevelMapper logLevel : LoggingLevelMapper.values()) {
            if (logLevel.getLevel() == level) {
                return logLevel.getStatus();
            }
        }
        return Status.INFO;
    }

    private String getEventMsgEntry(final Level level, final String eventMsg) {
        for (LoggingLevelMapper logLevel : LoggingLevelMapper.values()) {
            if (logLevel.getLevel() == level) {
                return logLevel.getEventMsgEntry(eventMsg);
            }
        }
        return LoggingLevelMapper.INFO.getEventMsgEntry(eventMsg);
    }

    private String getEventThrowableEntry(final Level level, final Throwable throwable) {
        for (LoggingLevelMapper logLevel : LoggingLevelMapper.values()) {
            if (logLevel.getLevel() == level) {
                return logLevel.getEventThrowableEntry(throwable);
            }
        }
        return LoggingLevelMapper.INFO.getEventThrowableEntry(throwable);
    }

    private static List<String> LOG_LEVELS;

    private static List<String> getLogLevels() {
        if (LOG_LEVELS == null) {
            LOG_LEVELS = TafConfig.EXTENTREPORTS_LOG_LEVELS_COLORING.getAsList(",");
        }
        return LOG_LEVELS;
    }

    @Getter
    @AllArgsConstructor
    private enum LoggingLevelMapper {

        DEBUG ( Level.DEBUG, Status.INFO, "[DEBUG] ", "badge", "background-color:#ebedef;"), //lightgray
        INFO ( Level.INFO, Status.INFO, "[INFO] ", "badge", "background-color:#b0d9f9;"), //#64b5f6
        WARNING ( Level.WARN, Status.INFO, "[WARNING] ", "badge warning-bg", ""), //background-color:#ffd369;
        ERROR ( Level.ERROR, Status.INFO, "[ERROR] ", "badge fail-bg", "" );

        private final Level level;
        private final Status status;
        private final String eventMsgPrefix;
        private final String eventMsgCssStyles;
        private final String eventMsgInnerStyles;

        public String getEventMsgEntry(final String eventMsg) {
            if (!getLogLevels().contains(getLevel().name().toLowerCase())) {
                return eventMsgPrefix.concat(eventMsg);
            }
            if (eventMsgInnerStyles.isEmpty()) {
                return "<div class='".concat(eventMsgCssStyles).concat("' style='white-space:inherit;'>")
                        .concat(eventMsgPrefix).concat(eventMsg)
                        .concat("</div>");
            }
            return "<div class='".concat(eventMsgCssStyles).concat("' style='white-space:inherit;").concat(eventMsgInnerStyles).concat("'>")
                    .concat(eventMsgPrefix).concat(eventMsg)
                    .concat("</div>");
        }

        private String getEventThrowableEntry(final Throwable throwable) {
            return "<div class='".concat(eventMsgCssStyles).concat("' style='display:block;'>")
                    .concat("<textarea readonly class='code-block'>").concat(ExceptionUtil.getStackTrace(throwable))
                    .concat("</textarea>")
                    .concat("</div>");
        }

    }
}