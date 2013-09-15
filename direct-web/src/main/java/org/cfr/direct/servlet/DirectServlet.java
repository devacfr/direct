package org.cfr.direct.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.handler.context.IDirectManager;
import org.cfr.direct.servlet.context.DirectManager;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;
import com.softwarementors.extjs.djn.router.RequestType;
import com.softwarementors.extjs.djn.servlet.DirectJNgineServlet;
import com.softwarementors.extjs.djn.servlet.ServletUtils;

public class DirectServlet extends DirectJNgineServlet  {

	/**serialVersionUID*/
	private static final long serialVersionUID = -2765616904505520650L;



	/**  Facultativ param to define the destination folder for generated js Api */
	public static final String API_JS_FOLDER_KEY = "direct.api-js.folder";

	/** Mandatory param to define servlet mapping in generated js Api */
	public static final String PROVIDER_PATH_KEY = "direct.provider.path";

	public static final String PROVIDER_NAMESPACE_KEY = "direct.ns";

	public static final String CONTEXT_NAME_KEY = "context.name";

	public static final String SERVLET_PATH_KEY = "direct.path";


	private final IDirectManager directManager;

	public DirectServlet() {
		super();
		directManager = new DirectManager();

	}

	/**
	 * For Testing
	 * @param directManager
	 */
	public DirectServlet(final IDirectManager directManager) {
		super();
		this.directManager = directManager;

	}


	private long id = 1000; // It is good for formatting to get lots of ids with the same number of digits...

	public synchronized long getUniqueRequestId() {
		return this.id++;
	}

	@Override
	protected void createDirectJNgineRouter(ServletConfig servletConfig) throws ServletException {

		String JsFolder = ServletUtils.getParameter(servletConfig, DirectServlet.API_JS_FOLDER_KEY, "");
		String contextName = servletConfig.getServletName();
		if (servletConfig.getInitParameter(CONTEXT_NAME_KEY) != null) {
			contextName = servletConfig.getInitParameter(CONTEXT_NAME_KEY);
		}
		String providersUrl = "/" + contextName;
		if (servletConfig.getInitParameter(PROVIDER_PATH_KEY) != null) {
			providersUrl = servletConfig.getInitParameter(PROVIDER_PATH_KEY);
		}

		String ns = contextName;
		if (servletConfig.getInitParameter(PROVIDER_NAMESPACE_KEY) != null) {
			ns = servletConfig.getInitParameter(PROVIDER_NAMESPACE_KEY);
		}

		ServletContext servletContext = servletConfig.getServletContext();
		String jsApiPath = servletContext.getRealPath(JsFolder);

		try {
			directManager.init(jsApiPath, ns, contextName, providersUrl);
		} catch (Exception e) {
			throw new ServletException("Unable to create DirectJNgine API files", e);
		}


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

				directManager.handleProcess(req, response, type);

			} finally {
				detachThreadLocalData();
			}
		} finally {
			timer.stop();
			timer.logDebugTimeInMilliseconds("Total servlet processing time");
		}

	}








}
