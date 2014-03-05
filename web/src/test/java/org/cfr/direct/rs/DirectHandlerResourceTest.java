package org.cfr.direct.rs;

import javax.ws.rs.core.UriInfo;

import org.cfr.direct.IJaxRsDirectFactory;
import org.cfr.direct.testing.EasyMockTestCase;
import org.junit.Test;

public class DirectHandlerResourceTest extends EasyMockTestCase {

	private IJaxRsDirectFactory getMockFullDirectManager() {
		IJaxRsDirectFactory directManager = mock(IJaxRsDirectFactory.class);
		return directManager;
	}

	@Test
	public void initTest() throws Exception {
		DirectHandlerResource resource = new DirectHandlerResource();

		resource.setDirectManager(getMockFullDirectManager());
		replay();
		//resource.afterPropertiesSet();
		verify();

	}

	@Test
	public void handleFormUrlEncodedPostTest() {
		DirectHandlerResource resource = new DirectHandlerResource();

		resource.setDirectManager(getMockFullDirectManager());

		String input = "myInput";
		UriInfo uriInfo = mock(UriInfo.class);

		replay();
		resource.handleFormUrlEncodedPost(uriInfo, input);
		verify();
	}

	@Test
	public void handleJSONPostTest() {
		DirectHandlerResource resource = new DirectHandlerResource();

		resource.setDirectManager(getMockFullDirectManager());

		String json = "myInput";
		UriInfo uriInfo = mock(UriInfo.class);

		replay();
		resource.handleJSONPost(uriInfo, json);
		verify();
	}

	@Test
	public void handlePollGetTest() {
		DirectHandlerResource resource = new DirectHandlerResource();

		resource.setDirectManager(getMockFullDirectManager());

		UriInfo uriInfo = mock(UriInfo.class);

		replay();
		resource.handlePollGet(uriInfo);
		verify();
	}

	@Test
	public void handlePollPostTest() {
		DirectHandlerResource resource = new DirectHandlerResource();

		resource.setDirectManager(getMockFullDirectManager());

		UriInfo uriInfo = mock(UriInfo.class);

		replay();
		resource.handlePollPost(uriInfo);
		verify();
	}


}
