package com.liashenko.app.persistance.result_parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark entity columns should be parsed
 * from the ResultSet
 *
 * @version 1.0
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * @return name of the column from the relevant to the Entity table,
     * corresponds to the annotated filed
     */
    String name();

    /**
     * @return - locale suffix for the column in the table
     * corresponds to the annotated filed
     */
    boolean useLocaleSuffix() default false;
}
