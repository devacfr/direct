package org.cfr.matcha.direct.handler;

import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;

public interface IDirectHandler {

    public final String JSON_CONTENT_TYPE = "application/json";

    public final String JAVASCRIPT_CONTENT_TYPE = "text/javascript"; // *YES*, shoul be "application/javascript", but then there is IE, and the fact that this is really cross-browser (sigh!)

    public final String HTML_CONTENT_TYPE = "text/html";

    void process(IDirectHandlerContext handlerContext);
}
