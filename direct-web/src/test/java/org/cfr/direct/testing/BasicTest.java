package org.cfr.direct.testing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.impl.DirectHandler;
import org.cfr.direct.servlet.DirectServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:direct-test-beans-definition.xml" })
public class BasicTest extends EasyMockTestCase implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private ServletConfig config;

    private String path;

    private ContextLoader contextLoader;

    private DirectContext directContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File currentFile = FileUtils.toFile(this.getClass().getResource("."));
        while (path == null) {
            currentFile = currentFile.getParentFile();
            if (currentFile.getName().equals("target")) {
                path = currentFile.getAbsolutePath();
            }
        }
        File jsApiFolder = new File(path, "jsApiFolder");

        config = mock(MockServletConfig.class);

        ServletContext servletContext = mock(ServletContext.class);

        String jsApiFolderStr = "jsApiFolder";
        expect(servletContext.getRealPath(jsApiFolderStr)).andReturn(jsApiFolder.getAbsolutePath());

        expect(config.getServletName()).andReturn("servletDirect").times(2);
        expect(config.getInitParameter(DirectServlet.API_JS_FOLDER_KEY)).andReturn(jsApiFolderStr);
        expect(config.getInitParameter(DirectServlet.PROVIDER_PATH_KEY)).andReturn("providersurl").times(2);
        expect(config.getServletContext()).andReturn(servletContext).anyTimes();

        contextLoader = mock(ContextLoader.class);

        directContext = new DirectContext();

    }

    @Test
    public void initServletTest() throws Exception {
        final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        DirectServlet servlet = new DirectServlet() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

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

        List<IDirectHandler> handlers = new ArrayList<IDirectHandler>();
        DirectHandler handler = new DirectHandler();
        handlers.add(handler);
        directContext.setDirectHandlers(handlers);
        replay();
        servlet.init(config);

        verify();
    }
}
