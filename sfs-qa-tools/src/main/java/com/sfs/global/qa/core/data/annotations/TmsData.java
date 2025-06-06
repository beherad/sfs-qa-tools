package com.sfs.global.qa.core.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sfs.global.qa.core.data.enums.TcType;

/**
 * TmsData annotation is used to sync test classes and methods with test management system (TMS) data (e.g. test suites & test cases).
 *
 * @author Monalis Behera
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TmsData {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Ts {

        String tsId() default "";
        String tsName() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Tc {

        int tcId() default 0;
        int[] tcIds() default {};
        String tcName() default "";
        String[] tcNames() default {};
        TcType tcType() default TcType.UNDEFINED;
    }

}