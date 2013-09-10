package org.cfr.direct.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.context.IDirectHandlerContext;
import org.cfr.direct.servlet.context.DirectHandlerContext;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;
import com.softwarementors.extjs.djn.jscodegen.CodeFileGenerator;
import com.softwarementors.extjs.djn.router.RequestType;
import com.softwarementors.extjs.djn.servlet.DirectJNgineServlet;
import com.softwarementors.extjs.djn.servlet.ServletUtils;

public class DirectServlet extends DirectJNgineServlet {

    /**serialVersionUID*/
    private static final long serialVersionUID = -2765616904505520650L;

    /** spring beanName prefix key */
    private static final String PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT = "djnServlet.directContext.";

    /**  Facultativ param to define the destination folder for generated js Api */
    public static final String API_JS_FOLDER_KEY = "direct.api-js.folder";

    /** Mandatory param to define servlet mapping in generated js Api */
    public static final String PROVIDER_PATH_KEY = "direct.provider.path";

    public static final String PROVIDER_NAMESPACE_KEY = "direct.ns";

    public static final String CONTEXT_NAME_KEY = "context.name";

    public static final String SERVLET_PATH_KEY = "direct.path";

    /**
     * Spring Context
     */
    private ContextLoader contextLoader;

    /**
     * Action Context
     */
    private DirectContext directContext;

    private long id = 1000; // It is good for formatting to get lots of ids with the same number of digits...

    public synchronized long getUniqueRequestId() {
        return this.id++;
    }

    @Override
    protected void createDirectJNgineRouter(ServletConfig servletConfig) throws ServletException {

        if (ContextLoader.getCurrentWebApplicationContext() == null) {
            this.contextLoader = createContextLoader();
            this.contextLoader.initWebApplicationContext(servletConfig.getServletContext());
        }

        // create beanFactory if not exist
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        // create directContext if not exist
        if (directContext == null) {
            directContext = createDirectContext();
        }

        // Autowired directContext
        beanFactory.autowireBeanProperties(this.directContext, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        beanFactory.initializeBean(this.directContext, PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT + servletConfig.getServletName());

        String JsFolder = ServletUtils.getParameter(servletConfig, DirectServlet.API_JS_FOLDER_KEY, "");
        String servletName = servletConfig.getServletName();
        String providersUrl = "/" + servletName;
        if (servletConfig.getInitParameter(PROVIDER_PATH_KEY) != null) {
            providersUrl = servletConfig.getInitParameter(PROVIDER_PATH_KEY);
        }

        String ns = servletName;
        if (servletConfig.getInitParameter(PROVIDER_PATH_KEY) != null) {
            ns = servletConfig.getInitParameter(PROVIDER_NAMESPACE_KEY);
        }

        ServletContext servletContext = servletConfig.getServletContext();
        String jsApiPath = servletContext.getRealPath(JsFolder);

        directContext.init(jsApiPath, ns, servletName, providersUrl);

        if (!CollectionUtils.isEmpty(directContext.getRegistry()
                .getApis())) {
            try {
                CodeFileGenerator.updateSource(directContext.getRegistry(), directContext.getDirectConfiguration()
                        .getCreateSourceFiles());
            } catch (IOException ex) {
                throw new ServletException("Unable to create DirectJNgine API files", ex);
            }
        }
    }

    @Override
    public void destroy() {
        if (contextLoader != null) {
            contextLoader.closeWebApplicationContext(getServletContext());
        }
        super.destroy();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BufferedRequestWrapper req = new BufferedRequestWrapper(request);
        Timer timer = new Timer();
        try {
            attachThreadLocalData(req, response);
            try {
                String requestEncoding = req.getCharacterEncoding();
                // If we don't know what the request encoding is, assume it to be UTF-8
                if (StringUtils.isEmpty(requestEncoding)) {
                    request.setCharacterEncoding(EncodingUtils.UTF8);
                }
                response.setCharacterEncoding(EncodingUtils.UTF8);
                RequestType type = ServletUtil.getFromRequestContentType(request);

                handleProcess(req, response, type);

            } finally {
                detachThreadLocalData();
            }
        } finally {
            timer.stop();
            timer.logDebugTimeInMilliseconds("Total servlet processing time");
        }

    }

    protected void handleProcess(HttpServletRequest request, HttpServletResponse response, RequestType type) {
        IDirectHandlerContext handlerContext = new DirectHandlerContext(directContext, type, request, response);
        for (IDirectHandler handler : directContext.getDirectHandlers()) {
            handler.process(handlerContext);
        }
        handlerContext = null;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return (DefaultListableBeanFactory) ContextLoader.getCurrentWebApplicationContext()
                .getAutowireCapableBeanFactory();
    }

    /**
    * Create the ContextLoader to use. Can be overridden in subclasses.
    *
    * @return the new ContextLoader
    */
    protected ContextLoader createContextLoader() {
        return new ContextLoader();
    }

    protected DirectContext createDirectContext() {
        return new DirectContext();
    }

}
