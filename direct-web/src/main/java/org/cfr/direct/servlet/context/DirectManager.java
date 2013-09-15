package org.cfr.direct.servlet.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.context.IDirectHandlerContext;
import org.cfr.direct.handler.context.IDirectManager;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.softwarementors.extjs.djn.jscodegen.CodeFileGenerator;
import com.softwarementors.extjs.djn.router.RequestType;

public class DirectManager implements IDirectManager {


	/** spring beanName prefix key */
	private static final String PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT = "djnServlet.directContext.";




	/**
	 * Action Context
	 */
	private DirectContext directContext;


	private String jsApiPath;

	private String namespace;

	private String name;

	private String providersUrl;



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(String jsDefaultApiPath, String namespace, String name, String providersUrl) throws Exception {
		this.setJsApiPath(jsDefaultApiPath);
		this.setNamespace(namespace);
		this.setName(name);
		this.setProvidersUrl(providersUrl);
		this.init();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws Exception {
		Assert.hasText(jsApiPath, "jsApiPath is required");
		Assert.hasText(namespace, "namespace is required");
		Assert.hasText(name, "name is required");
		Assert.hasText(providersUrl, "providersUrl is required");

		// create beanFactory if not exist
		DefaultListableBeanFactory beanFactory = createBeanFactory();

		// create directContext if not exist
		if (directContext == null) {
			directContext = createDirectContext();
		}

		// Autowired directContext
		beanFactory.autowireBeanProperties(this.directContext, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
		beanFactory.initializeBean(this.directContext, PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT + name);


		directContext.init(jsApiPath, namespace, name, providersUrl);

		if (!CollectionUtils.isEmpty(directContext.getRegistry()
				.getApis())) {
			CodeFileGenerator.updateSource(directContext.getRegistry(), directContext.getDirectConfiguration()
					.getCreateSourceFiles());

		}
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

	public DirectContext getDirectContext() {
		return directContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getJsApiPath() {
		return jsApiPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setJsApiPath(String jsApiPath) {
		this.jsApiPath = jsApiPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNamespace() {
		return namespace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProvidersUrl() {
		return providersUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProvidersUrl(String providersUrl) {
		this.providersUrl = providersUrl;
	}


	protected DefaultListableBeanFactory createBeanFactory() {
		return (DefaultListableBeanFactory) ContextLoader.getCurrentWebApplicationContext()
				.getAutowireCapableBeanFactory();
	}

	protected DirectContext createDirectContext() {
		return new DirectContext();
	}

}
