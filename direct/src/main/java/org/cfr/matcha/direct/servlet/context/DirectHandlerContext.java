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
package org.cfr.matcha.direct.servlet.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.matcha.direct.handler.context.impl.BaseHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectHandlerContext extends BaseHandlerContext {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public DirectHandlerContext(final BaseDirectContext context, final RequestType requestType,
                                final HttpServletRequest request, HttpServletResponse response) {
        super(context, requestType);
        this.request = request;
        this.response = response;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    public String getPathInfo() {
        return request.getPathInfo();
    }

    @Override
    public void setResponseContentType(final String contentType) {
        this.response.setContentType(contentType);
    }

}
