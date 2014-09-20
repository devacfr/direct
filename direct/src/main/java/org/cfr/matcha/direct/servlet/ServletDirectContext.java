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
package org.cfr.matcha.direct.servlet;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.commons.util.Assert;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.servlet.context.DirectHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

@Named("DirectContext")
@Singleton
public class ServletDirectContext extends BaseDirectContext implements IServletDirectContext {

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws Exception {
        Assert.hasText(getJsApiPath(), "defaultJsApiPath is required");
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleProcess(final HttpServletRequest request, final HttpServletResponse response) {
        RequestType type = ServletUtil.getFromRequestContentType(request);
        IDirectHandlerContext handlerContext = new DirectHandlerContext(this, type, request, response);
        for (IDirectHandler handler : getDirectHandlers()) {
            handler.process(handlerContext);
        }
        handlerContext = null;
    }

}
