package org.cfr.matcha.direct.servlet;
//package org.cfr.direct.servlet;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.util.Enumeration;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.FileUtils;
//import org.cfr.direct.IServletDirectManager;
//import org.cfr.direct.servlet.BufferedRequestWrapper.BufferedServletInputStream;
//import org.cfr.direct.testing.EasyMockTestCase;
//import org.easymock.EasyMock;
//import org.junit.Test;
//
//public class DirectServletTest extends EasyMockTestCase {
//
//	private ServletConfig servletConfig;
//
//	private DirectServlet servlet;
//
//
//	private IServletDirectManager directManager;
//
//	@Override
//	public void setUp() throws Exception {
//		super.setUp();
//		String jsApiFolderPath = null;
//		File currentFile = FileUtils.toFile(this.getClass().getResource("."));
//		while (jsApiFolderPath == null) {
//			currentFile = currentFile.getParentFile();
//			if (currentFile.getName().equals("target")) {
//				jsApiFolderPath = currentFile.getAbsolutePath();
//			}
//		}
//
//		ServletContext servletContext = mock(ServletContext.class);
//		EasyMock.expect(servletContext.getRealPath("jsApiFolder")).andReturn(jsApiFolderPath);
//
//		servletConfig = mock(ServletConfig.class);
//		EasyMock.expect(servletConfig.getServletContext()).andReturn(servletContext).anyTimes();
//		EasyMock.expect(servletConfig.getInitParameter(DirectServlet.API_JS_FOLDER_KEY)).andReturn("jsApiFolder").anyTimes();
//		EasyMock.expect(servletConfig.getInitParameter(DirectServlet.PROVIDER_NAMESPACE_KEY)).andReturn("ns").anyTimes();
//		EasyMock.expect(servletConfig.getInitParameter(DirectServlet.PROVIDER_PATH_KEY)).andReturn("providersurl").anyTimes();
//		EasyMock.expect(servletConfig.getInitParameter(DirectServlet.CONTEXT_NAME_KEY)).andReturn("contextName").anyTimes();
//
//		directManager = mock(IServletDirectManager.class);
//		directManager.init(jsApiFolderPath, "ns", "contextName", "providersurl");
//		EasyMock.expectLastCall().once();
//
//		servlet = new DirectServlet(directManager);
//
//
//	}
//
//	@Test
//	public void destroyTest() throws ServletException {
//		replay();
//
//		servlet.init(servletConfig);
//		servlet.destroy();
//		verify();
//	}
//
//	@Test
//	public void postTest() throws Exception {
//
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		expect(request.getContentType()).andReturn("multipart/blablatest").anyTimes();
//		expect(request.getInputStream()).andReturn(new BufferedServletInputStream(new ByteArrayInputStream(new byte[] {}))).once();
//		expect(request.getMethod()).andReturn("post").anyTimes();
//		@SuppressWarnings("unchecked")
//		Enumeration<Object> header = mock(Enumeration.class);
//		expect(request.getHeaderNames()).andReturn(header).anyTimes();
//
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		replay();
//
//		servlet.init(servletConfig);
//		servlet.doPost(request, response);
//		verify();
//
//	}
//}
