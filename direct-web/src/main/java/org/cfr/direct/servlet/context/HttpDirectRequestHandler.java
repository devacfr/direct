package org.cfr.direct.servlet.context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.servlet.BufferedRequestWrapper;
import org.cfr.direct.servlet.ServletUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestHandler;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;
import com.softwarementors.extjs.djn.router.RequestType;

public class HttpDirectRequestHandler implements HttpRequestHandler, InitializingBean {

	private DirectManager directManager;

	private String jsApiPath;

	private String namespace;

	private String name;

	private String providersUrl;

	public HttpDirectRequestHandler() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(jsApiPath, "jsApiPath is required");
		Assert.hasText(namespace, "namespace is required");
		Assert.hasText(name, "name is required");
		Assert.hasText(providersUrl, "providersUrl is required");
		directManager.init(jsApiPath, namespace, name, providersUrl);
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
