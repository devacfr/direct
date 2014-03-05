package org.cfr.direct.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.IServletDirectFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestHandler;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;
import com.softwarementors.extjs.djn.router.RequestType;

public class HttpDirectRequestHandler implements HttpRequestHandler, InitializingBean {

	private final IServletDirectFactory directManager;


	public HttpDirectRequestHandler(final IServletDirectFactory directManager) {
		this.directManager = directManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(directManager, "directManager is required");
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



}