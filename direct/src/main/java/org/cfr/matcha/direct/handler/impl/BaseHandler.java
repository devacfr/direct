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
package org.cfr.matcha.direct.handler.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.router.RequestType;

public abstract class BaseHandler implements IDirectHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static List<IDirectHandler> DefaultDirectHandlers = Arrays.<IDirectHandler> asList(new DirectHandler(),
        new UploadFormHandler());

    /**
     * Must return a not null List of {@link RequestType} accepted by the
     * handler
     * 
     * @return
     */
    public abstract List<RequestType> getAcceptedRequestType();

    public boolean acceptRequest(RequestType type) {
        return getAcceptedRequestType().contains(type);
    }

    @Override
    public final void process(IDirectHandlerContext handlerContext) {
        try {
            if (acceptRequest(handlerContext.getRequestType())) {
                doProcess(handlerContext);
            }
        } catch (Exception e) {
            LOGGER.error("Handler error during process", e);
            handleException(e, "Handler error during process");
        }
    }

    protected void handleException(Exception e, String message) {
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        throw new DirectHandlerException(message, e);
    }

    protected abstract void doProcess(IDirectHandlerContext handlerContext) throws Exception;
}
