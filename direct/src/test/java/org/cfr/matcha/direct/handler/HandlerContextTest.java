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
package org.cfr.matcha.direct.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.direct.rs.context.DirectJaxRsHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;
import org.junit.Test;

import com.softwarementors.extjs.djn.router.RequestType;

public class HandlerContextTest extends EasyMockTestCase {

    @Test
    public void constructorTest() throws IOException {
        BaseDirectContext context = mock(BaseDirectContext.class);
        RequestType requestType = RequestType.JSON;
        String pathInfo = "pathInfo";
        BufferedReader reader = mock(BufferedReader.class);
        PrintWriter writer = mock(PrintWriter.class);

        DirectJaxRsHandlerContext handlerContext =
                new DirectJaxRsHandlerContext(context, requestType, pathInfo, reader, writer);

        assertEquals(context, handlerContext.getContext());
        assertEquals(requestType, handlerContext.getRequestType());
        assertEquals(pathInfo, handlerContext.getPathInfo());
        assertEquals(reader, handlerContext.getReader());
        assertEquals(writer, handlerContext.getWriter());

    }
}
