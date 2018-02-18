package com.liashenko.app.web.authorization;


import java.lang.annotation.Annotation;
import java.util.Map;

import static com.liashenko.app.utils.Asserts.assertIsNull;

/**
 * @param <T> - any object type annotated with Authorization inner (Allowed or Restrict) annotation
 * @version 1.0
 * @Author Vitalii Liashenko
 * <p>
 * Class contains logic to resolve allowed command annotated with Authorization for user which have relevant role
 * <p>
 * Annotation Authorization.Allowed used to allow call annotated command by users, counted in the "roles" array.
 * If user's role is not present in the list, he will be redirected to command in the "defAction"
 * <p>
 * Annotation Authorization.Restrict used to restrict calling annotated commands by users, counted in the "roles" array.
 * If user's role is present in the list, he will be redirected to command specified in the "action".
 * <p>
 * If command is annotated more than one time and the same role presents in several lists of restricted roles,
 * would be used action from the annotation with higher value of "priority".
 * <p>
 * If the values of priorities are equal would be used value of "action" from the annotation
 * which happens first in from top-to-bottom direction
 * <p>
 * Annotation Authorization.Restrict always has higher priority above Authorization.Allowed
 * <p>
 * If user role is absent in the lists of Authorization annotations fields he would be redirected to defaultAction
 */
public abstract class RightsChecker<T> {

    /**
     * Method resolves allowed for user command to process request
     *
     * @param actualAction  - name of the requested by user action
     * @param defaultAction - default action would be used if not annotation Allowed nor Restrict has users'r role in it's list
     * @param currentRole   - current user's role
     * @param commands      - Map with whole list of commands
     * @param <T>           - any object type annotated with Authorization inner (Allowed or Restrict) annotation
     * @return - allowed for user action
     */
    public static <T> T checkUserRightsAndGetCommand(String actualAction, String defaultAction, Long currentRole,
                                                     Map<String, T> commands) {
        T requestedCommand = commands.get(actualAction);
        if (requestedCommand == null) {
            return commands.get(defaultAction);
        }
        String resultAction = getAction(actualAction, requestedCommand, currentRole);
        return commands.get(resultAction);
    }

    private static <T> String getAction(String actualAction, T command, Long currentRole) {
        String resultAction = null;
        Class clazz = command.getClass();
        String restrictedAction = getRestrictedAction(clazz, currentRole);
        String allowedAction = getAllowedAction(actualAction, clazz, currentRole);
        if (assertIsNull(restrictedAction)) {
            resultAction = assertIsNull(allowedAction) ? actualAction : allowedAction;
        } else {
            resultAction = restrictedAction;
        }
        return resultAction;
    }

    //resolves Authorization.Allowed annotation
    private static String getAllowedAction(String actualAction, Class clazz, Long currentRole) {
        String resultAction = null;
        if (clazz.isAnnotationPresent(Authorization.Allowed.class)) {
            Annotation annotation = clazz.getAnnotation(Authorization.Allowed.class);
            Authorization.Allowed authAllowedAnnot = (Authorization.Allowed) annotation;

            String defActionValue = authAllowedAnnot.defAction();
            if (!defActionValue.isEmpty()) {
                resultAction = defActionValue;
            }
            long[] rolesValues = authAllowedAnnot.roles();
            if (rolesValues.length > 0) {
                for (long role : rolesValues) {
                    if (role == currentRole) {
                        resultAction = actualAction;
                        break;
                    }
                }
            }
        }
        return resultAction;
    }

    //resolves Authorization.Restrict annotation
    private static String getRestrictedAction(Class clazz, Long currentRole) {
        String resultAction = null;
        int priority = Integer.MIN_VALUE;
        if (clazz.isAnnotationPresent(Authorization.Restricted.class)) {
            Annotation[] annotations = clazz.getAnnotationsByType(Authorization.Restricted.class);
            for (Annotation annotation : annotations) {
                Authorization.Restricted authRestrictAnnot = (Authorization.Restricted) annotation;
                int currentActionPriority = authRestrictAnnot.priority();
                long[] rolesValues = authRestrictAnnot.roles();
                if (rolesValues.length > 0) {
                    for (long role : rolesValues) {
                        if (role == currentRole) {
                            resultAction = authRestrictAnnot.action();
                            if (currentActionPriority > priority) {
                                priority = currentActionPriority;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return resultAction;
    }
}
