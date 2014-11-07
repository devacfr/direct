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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.cfr.matcha.IDirectContext;
import org.cfr.matcha.IRequest;
import org.cfr.matcha.MatchaException;

import com.softwarementors.extjs.djn.router.RequestType;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class DirectHandler extends AbstractHandler {

    /**
     *
     */
    public final static List<RequestType> ACCEPTED_REQUEST_TYPE_LIST =
            Collections.unmodifiableList(Arrays.asList(RequestType.FORM_SIMPLE_POST,
                RequestType.JSON,
                RequestType.POLL,
                RequestType.SOURCE));

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RequestType> getAcceptedRequestType() {
        return ACCEPTED_REQUEST_TYPE_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final IRequest handlerContext) throws MatchaException {

        final IDirectContext context = handlerContext.getContext();
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = handlerContext.getReader();
            writer = handlerContext.getWriter();
            switch (handlerContext.getRequestType()) {
                case FORM_SIMPLE_POST:
                    handlerContext.setResponseContentType(IDirectContext.JSON_CONTENT_TYPE);
                    context.getRequestRouter().processSimpleFormPostRequest(reader, writer);
                    break;
                case JSON:
                    handlerContext.setResponseContentType(IDirectContext.JSON_CONTENT_TYPE);
                    context.getRequestRouter().processJsonRequest(reader, writer);
                    break;
                case POLL:
                    handlerContext.setResponseContentType(IDirectContext.JSON_CONTENT_TYPE);
                    context.getRequestRouter().processPollRequest(reader, writer, handlerContext.getPathInfo());
                    break;
                case SOURCE:
                    handlerContext.setResponseContentType(IDirectContext.JAVASCRIPT_CONTENT_TYPE);
                    context.getRequestRouter().processSourceRequest(reader, writer, handlerContext.getPathInfo());
                    break;
                case FORM_UPLOAD_POST:
                    throw new RuntimeException("This Handler doesn't treat file uploading");
                default:
                    break;
            }
        } catch (IOException e) {
            throw new MatchaException("Error in processing of request", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }
}
