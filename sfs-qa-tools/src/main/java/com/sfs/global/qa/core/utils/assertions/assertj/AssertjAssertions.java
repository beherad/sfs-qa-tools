package com.sfs.global.qa.core.utils.assertions.assertj;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

import com.sfs.global.qa.core.config.TafConfig;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class AssertjAssertions extends AssertjHardAssertions {

    private static final boolean LOG_DESCRIPTIONS = TafConfig.ASSERTJ_ASSERTIONS_LOG_DESCRIPTIONS.isTrue();

    public AssertjAssertions() {
        if (LOG_DESCRIPTIONS) {
            setDescriptionConsumer(desc -> {
                if (desc != null) {
                    log.info("[Assertion] {}", desc);
                }
            });
        }
    }

    public SoftAssertions soft() {
        if (LOG_DESCRIPTIONS) {
            setDescriptionConsumer(desc -> {
                if (desc != null) {
                    log.info("[Assertion] [Soft] {}", desc);
                }
            });
        }
        return AssertjSoftAssertions.get();
    }

    public <T> T soft(Class<T> returnType) {
        try {
            return returnType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            log.error("Failed to instantiate soft assertion class: {}", returnType.getName(), e);
        }
        return null;
    }

    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class AssertjSoftAssertions {

        private static final ThreadLocal<SoftAssertions> THREAD_LOCAL = ThreadLocal.withInitial(SoftAssertions::new);

        public static SoftAssertions get() {
            return THREAD_LOCAL.get();
        }

        public static void removeAll() {
            THREAD_LOCAL.remove();
        }

        public static void assertAll() {
            SoftAssertions assertions = THREAD_LOCAL.get();
            if (assertions != null) {
                log.debug("Validating specified soft assertions...");
                assertions.assertAll();
            }
            removeAll();
        }
    }
}
