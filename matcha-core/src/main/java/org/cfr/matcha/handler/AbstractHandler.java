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
package org.cfr.matcha.handler;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;
import org.cfr.matcha.IRequest;
import org.cfr.matcha.MatchaException;

import com.softwarementors.extjs.djn.router.RequestType;

public abstract class AbstractHandler implements IDirectHandler {

    /**
     *
     */
    public static final List<IDirectHandler> DEFAULT_DIRECT_HANDLERS =
            Arrays.<IDirectHandler> asList(new DirectHandler(), new UploadFormHandler());

    /**
     *
     * @return
     */
    public static List<IDirectHandler> getDefaultDirectHandlers() {
        return DEFAULT_DIRECT_HANDLERS;
    }

    /**
     * Must return a not null List of {@link RequestType} accepted by the handler.
     *
     * @return
     */
    public abstract List<RequestType> getAcceptedRequestType();

    /**
     *
     * @param type
     * @return
     */
    @Override
    public boolean acceptRequest(@Nonnull final IRequest request) {
        Assert.notNull(request, "request is required");
        return getAcceptedRequestType().contains(request.getRequestType());
    }

    /**
     *
     * @param handlerContext
     * @throws Exception
     */
    @Override
    public abstract void process(IRequest handlerContext) throws MatchaException;
}
