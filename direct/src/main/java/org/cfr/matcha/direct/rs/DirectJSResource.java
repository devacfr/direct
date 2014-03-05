package org.cfr.matcha.direct.rs;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.cfr.matcha.direct.IJaxRsDirectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Path("/direct/javascript/{jsFileName}")
public class DirectJSResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectJSResource.class);

    public static final String FILE_DJN_REMOTE_CALL_SUPPORT = "djn-remote-call-support.js";

    public static final String FILE_EJN_ASSERT = "ejn-assert.js";

    public static final String FILE_DIRECT_API = "direct-api.js";

    public static final String FILE_DIRECT_DEBUG_API = "direct-debug-api.js";

    public static final String PATH_DJN_REMOTE_CALL_SUPPORT = "classpath:djn/" + FILE_DJN_REMOTE_CALL_SUPPORT;

    public static final String PATH_EJN_ASSERT = "classpath:ejn/" + FILE_EJN_ASSERT;

    public static final String NO_JS_FILE = "No js file found with name: ";

    /** Action Context  */
    @Autowired(required = true)
    private IJaxRsDirectFactory directManager;

    @GET
    @Produces(value = "text/javascript")
    public String getDjnJSResource(@PathParam(value = "jsFileName") String jsFileName) {
        String js;
        if (FILE_DJN_REMOTE_CALL_SUPPORT.equals(jsFileName)) {
            js = readFile(PATH_DJN_REMOTE_CALL_SUPPORT);
        } else if (FILE_EJN_ASSERT.equals(jsFileName)) {
            js = readFile(PATH_EJN_ASSERT);
        } else if (FILE_DIRECT_API.equals(jsFileName)) {
            js = generateApiCode(jsFileName, true);
        } else if (FILE_DIRECT_DEBUG_API.equals(jsFileName)) {
            js = generateApiCode(jsFileName, false);
        } else {
            js = NO_JS_FILE + jsFileName;
        }
        return js;
    }

    protected String generateApiCode(String jsFileName, boolean minified) {
        return directManager.generateSource(jsFileName, minified);

    }

    protected String readFile(String filePath) {
        InputStream stream = null;
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        try {
            stream = resourceLoader.getResource(filePath).getInputStream();

            return IOUtils.toString(stream, "UTF-8");
        } catch (IOException ioe) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
            throw new IllegalStateException("Unable to load " + filePath, ioe);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public void setDirectManager(IJaxRsDirectFactory directManager) {
        this.directManager = directManager;
    }
}
