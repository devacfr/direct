package org.cfr.matcha.direct.servlet;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.commons.util.Assert;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.servlet.context.DirectHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

@Named("DirectContext")
@Singleton
public class ServletDirectContext extends BaseDirectContext implements IServletDirectContext {

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws Exception {
        Assert.hasText(getJsApiPath(), "defaultJsApiPath is required");
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleProcess(HttpServletRequest request, HttpServletResponse response, RequestType type) {
        IDirectHandlerContext handlerContext = new DirectHandlerContext(this, type, request, response);
        for (IDirectHandler handler : getDirectHandlers()) {
            handler.process(handlerContext);
        }
        handlerContext = null;
    }

}
