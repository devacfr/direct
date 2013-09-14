package org.cfr.direct.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.rs.context.DirectJaxRsHandlerContext;
import org.cfr.direct.testing.EasyMockTestCase;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestType;

public class HandlerContextTest extends EasyMockTestCase {

    @Test
    public void constructorTest() throws IOException {
        DirectContext context = mock(DirectContext.class);
        RequestType requestType = RequestType.JSON;
        String pathInfo = "pathInfo";
        BufferedReader reader = mock(BufferedReader.class);
        PrintWriter writer = mock(PrintWriter.class);

        DirectJaxRsHandlerContext handlerContext = new DirectJaxRsHandlerContext(context, requestType, pathInfo, reader, writer);

        assertEquals(context, handlerContext.getContext());
        assertEquals(requestType, handlerContext.getRequestType());
        assertEquals(pathInfo, handlerContext.getPathInfo());
        assertEquals(reader, handlerContext.getReader());
        assertEquals(writer, handlerContext.getWriter());

    }
}
