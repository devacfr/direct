package org.cfr.matcha.direct.handler.impl;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.cfr.matcha.direct.IDirectContext;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

public class DirectHandler extends BaseHandler {

    public final static List<RequestType> acceptedRequestTypeList = Collections.unmodifiableList(Arrays.asList(RequestType.FORM_SIMPLE_POST,
        RequestType.JSON,
        RequestType.POLL,
        RequestType.SOURCE));

    @Override
    public List<RequestType> getAcceptedRequestType() {
        return acceptedRequestTypeList;
    }

    @Override
    protected void doProcess(IDirectHandlerContext handlerContext) throws Exception {

        IDirectContext context = handlerContext.getContext();
        BufferedReader reader = handlerContext.getReader();
        PrintWriter writer = handlerContext.getWriter();

        try {
            switch (handlerContext.getRequestType()) {
                case FORM_SIMPLE_POST:
                    handlerContext.setResponseContentType(JSON_CONTENT_TYPE);
                    context.getRequestRouter().processSimpleFormPostRequest(reader, writer);
                    break;
                case JSON:
                    handlerContext.setResponseContentType(JSON_CONTENT_TYPE);
                    context.getRequestRouter().processJsonRequest(reader, writer);
                    break;
                case POLL:
                    handlerContext.setResponseContentType(JSON_CONTENT_TYPE);
                    context.getRequestRouter().processPollRequest(reader, writer, handlerContext.getPathInfo());
                    break;
                case SOURCE:
                    handlerContext.setResponseContentType(JAVASCRIPT_CONTENT_TYPE);
                    context.getRequestRouter().processSourceRequest(reader, writer, handlerContext.getPathInfo());
                    break;
                case FORM_UPLOAD_POST:
                    throw new RuntimeException("This Handler doesn't treat file uploading");
                default:
                    break;
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }
}
