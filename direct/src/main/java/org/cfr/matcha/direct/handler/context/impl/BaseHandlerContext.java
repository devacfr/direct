package org.cfr.matcha.direct.handler.context.impl;

import org.cfr.matcha.direct.config.DirectContext;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

public abstract class BaseHandlerContext implements IDirectHandlerContext {

    private DirectContext context;

    private RequestType requestType;

    private BaseHandlerContext() {
        super();
    }

    public BaseHandlerContext(final DirectContext context, final RequestType requestType) {
        this();
        this.context = context;
        this.requestType = requestType;
    }

    /**
     * @return the context
     */
    @Override
    public DirectContext getContext() {
        return context;
    }

    /**
     * @return the requestType
     */
    @Override
    public RequestType getRequestType() {
        return requestType;
    }

}
