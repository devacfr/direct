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
package org.cfr.matcha.handler.processor;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;

import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredStandardMethod;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.RequestProcessorBase;
import com.softwarementors.extjs.djn.router.processor.standard.StandardErrorResponseData;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class StandardRequestProcessorBase extends RequestProcessorBase {

    /**
     *
     * @param registry
     * @param dispatcher
     * @param globalConfiguration
     */
    protected StandardRequestProcessorBase(final Registry registry, final Dispatcher dispatcher,
            final GlobalConfiguration globalConfiguration) {
        super(registry, dispatcher, globalConfiguration);
    }

    /**
     *
     * @param request
     * @param t
     * @return
     */
    protected StandardErrorResponseData createJsonServerErrorResponse(@Nonnull final StandardRequestData request,
            @Nonnull final Throwable t) {
        Assert.notNull(request);
        Assert.notNull(t);

        StandardErrorResponseData response = new StandardErrorResponseData(request.getTid(), request.getAction(),//
                request.getMethod(), t, getDebug());
        return response;
    }

    protected RegisteredStandardMethod getStandardMethod(@Nonnull final String actionName,
            @Nonnull final String methodName) {
        Assert.hasText(actionName);
        Assert.hasText(methodName);

        RegisteredAction action = getRegistry().getAction(actionName);
        if (action == null) {
            throw RequestException.forActionNotFound(actionName);
        }

        RegisteredStandardMethod method = action.getStandardMethod(methodName);
        if (method == null) {
            throw RequestException.forActionMethodNotFound(action.getName(), methodName);
        }
        return method;
    }

    protected Object dispatchStandardMethod(@Nonnull final String actionName, @Nonnull final String methodName,
            @Nonnull final Object[] parameters) {
        Assert.hasText(actionName);
        Assert.hasText(methodName);
        Assert.notNull(parameters);

        RegisteredStandardMethod method = getStandardMethod(actionName, methodName);
        Object result = getDispatcher().dispatch(method, parameters);
        return result;
    }

}
