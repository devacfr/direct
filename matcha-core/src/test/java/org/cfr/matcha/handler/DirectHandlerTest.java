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
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.testing.MockitoTestCase;
import org.cfr.matcha.IRequest;
import org.cfr.matcha.handler.DirectHandler;
import org.cfr.matcha.servlet.HttpServletDirectRequest;
import org.cfr.matcha.servlet.ServletUtil;
import org.cfr.matcha.spi.BaseDirectContext;
import org.cfr.matcha.spi.DirectRequestRouter;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectHandlerTest extends MockitoTestCase {

    private static List<RequestType> expectedTypes = DirectHandler.ACCEPTED_REQUEST_TYPE_LIST;

    private BaseDirectContext context;

    private HttpServletRequest request;

    private HttpServletResponse response;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = mock(BaseDirectContext.class);
        DirectRequestRouter requestRouter = mock(DirectRequestRouter.class);

        when(context.getRequestRouter()).thenReturn(requestRouter);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("")));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
    }

    @Test
    public void constructorDirectHandlerTest() {
        DirectHandler handler = new DirectHandler();
        assertNotNull("RequestType list must be at least an empty list", handler.getAcceptedRequestType());
        assertEquals("RequestType number for DirectHandler", expectedTypes.size(), handler.getAcceptedRequestType()
                .size());
        for (RequestType expectedType : expectedTypes) {
            IRequest r = mock(IRequest.class);
            stub(r.getRequestType()).toReturn(expectedType);
            assertTrue("DirectHandler must support " + expectedType.name(), handler.acceptRequest(r));
        }
    }

    @Test
    public void processAlltypeTest() throws Exception {
        DirectHandler handler = new DirectHandler();
        for (RequestType type : expectedTypes) {
            IRequest directRequest = new HttpServletDirectRequest(context, type, request, response);
            handler.process(directRequest);
        }
    }

    @Test(expected = RuntimeException.class)
    public void wrongRequestTypeTest() throws Exception {
        DirectHandler handler = new DirectHandler();
        IRequest directRequest =
                new HttpServletDirectRequest(null, RequestType.FORM_UPLOAD_POST, request, response);
        handler.process(directRequest);
    }

    @Test()
    public void defaultHandlerToolsTest() {

        when(request.getContextPath()).thenReturn("/");
        when(request.getServletPath()).thenReturn("/test");
        when(request.getPathInfo()).thenReturn("/");
        when(request.getQueryString()).thenReturn("http://localhost:8080/test");
        when(request.getCharacterEncoding()).thenReturn("utf-8");
        when(request.getAuthType()).thenReturn("BASIC");
        when(request.getContentType()).thenReturn("application/json");
        when(request.getScheme()).thenReturn("http");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        when(request.getMethod()).thenReturn("post");

        ServletUtil.getFromRequestContentType(request);
    }

    @Test
    public void handlerTools_POLLTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/poll/blablatest");

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify(request, times(1)).getPathInfo();

        assertEquals(RequestType.POLL, requestType);
    }

    @Test
    public void handlerTools_JSONTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContentType()).thenReturn("application/json");
        when(request.getMethod()).thenReturn("post");

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify(request, times(1)).getContentType();

        assertEquals(RequestType.JSON, requestType);
    }

    @Test
    public void handlerTools_FORM_SIMPLE_POSTTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContentType()).thenReturn("application/x-www-form-urlencoded");
        when(request.getMethod()).thenReturn("post");

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify(request, times(1)).getContentType();

        assertEquals(RequestType.FORM_SIMPLE_POST, requestType);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handlerTools_FORM_UPLOAD_POSTTypeTest() throws Exception {

        when(request.getContentType()).thenReturn("multipart/blablatest");
        when(request.getMethod()).thenReturn("post");

        Enumeration<Object> header = mock(Enumeration.class);
        when(request.getHeaderNames()).thenReturn(header);

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        assertEquals(RequestType.FORM_UPLOAD_POST, requestType);
    }

}
