package org.cfr.matcha.direct.handler;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.handler.impl.DirectHandlerException;
import org.cfr.matcha.direct.handler.impl.DirectRequestRouter;
import org.cfr.matcha.direct.handler.impl.UploadFormHandler;
import org.cfr.matcha.direct.handler.processor.form.UploadFormPostRequestProcessor;
import org.cfr.matcha.direct.rs.context.DirectJaxRsHandlerContext;
import org.cfr.matcha.direct.servlet.context.DirectHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;
import org.easymock.EasyMock;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestType;

public class UploadFormHandlerTest extends EasyMockTestCase {

    private static List<RequestType> expectedTypes = Arrays.asList(RequestType.FORM_UPLOAD_POST);

    private BaseDirectContext context;

    private HttpServletRequest request;

    private HttpServletResponse response;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        context = mock(BaseDirectContext.class);

        DirectRequestRouter requestRouter = mock(DirectRequestRouter.class);

        EasyMock.expect(context.getRequestRouter()).andReturn(requestRouter).anyTimes();

        UploadFormPostRequestProcessor processor = mock(UploadFormPostRequestProcessor.class);
        EasyMock.expect(requestRouter.createUploadFromProcessor()).andReturn(processor).anyTimes();

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
            assertTrue("UploadFormHandler must support " + expectedType.name(), handler.acceptRequest(expectedType));
        }
    }

    @Test
    public void processTest() {
        UploadFormHandler handler = new UploadFormHandler();
        replay();
        for (RequestType type : expectedTypes) {
            IDirectHandlerContext handlerContext = new DirectHandlerContext(context, type, request, response);
            handler.process(handlerContext);
        }
    }

    @Test(expected = DirectHandlerException.class)
    public void processWithWrongHandlerTest() {
        UploadFormHandler handler = new UploadFormHandler();
        replay();
        for (RequestType type : expectedTypes) {
            IDirectHandlerContext handlerContext = new DirectJaxRsHandlerContext(context, type, "", null, null);
            handler.process(handlerContext);
        }
    }

    @Test(expected = DirectHandlerException.class)
    public void UploadFormHandlerExceptionTest() {
        UploadFormHandler handler = new UploadFormHandler();
        replay();
        for (RequestType type : expectedTypes) {
            IDirectHandlerContext handlerContext = new DirectHandlerContext(null, type, request, response);
            handler.process(handlerContext);
        }
    }

    @Test
    public void wrongRequestTypeTest() {
        UploadFormHandler handler = new UploadFormHandler();
        replay();
        IDirectHandlerContext handlerContext = new DirectHandlerContext(null, RequestType.JSON, request, response);
        handler.process(handlerContext);
    }
}
