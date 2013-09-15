package org.cfr.direct.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.IServletDirectManager;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.context.IDirectHandlerContext;
import org.cfr.direct.servlet.context.DirectHandlerContext;
import org.springframework.util.Assert;

import com.softwarementors.extjs.djn.router.RequestType;

public class ServletDirectManager extends BaseDirectManager implements IServletDirectManager {




	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws Exception {
		Assert.hasText(getDefaultJsApiPath(), "defaultJsApiPath is required");
		super.init();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleProcess(HttpServletRequest request, HttpServletResponse response, RequestType type) {
		DirectContext directContext = getDirectContext();
		IDirectHandlerContext handlerContext = new DirectHandlerContext(directContext, type, request, response);
		for (IDirectHandler handler : directContext.getDirectHandlers()) {
			handler.process(handlerContext);
		}
		handlerContext = null;
	}

	@Override
	public String getJsApiPath() {
		return getDefaultJsApiPath();
	}

	@Override
	public void setJsApiPath(String jsApiPath) {
		this.setDefaultJsApiPath(jsApiPath);
	}

	@Override
	public boolean isCreateSourceFiles() {
		return this.isDefaultCreateSourceFiles();
	}

	@Override
	public void setCreateSourceFiles(boolean createSourceFiles) {
		this.setDefaultCreateSourceFiles(createSourceFiles);
	}

}
