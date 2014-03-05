package org.cfr.matcha.direct.servlet.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.matcha.direct.config.DirectContext;
import org.cfr.matcha.direct.handler.context.impl.BaseHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectHandlerContext extends BaseHandlerContext {

	private final HttpServletRequest request;

	private final HttpServletResponse response;



	public DirectHandlerContext(DirectContext context, RequestType requestType, HttpServletRequest request, HttpServletResponse response) {
		super(context, requestType);
		this.request = request;
		this.response = response;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return request.getReader();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return response.getWriter();
	}

	@Override
	public String getPathInfo() {
		return request.getPathInfo();
	}

	@Override
	public void setResponseContentType(String contentType) {
		this.response.setContentType(contentType);
	}




}
