package com.liashenko.app.web.authorization;

import java.lang.annotation.*;

/**
 * Annotation contains annotations to mark commands
 * should be maneged by RightChecker
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Authorization {

    /**
     * Annotation used to allow call annotated command by users, counted in the "roles" array.
     * If user's role is not present in the list, he will be redirected to command in the "defAction"
     *
     * @method description - used to describe the purpose of using annotation, some additional details
     */
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface Allowed {
        long[] roles();

        String defAction() default "";

        String description() default "";
    }

    /**
     * Annotation used to restrict calling annotated commands by users, counted in the "roles" array.
     * If user's role is present in the list, he will be redirected to command specified in the "action".
     * If command is annotated more than one time and the same role presents in several lists of restricted roles,
     * would be used action from the annotation with higher value of "priority".
     * If the values of priorities are equal would be used value of "action" from the annotation
     * which happens first in from top-to-bottom direction
     *
     * @method description - used to describe the purpose of using annotation, some additional details
     */
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Repeatable(Restrictions.class)
    @interface Restricted {
        long[] roles();

        String action();

        int priority() default 0;

        String description() default "";
    }

    //Container for repeatable using Restricted annotation
    @Target(value = ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Restrictions {
        Restricted[] value();
    }
}
