/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.db.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface GetDataFromMongo {

	String dbName() default "";

	String collectionName() default "";

	String appName() default "";

	String tcName() default "";

	String envName() default ""; // get it from environment variable

	String dataType(); //config or testdata
}