package org.cfr.direct.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.cfr.direct.config.DirectConfiguration;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.servlet.BufferedRequestWrapper.BufferedServletInputStream;
import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;

public class DirectServletTest extends EasyMockTestCase {

    private ServletConfig servletConfig;

    private DirectServlet servlet;

    private ContextLoader contextLoader;

    private DirectContext directContext;

    private List<IDirectHandler> directHandlers;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        String jsApiFolderPath = null;
        File currentFile = FileUtils.toFile(this.getClass().getResource("."));
        while (jsApiFolderPath == null) {
            currentFile = currentFile.getParentFile();
            if (currentFile.getName().equals("target")) {
                jsApiFolderPath = currentFile.getAbsolutePath();
            }
        }

        ServletContext servletContext = mock(ServletContext.class);
        EasyMock.expect(servletContext.getRealPath("jsApiFolder")).andReturn(jsApiFolderPath);

        servletConfig = mock(ServletConfig.class);
        EasyMock.expect(servletConfig.getServletContext()).andReturn(servletContext).anyTimes();
        EasyMock.expect(servletConfig.getInitParameter(DirectServlet.API_JS_FOLDER_KEY)).andReturn("jsApiFolder").anyTimes();
        EasyMock.expect(servletConfig.getInitParameter(DirectServlet.PROVIDER_PATH_KEY)).andReturn("providersurl").anyTimes();
        EasyMock.expect(servletConfig.getInitParameter(DirectServlet.CONTEXT_NAME_KEY)).andReturn("contextName").anyTimes();

        WebApplicationContext context = mock(WebApplicationContext.class);

        contextLoader = mock(ContextLoader.class);
        EasyMock.expect(contextLoader.initWebApplicationContext(servletContext)).andReturn(context).anyTimes();

        final DefaultListableBeanFactory beanFactory = mock(DefaultListableBeanFactory.class);
        //        WebApplicationContext webApplicationContext = mock(WebApplicationContext.class);
        //        EasyMock.expect(webApplicationContext.getAutowireCapableBeanFactory()).andReturn(beanFactory).anyTimes();
        //        EasyMock.expect(ContextLoader.getCurrentWebApplicationContext().getAutowireCapableBeanFactory()).andReturn(beanFactory).anyTimes();

        servlet = new DirectServlet() {

            /**
             * 
             */
            private static final long serialVersionUID = -7369636151684573001L;

            //FIXME Find a real solution to mock the factory
            @Override
            protected DefaultListableBeanFactory createBeanFactory() {
                return beanFactory;
            }

            @Override
            protected ContextLoader createContextLoader() {
                return contextLoader;
            }

            @Override
            protected DirectContext createDirectContext() {
                return directContext;
            }
        };

        IDirectHandler handler = mock(IDirectHandler.class);

        directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);

        directContext = mock(DirectContext.class);
        DirectConfiguration configuration = mock(DirectConfiguration.class);
        GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);

        EasyMock.expect(configuration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();
        EasyMock.expect(directContext.getDirectConfiguration()).andReturn(configuration).anyTimes();
        EasyMock.expect(directContext.getApiConfigurations()).andReturn(new ArrayList<ApiConfiguration>()).anyTimes();
        EasyMock.expect(directContext.getDirectHandlers()).andReturn(directHandlers).anyTimes();

        Registry registry = mock(Registry.class);

        EasyMock.expect(directContext.getRegistry()).andReturn(registry).anyTimes();
    }

    @Test
    public void destroyTest() throws ServletException {
        replay();

        servlet.init(servletConfig);
        servlet.destroy();
        verify();
    }

    @Test
    public void postTest() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        expect(request.getContentType()).andReturn("multipart/blablatest").anyTimes();
        expect(request.getInputStream()).andReturn(new BufferedServletInputStream(new ByteArrayInputStream(new byte[] {}))).once();
        expect(request.getMethod()).andReturn("post").anyTimes();
        Enumeration<Object> header = mock(Enumeration.class);
        expect(request.getHeaderNames()).andReturn(header).anyTimes();

        HttpServletResponse response = mock(HttpServletResponse.class);
        replay();

        servlet.init(servletConfig);
        servlet.doPost(request, response);
        verify();

        //  assertEquals("Error while retrieve handlers", handlers, servlet.get);
    }
}
