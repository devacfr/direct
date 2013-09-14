package org.cfr.direct.testing;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.cfr.direct.rs.DirectJSResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:direct-jaxrs-test-beans-definition.xml" })
public class DirectJSResourceTest extends BaseJAXRSResourceTest {

    private static final Logger LOGGER = Logger.getLogger(DirectJSResourceTest.class);

    @Override
    protected Set<Class<?>> getJAXRSResourceToTest() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(DirectJSResource.class);
        return rrcs;
    }

    @Test
    public void getFILE_EJN_ASSERT_Test() throws Exception {

        ClientResource resource = getClient("/javascript/" + DirectJSResource.FILE_EJN_ASSERT);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.info(response);

        String expected = readFile(DirectJSResource.PATH_EJN_ASSERT);

        assertEquals(expected, response);
    }

    @Test
    public void getFILE_DJN_REMOTE_CALL_SUPPORT_Test() throws Exception {

        ClientResource resource = getClient("/javascript/" + DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.info(response);

        String expected = readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);

        assertEquals(expected, response);
    }

    @Test
    public void getFILE_DIRECT_API_Test() throws Exception {

        ClientResource resource = getClient("/javascript/" + DirectJSResource.FILE_DIRECT_API);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.info(response);

        assertTrue(response.contains("Ext.namespace(\"DirectHandlerResource.DirectHandlerResource\");"));
        assertTrue(response.contains("DirectHandlerResource.DirectHandlerResource.PROVIDER_BASE_URL=window.location.protocol+\"//\"+window.location.host+\"/\"+(window.location.pathname.split(\"/\").length>2?window.location.pathname.split(\"/\")[1]+\"/\":\"\")+\"/directrs\";"));
        assertTrue(response.contains("DirectHandlerResource.DirectHandlerResource.POLLING_URLS={};"));
        assertTrue(response.contains("DirectHandlerResource.DirectHandlerResource.REMOTING_API={url:DirectHandlerResource.DirectHandlerResource.PROVIDER_BASE_URL,type:\"remoting\",actions:{MyAction:[{name:\"myMethod\",len:1,formHandler:false}]}};"));
    }

    @Test
    public void getFILE_DIRECT_DEBUG_API_Test() throws Exception {

        ClientResource resource = getClient("/javascript/" + DirectJSResource.FILE_DIRECT_DEBUG_API);

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.info(response);

        assertTrue(response.contains("Ext.namespace( 'DirectHandlerResource.DirectHandlerResource')"));
        assertTrue(response.contains("DirectHandlerResource.DirectHandlerResource.PROVIDER_BASE_URL=window.location.protocol + '//' + window.location.host + '/' + (window.location.pathname.split('/').length>2 ? window.location.pathname.split('/')[1]+ '/' : '')  + '/directrs';"));
        assertTrue(response.contains("    MyAction: ["));
    }

    @Test
    public void getUNKNOW_FILE_Test() throws Exception {

        ClientResource resource = getClient("/javascript/" + "unknowfile");

        String response = IOUtils.toString(resource.get().getStream());
        LOGGER.info(response);

        String expected = DirectJSResource.NO_JS_FILE + "unknowfile";

        assertEquals(expected, response);
    }

    @Test(expected = ResourceException.class)
    public void getEmptyFileNameTest() throws Exception {

        ClientResource resource = getClient("/");

        resource.get();

    }

    @Test
    public void multipleGetTest() throws Exception {

        ClientResource resource = getClient("/javascript/" + DirectJSResource.FILE_EJN_ASSERT);

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.info(response);

        resource = getClient("/javascript/" + DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT);

        response = IOUtils.toString(resource.get().getStream());

        LOGGER.info(response);

        resource = getClient("/javascript/" + "unknowfile");

        response = IOUtils.toString(resource.get().getStream());

        LOGGER.info(response);

    }

    private String readFile(String filePath) {
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
}
