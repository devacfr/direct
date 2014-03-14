package org.cfr.matcha.direct.rs.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.cfr.matcha.direct.handler.context.impl.BaseHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectJaxRsHandlerContext extends BaseHandlerContext {

	private final BufferedReader reader;

	private final PrintWriter writer;

	private final String pathInfo;

	public DirectJaxRsHandlerContext(BaseDirectContext context, RequestType requestType, String pathInfo, BufferedReader reader, PrintWriter writer) {
		super(context, requestType);
		this.reader = reader;
		this.writer = writer;
		this.pathInfo = pathInfo;

	}

	@Override
	public String getPathInfo() {
		return pathInfo;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return reader;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	@Override
	public void setResponseContentType(String contentType) {
		// nothing
	}


}
