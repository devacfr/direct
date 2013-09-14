package org.cfr.direct.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.context.IDirectHandlerContext;
import org.cfr.direct.handler.impl.DirectHandler;
import org.cfr.direct.handler.impl.DirectHandlerException;
import org.cfr.direct.servlet.ServletUtil;
import org.cfr.direct.servlet.context.DirectHandlerContext;
import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestRouter;
import com.softwarementors.extjs.djn.router.RequestType;

public class DirectHandlerTest extends EasyMockTestCase {

    private static List<RequestType> expectedTypes = DirectHandler.acceptedRequestTypeList;

    private DirectContext context;

    private HttpServletRequest request;

    private HttpServletResponse response;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = mock(DirectContext.class);
        RequestRouter requestRouter = mock(RequestRouter.class);

        EasyMock.expect(context.getRequestRouter()).andReturn(requestRouter).anyTimes();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        expect(request.getReader()).andReturn(new BufferedReader(new StringReader(""))).anyTimes();
        expect(response.getWriter()).andReturn(new PrintWriter(new StringWriter())).anyTimes();
    }

    @Test
    public void constructorDirectHandlerTest() {
        DirectHandler handler = new DirectHandler();
        assertNotNull("RequestType list must be at least an empty list", handler.getAcceptedRequestType());
        assertEquals("RequestType number for DirectHandler", expectedTypes.size(), handler.getAcceptedRequestType().size());
        for (RequestType expectedType : expectedTypes) {
            assertTrue("DirectHandler must support " + expectedType.name(), handler.acceptRequest(expectedType));
        }
    }

    @Test
    public void processAlltypeTest() {
        DirectHandler handler = new DirectHandler();
        replay();
        for (RequestType type : expectedTypes) {
            IDirectHandlerContext handlerContext = new DirectHandlerContext(context, type, request, response);
            handler.process(handlerContext);
        }
    }

    @Test(expected = DirectHandlerException.class)
    public void DirectHandlerExceptionTest() {
        DirectHandler handler = new DirectHandler();
        replay();
        for (RequestType type : expectedTypes) {
            IDirectHandlerContext handlerContext = new DirectHandlerContext(null, type, request, response);
            handler.process(handlerContext);
        }
    }

    @Test
    public void wrongRequestTypeTest() {
        DirectHandler handler = new DirectHandler();
        replay();
        IDirectHandlerContext handlerContext = new DirectHandlerContext(null, RequestType.FORM_UPLOAD_POST, request, response);
        handler.process(handlerContext);
    }

    @Test()
    public void defaultHandlerToolsTest() {

        expect(request.getContextPath()).andReturn("/");
        expect(request.getServletPath()).andReturn("/test");
        expect(request.getPathInfo()).andReturn("/");
        expect(request.getQueryString()).andReturn("http://localhost:8080/test");
        expect(request.getCharacterEncoding()).andReturn("utf-8");
        expect(request.getAuthType()).andReturn("BASIC");
        expect(request.getContentType()).andReturn("application/json");
        expect(request.getScheme()).andReturn("http");
        expect(request.getLocale()).andReturn(Locale.ENGLISH);

        EasyMock.expect(request.getMethod()).andReturn("post").anyTimes();
        replay();

        ServletUtil.getFromRequestContentType(request);
    }

    @Test
    public void handlerTools_POLLTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        EasyMock.expect(request.getPathInfo()).andReturn("/poll/blablatest");

        replay();

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify();

        assertEquals(RequestType.POLL, requestType);
    }

    @Test
    public void handlerTools_JSONTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        // EasyMock.expect(request.getPathInfo()).andReturn("");
        EasyMock.expect(request.getContentType()).andReturn("application/json");
        EasyMock.expect(request.getMethod()).andReturn("post").anyTimes();
        replay();

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify();

        assertEquals(RequestType.JSON, requestType);
    }

    @Test
    public void handlerTools_FORM_SIMPLE_POSTTypeTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        EasyMock.expect(request.getContentType()).andReturn("application/x-www-form-urlencoded");
        EasyMock.expect(request.getMethod()).andReturn("post");
        replay();

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify();

        assertEquals(RequestType.FORM_SIMPLE_POST, requestType);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handlerTools_FORM_UPLOAD_POSTTypeTest() throws Exception {

        EasyMock.expect(request.getContentType()).andReturn("multipart/blablatest").anyTimes();
        EasyMock.expect(request.getMethod()).andReturn("post").anyTimes();

        Enumeration<Object> header = mock(Enumeration.class);
        EasyMock.expect(request.getHeaderNames()).andReturn(header).anyTimes();

        replay();

        RequestType requestType = ServletUtil.getFromRequestContentType(request);

        verify();

        assertEquals(RequestType.FORM_UPLOAD_POST, requestType);
    }
}
