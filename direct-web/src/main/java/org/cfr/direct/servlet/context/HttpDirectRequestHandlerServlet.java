package org.cfr.direct.servlet.context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.softwarementors.extjs.djn.servlet.ssm.WebContextManager;

public class HttpDirectRequestHandlerServlet extends HttpServlet {


	private HttpDirectRequestHandler target;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8749240296145041994L;


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LocaleContextHolder.setLocale(request.getLocale());
		attachThreadLocalData(request, response);
		try {
			this.target.handleRequest(request, response);
		}
		catch (HttpRequestMethodNotSupportedException ex) {
			String[] supportedMethods = ex.getSupportedMethods();
			if (supportedMethods != null) {
				response.setHeader("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
			}
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ex.getMessage());
		}
		finally {
			detachThreadLocalData();
			LocaleContextHolder.resetLocaleContext();
		}
	}

	protected void attachThreadLocalData(HttpServletRequest request, HttpServletResponse response) {
		WebContextManager.initializeWebContextForCurrentThread(this, request, response);
	}

	protected void detachThreadLocalData() {
		WebContextManager.detachFromCurrentThread();
	}

}
