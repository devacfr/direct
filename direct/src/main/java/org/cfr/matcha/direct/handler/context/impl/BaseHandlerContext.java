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
package org.cfr.matcha.direct.handler.context.impl;

import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class BaseHandlerContext implements IDirectHandlerContext {

    /**
     *
     */
    private BaseDirectContext context;

    /**
     *
     */
    private RequestType requestType;

    /**
     *
     */
    private BaseHandlerContext() {
        super();
    }

    /**
     *
     * @param context
     * @param requestType
     */
    public BaseHandlerContext(final BaseDirectContext context, final RequestType requestType) {
        this();
        this.context = context;
        this.requestType = requestType;
    }

    /**
     * @return the context
     */
    @Override
    public BaseDirectContext getContext() {
        return context;
    }

    /**
     * @return the requestType
     */
    @Override
    public RequestType getRequestType() {
        return requestType;
    }

}
