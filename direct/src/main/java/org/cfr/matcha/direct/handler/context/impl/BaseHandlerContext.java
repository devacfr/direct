package org.cfr.matcha.direct.handler.context.impl;

import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

public abstract class BaseHandlerContext implements IDirectHandlerContext {

    private BaseDirectContext context;

    private RequestType requestType;

    private BaseHandlerContext() {
        super();
    }

    public BaseHandlerContext(final BaseDirectContext context, final RequestType requestType) {
        this();
        this.context = context;
        this.requestType = requestType;
    }

    /**
     * @return the context
     */
    @Override
    public BaseDirectContext getContext() {
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
