/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.matcha.direct.spi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cfr.commons.util.Assert;
import org.cfr.matcha.api.direct.DirectFormPostMethod;
import org.cfr.matcha.api.direct.DirectMethod;
import org.cfr.matcha.api.direct.DirectPollMethod;
import org.cfr.matcha.api.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.ClassUtils;
import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.api.RegisteredPollMethod;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfigurationException;
import com.softwarementors.extjs.djn.scanner.Scanner;

/**
 * Extends {@link Scanner} to replace {@link Map} by {@link Form} parameter in Form call.
 *
 * @author devacfr
 * @since 1.0
 */
public class DirectScanner extends Scanner {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(DirectScanner.class);

    /**
     *
     */
    private final Registry registry;

    public DirectScanner(final Registry registry) {
        super(registry);
        this.registry = registry;
    }

    @Override
    public void scanAndRegisterActionClass(RegisteredApi api, Class<?> actionClass) {
        Assert.notNull(api);
        Assert.notNull(actionClass);

        List<RegisteredAction> actions = createActionsFromJavaClass(api, actionClass);
        scanAndRegisterActionClass(actions);
    }

    private void scanAndRegisterActionClass(List<RegisteredAction> actions) {
        assert actions != null;
        assert !actions.isEmpty();

        RegisteredAction actionTemplate = actions.get(0);

        // *All* methods are candidates, including those in base classes,
        // even if the base class does not have a DirectAction annotation!
        List<Method> allMethods = new ArrayList<Method>();
        Class<?> cls = actionTemplate.getActionClass();
        while (cls != null) {
            Method[] methods = cls.getDeclaredMethods(); // Get private, protected and other methods!
            Collections.addAll(allMethods, methods);
            cls = cls.getSuperclass();
        }

        for (Method method : allMethods) {
            // Check if the kind of direct method -if any
            DirectMethod methodAnnotation = method.getAnnotation(DirectMethod.class);
            boolean isStandardMethod = methodAnnotation != null;

            DirectFormPostMethod postMethodAnnotation = method.getAnnotation(DirectFormPostMethod.class);
            boolean isFormPostMethod = postMethodAnnotation != null;

            DirectPollMethod pollMethodAnnotation = method.getAnnotation(DirectPollMethod.class);
            boolean isPollMethod = pollMethodAnnotation != null;

            // Check that a method is just of only one kind of method
            if (isStandardMethod && isFormPostMethod) {
                ApiConfigurationException ex =
                        ApiConfigurationException.forMethodCantBeStandardAndFormPostMethodAtTheSameTime(actionTemplate,
                            method);
                logger.error(ex.getMessage(), ex);
                throw ex;
            }
            if ((methodAnnotation != null || postMethodAnnotation != null) && isPollMethod) {
                ApiConfigurationException ex =
                        ApiConfigurationException.forPollMethodCantBeStandardOrFormPostMethodAtTheSameTime(actionTemplate,
                            method);
                logger.error(ex.getMessage(), ex);
                throw ex;
            }

            // Process standard and form post methods together, as they are very similar
            if (isStandardMethod || isFormPostMethod) {

                String methodName = "";
                if (isStandardMethod) {
                    methodName = getStandardMethodName(method, methodAnnotation);
                } else {
                    methodName = getFormPostMethodName(method, postMethodAnnotation);
                }

                if (actionTemplate.hasStandardMethod(methodName)) {
                    ApiConfigurationException ex =
                            ApiConfigurationException.forMethodAlreadyRegisteredInAction(methodName,
                                actionTemplate.getName());
                    logger.error(ex.getMessage(), ex);
                    throw ex;
                }

                if (isFormPostMethod && !isValidFormHandlingMethod(method)) {
                    ApiConfigurationException ex =
                            ApiConfigurationException.forMethodHasWrongParametersForAFormHandler(actionTemplate.getName(),
                                methodName);
                    logger.error(ex.getMessage(), ex);
                    throw ex;
                }

                for (RegisteredAction actionToRegister : actions) {
                    actionToRegister.addStandardMethod(methodName, method, isFormPostMethod);
                }
            }

            // Process "poll" method
            if (isPollMethod) {
                for (RegisteredAction actionToRegister : actions) {
                    createPollMethod(actionToRegister, method, pollMethodAnnotation);
                }
            }
        }
    }

    /**
     *
     * @param method
     * @param postMethodAnnotation
     * @return
     */
    private static String getFormPostMethodName(final Method method, final DirectFormPostMethod postMethodAnnotation) {
        return method.getName();
    }

    /**
     *
     * @param method
     * @param methodAnnotation
     * @return
     */
    private static String getStandardMethodName(final Method method, final DirectMethod methodAnnotation) {
        return method.getName();
    }

    /**
     *
     * @param action
     * @param method
     * @param pollMethodAnnotation
     * @return
     */
    private RegisteredPollMethod createPollMethod(RegisteredAction action, Method method,
                                                  DirectPollMethod pollMethodAnnotation) {
        Assert.notNull(action);
        Assert.notNull(method);

        String eventName = getEventName(method, pollMethodAnnotation);

        if (this.registry.hasPollMethod(eventName)) {
            ApiConfigurationException ex = ApiConfigurationException.forPollEventAlreadyRegistered(eventName);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }

        if (!RegisteredPollMethod.isValidPollMethod(method)) {
            ApiConfigurationException ex = ApiConfigurationException.forMethodHasWrongParametersForAPollHandler(method);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }

        RegisteredPollMethod poll = action.addPollMethod(eventName, method);
        return poll;
    }

    /**
     *
     * @param method
     * @param pollMethodAnnotation
     * @return
     */
    private static String getEventName(Method method, DirectPollMethod pollMethodAnnotation) {
        Assert.notNull(method);
        return method.getName();
    }

    /**
     *
     * @param api
     * @param actionClass
     * @return
     */
    private List<RegisteredAction> createActionsFromJavaClass(RegisteredApi api, Class<?> actionClass) {
        assert api != null;
        assert actionClass != null;
        List<String> actionNames = new ArrayList<String>();

        if (actionNames.isEmpty()) {
            actionNames.add(ClassUtils.getSimpleName(actionClass));
        }

        List<RegisteredAction> actions = new ArrayList<RegisteredAction>();
        for (String actionName : actionNames) {
            if (this.registry.hasAction(actionName)) {
                RegisteredAction existingAction = this.registry.getAction(actionName);
                ApiConfigurationException ex =
                        ApiConfigurationException.forActionAlreadyRegistered(actionName,
                            actionClass,
                            existingAction.getActionClass());
                logger.error(ex.getMessage(), ex);
                throw ex;
            }
            RegisteredAction action = api.addAction(actionClass, actionName);
            actions.add(action);
        }

        return actions;
    }

    /**
     *
     * @param method
     * @return
     */
    public static boolean isValidFormHandlingMethod(Method method) {
        assert method != null;

        return method.getParameterTypes().length == 2 && method.getParameterTypes()[0].equals(Form.class)
                || !method.getParameterTypes()[1].equals(Map.class);
    }
}
