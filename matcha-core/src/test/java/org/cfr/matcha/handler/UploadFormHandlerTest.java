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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.testing.MockitoTestCase;
import org.cfr.matcha.IDirectContext;
import org.cfr.matcha.IRequest;
import org.cfr.matcha.MatchaException;
import org.cfr.matcha.handler.UploadFormHandler;
import org.cfr.matcha.handler.processor.form.UploadFormPostRequestProcessor;
import org.cfr.matcha.rs.JaxRsDirectRequest;
import org.cfr.matcha.servlet.HttpServletDirectRequest;
import org.cfr.matcha.spi.DirectRequestRouter;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestType;

public class UploadFormHandlerTest extends MockitoTestCase {

    private static List<RequestType> expectedTypes = Arrays.asList(RequestType.FORM_UPLOAD_POST);

    private IDirectContext context;

    private HttpServletRequest request;

    private HttpServletResponse response;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        context = mock(IDirectContext.class);

        DirectRequestRouter requestRouter = mock(DirectRequestRouter.class);

        when(context.getRequestRouter()).thenReturn(requestRouter);

        UploadFormPostRequestProcessor processor = mock(UploadFormPostRequestProcessor.class);
        when(requestRouter.createUploadFromProcessor()).thenReturn(processor);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

    }

    @Test
    public void constructorUploadFormHandlerTest() {
        UploadFormHandler handler = new UploadFormHandler();
        assertNotNull("RequestType list must be at least an empty list", handler.getAcceptedRequestType());
        assertEquals("RequestType number for UploadFormHandler", expectedTypes.size(), handler.getAcceptedRequestType()
                .size());
        for (RequestType expectedType : expectedTypes) {
            IRequest request = mock(IRequest.class);
            stub(request.getRequestType()).toReturn(expectedType);
            assertTrue("UploadFormHandler must support " + expectedType.name(), handler.acceptRequest(request));
        }
    }

    @Test
    public void processTest() throws Exception {
        UploadFormHandler handler = new UploadFormHandler();
        for (RequestType type : expectedTypes) {
            IRequest directRequest = new HttpServletDirectRequest(context, type, request, response);
            handler.process(directRequest);
        }
    }

    @Test(expected = MatchaException.class)
    public void processWithWrongHandlerTest() {
        UploadFormHandler handler = new UploadFormHandler();

        for (RequestType type : expectedTypes) {
            IRequest request = new JaxRsDirectRequest(context, type, "", null, null);
            handler.process(request);
        }
    }

}
