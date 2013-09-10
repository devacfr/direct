package org.cfr.direct.handler.context.impl;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.context.IDirectHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

public abstract class BaseHandlerContext implements IDirectHandlerContext {

    private DirectContext context;

    private RequestType requestType;

    private BaseHandlerContext() {
        super();
    }

    public BaseHandlerContext(DirectContext context, RequestType requestType) {
        this();
        this.context = context;
        this.requestType = requestType;
    }

    /**
     * @return the context
     */
    public DirectContext getContext() {
        return context;
    }

    /**
     * @return the requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

}
