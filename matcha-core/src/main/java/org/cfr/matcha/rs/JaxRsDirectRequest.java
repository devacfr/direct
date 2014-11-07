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
package org.cfr.matcha.rs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.cfr.matcha.IDirectContext;
import org.cfr.matcha.spi.AbstractDirectRequest;

import com.softwarementors.extjs.djn.router.RequestType;

public class JaxRsDirectRequest extends AbstractDirectRequest {

    /**
     *
     */
    private final BufferedReader reader;

    /**
     *
     */
    private final PrintWriter writer;

    /**
     *
     */
    private final String pathInfo;

    public JaxRsDirectRequest(final IDirectContext context, final RequestType requestType, final String pathInfo,
            final BufferedReader reader, final PrintWriter writer) {
        super(context, requestType);
        this.reader = reader;
        this.writer = writer;
        this.pathInfo = pathInfo;

    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return reader;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void setResponseContentType(final String contentType) {
        // nothing
    }

}
