package org.cfr.direct.rs;

import java.util.ArrayList;
import java.util.List;

import org.cfr.direct.IJaxRsDirectFactory;
import org.cfr.direct.config.DirectConfiguration;
import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;

import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.api.RegisteredPollMethod;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;

public class DirectJSResourceTest extends EasyMockTestCase {

	@Test
	public void readFileTest() {
		DirectJSResource resource = new DirectJSResource();
		String expected = resource.readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);
		assertNotNull(expected);
	}

	/**
	 * Try to read an unknow file
	 */
	@Test(expected = IllegalStateException.class)
	public void readFileunknowfileTest() {
		DirectJSResource resource = new DirectJSResource();
		resource.readFile("unknowfile");
	}

	@Test
	public void retrieveNonExistingFileTest() {

		DirectJSResource resource = new DirectJSResource();
		String fileName = "unknowfile";
		String response = resource.getDjnJSResource(fileName);

		assertEquals(DirectJSResource.NO_JS_FILE + fileName, response);

	}

	@Test
	public void retrieveFILE_DJN_REMOTE_CALL_SUPPORT_Test() {

		DirectJSResource resource = new DirectJSResource();
		String fileName = DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT;
		String response = resource.getDjnJSResource(fileName);

		String expected = resource.readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);
		assertEquals(expected, response);

	}

	@Test
	public void retrieveFILE_EJN_ASSERT_Test() {

		DirectJSResource resource = new DirectJSResource();
		String fileName = DirectJSResource.FILE_EJN_ASSERT;
		String response = resource.getDjnJSResource(fileName);

		String expected = resource.readFile(DirectJSResource.PATH_EJN_ASSERT);
		assertEquals(expected, response);

	}

	@Test
	public void retrieveFILE_DIRECT_API_Test() {

		RegisteredApi registeredApi = mock(RegisteredApi.class);
		EasyMock.expect(registeredApi.getApiNamespace()).andReturn("TestingApi").anyTimes();
		EasyMock.expect(registeredApi.getActionsNamespace()).andReturn(new String()).anyTimes();
		EasyMock.expect(registeredApi.getPollMethods()).andReturn(new ArrayList<RegisteredPollMethod>()).anyTimes();
		EasyMock.expect(registeredApi.getActions()).andReturn(new ArrayList<RegisteredAction>()).anyTimes();

		List<RegisteredApi> registeredApis = new ArrayList<RegisteredApi>();
		registeredApis.add(registeredApi);

		Registry registry = mock(Registry.class);
		EasyMock.expect(registry.getApis()).andReturn(registeredApis).anyTimes();

		GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);

		DirectConfiguration directConfiguration = mock(DirectConfiguration.class);

		EasyMock.expect(directConfiguration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();

		IJaxRsDirectFactory directManager = mock(IJaxRsDirectFactory.class);


		DirectJSResource resource = new DirectJSResource();
		resource.setDirectManager(directManager);

		String fileName = DirectJSResource.FILE_DIRECT_API;

		replay();

		resource.getDjnJSResource(fileName);

		verify();

	}

	@Test
	public void retrieveFILE_DIRECT_DEBUG_API_Test() {

		RegisteredApi registeredApi = mock(RegisteredApi.class);
		EasyMock.expect(registeredApi.getApiNamespace()).andReturn("TestingDebugApi").anyTimes();
		EasyMock.expect(registeredApi.getActionsNamespace()).andReturn(new String()).anyTimes();
		EasyMock.expect(registeredApi.getPollMethods()).andReturn(new ArrayList<RegisteredPollMethod>()).anyTimes();
		EasyMock.expect(registeredApi.getActions()).andReturn(new ArrayList<RegisteredAction>()).anyTimes();

		List<RegisteredApi> registeredApis = new ArrayList<RegisteredApi>();
		registeredApis.add(registeredApi);

		Registry registry = mock(Registry.class);
		EasyMock.expect(registry.getApis()).andReturn(registeredApis).anyTimes();

		GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);

		DirectConfiguration directConfiguration = mock(DirectConfiguration.class);

		EasyMock.expect(directConfiguration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();

		IJaxRsDirectFactory directManager = mock(IJaxRsDirectFactory.class);

		DirectJSResource resource = new DirectJSResource();
		resource.setDirectManager(directManager);

		String fileName = DirectJSResource.FILE_DIRECT_DEBUG_API;

		replay();

		resource.getDjnJSResource(fileName);

		verify();

	}

}
