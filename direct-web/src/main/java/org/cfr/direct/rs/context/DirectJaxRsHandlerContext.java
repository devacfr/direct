package org.cfr.direct.rs.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.context.impl.BaseHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectJaxRsHandlerContext extends BaseHandlerContext {

    private BufferedReader reader;

    private PrintWriter writer;

    private String pathInfo;

    public DirectJaxRsHandlerContext(DirectContext context, RequestType requestType, String pathInfo, BufferedReader reader, PrintWriter writer) {
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
