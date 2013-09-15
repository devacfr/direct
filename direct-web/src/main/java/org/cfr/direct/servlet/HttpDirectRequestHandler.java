package org.cfr.direct.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.IServletDirectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;
import com.softwarementors.extjs.djn.router.RequestType;

public class HttpDirectRequestHandler implements HttpRequestHandler {

	private final IServletDirectManager directManager;

	private String jsApiPath;

	private String namespace;

	private String name;

	private String providersUrl;

	@Autowired
	public HttpDirectRequestHandler(final IServletDirectManager directManager) {
		this.directManager = directManager;
	}



	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		BufferedRequestWrapper req = new BufferedRequestWrapper(request);
		Timer timer = new Timer();
		try {

			String requestEncoding = req.getCharacterEncoding();
			// If we don't know what the request encoding is, assume it to
			// be UTF-8
			if (StringUtils.isEmpty(requestEncoding)) {
				request.setCharacterEncoding(EncodingUtils.UTF8);
			}
			response.setCharacterEncoding(EncodingUtils.UTF8);
			RequestType type = ServletUtil.getFromRequestContentType(request);

			directManager.handleProcess(req, response, type);
		} finally {
			timer.stop();
			timer.logDebugTimeInMilliseconds("Total servlet processing time");
		}

	}

	public String getJsApiPath() {
		return jsApiPath;
	}

	public void setJsApiPath(String jsApiPath) {
		this.jsApiPath = jsApiPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getProvidersUrl() {
		return providersUrl;
	}

	public void setProvidersUrl(String providersUrl) {
		this.providersUrl = providersUrl;
	}

}
