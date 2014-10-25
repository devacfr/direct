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
package org.cfr.matcha.direct.rs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
@Named
@Path(IJaxRsDirectApplication.PROVIDER_URL + "/javascript/{jsFileName}")
public class DirectJSResource {

    /**
    *
    */
   public static final String FILE_DJN_REMOTE_CALL_SUPPORT = "djn-remote-call-support.js";

   /**
    *
    */
   public static final String FILE_EJN_ASSERT = "ejn-assert.js";

   /**
    *
    */
   public static final String FILE_DIRECT_API = "direct-api.js";

   /**
    *
    */
   public static final String FILE_DIRECT_DEBUG_API = "direct-debug-api.js";

   /**
    *
    */
   public static final String PATH_DJN_REMOTE_CALL_SUPPORT = "classpath:djn/" + FILE_DJN_REMOTE_CALL_SUPPORT;

   /**
    *
    */
   public static final String PATH_EJN_ASSERT = "classpath:ejn/" + FILE_EJN_ASSERT;

   /**
    *
    */
   public static final String NO_JS_FILE = "No js file found with name: ";
   
    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectJSResource.class);


    /** Action Context  */
    @Inject
    @Named("DirectApplication")
    private IJaxRsDirectApplication directApplication;

    /**
     *
     * @param jsFileName
     * @return
     */
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

    /**
     *
     * @param jsFileName
     * @param minified
     * @return
     */
    protected String generateApiCode(String jsFileName, boolean minified) {
        return directApplication.generateSource(jsFileName, minified);

    }

    /**
     *
     * @param filePath
     * @return
     */
    protected String readFile(@Nonnull final String filePath) {
        Assert.hasText(filePath, "filePath parameter is required");
        InputStream stream = null;
        URL url = null;
        try {
            url = ResourceUtils.getURL(filePath);
            stream = url.openStream();

            return IOUtils.toString(stream, "UTF-8");
        } catch (IOException ioe) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
            throw new IllegalStateException("Unable to load " + filePath, ioe);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     *
     * @param application
     */
    public void setDirectApplication(@Nonnull IJaxRsDirectApplication application) {
        this.directApplication = application;
    }
}
