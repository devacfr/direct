package org.cfr.matcha.direct.config;

import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessorThread;
import com.softwarementors.extjs.djn.servlet.DirectJNgineServlet;
import com.softwarementors.extjs.djn.servlet.ssm.SsmDispatcher;

public class DirectConfiguration {

	private String contextPath;

	private String providersUrl;

	private boolean debug = GlobalConfiguration.DEFAULT_DEBUG_VALUE;

	private Class<? extends Dispatcher> dispatcherClass = SsmDispatcher.class;

	private Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass = GlobalConfiguration.DEFAULT_GSON_BUILDER_CONFIGURATOR_CLASS;

	private Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass = GlobalConfiguration.DEFAULT_JSON_REQUEST_PROCESSOR_THREAD_CLASS;

	private boolean batchRequestsMultithreadingEnabled = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;

	private int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE;

	private int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE;

	private int batchRequestsThreadKeepAliveSeconds = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS;

	private int batchRequestsMaxThreadsPerRequest = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST;

	private boolean minify = GlobalConfiguration.DEFAULT_MINIFY_VALUE;

	private GlobalConfiguration globalConfiguration;

	private boolean createSourceFiles = GlobalConfiguration.DEFAULT_CREATE_SOURCE_FILES;

	public DirectConfiguration() {
	}

	public DirectConfiguration(String providersUrl) {
		this.providersUrl = providersUrl;
	}

	public DirectConfiguration(String contextPath, String providersUrl, boolean debug,
			Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass,
			Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass, Class<? extends Dispatcher> dispatcherClass, boolean minify,
			boolean batchRequestsMultithreadingEnabled, int batchRequestsMinThreadsPoolSize, int batchRequestsMaxThreadsPoolSize,
			int batchRequestsThreadKeepAliveSeconds, int batchRequestsMaxThreadsPerRequest) {

		this.contextPath = contextPath;
		this.providersUrl = providersUrl;
		this.debug = debug;
		this.gsonBuilderConfiguratorClass = gsonBuilderConfiguratorClass;
		this.jsonRequestProcessorThreadClass = jsonRequestProcessorThreadClass;
		this.dispatcherClass = dispatcherClass;
		this.minify = minify;
		this.batchRequestsMultithreadingEnabled = batchRequestsMultithreadingEnabled;
		this.batchRequestsMinThreadsPoolSize = batchRequestsMinThreadsPoolSize;
		this.batchRequestsMaxThreadsPoolSize = batchRequestsMaxThreadsPoolSize;
		this.batchRequestsThreadKeepAliveSeconds = batchRequestsThreadKeepAliveSeconds;
		this.batchRequestsMaxThreadsPerRequest = batchRequestsMaxThreadsPerRequest;

	}

	public GlobalConfiguration getGlobalConfiguration() {
		if (this.globalConfiguration == null) {
			if (batchRequestsMinThreadsPoolSize > batchRequestsMaxThreadsPoolSize) {
				throw new IllegalStateException("The maximum batch request pool ('"
						+ DirectJNgineServlet.GlobalParameters.BATCH_REQUESTS_MAX_THREADS_POOOL_SIZE + "') size was "
						+ batchRequestsMaxThreadsPoolSize + ". It must be greater or equal to the minimum request pool size ('"
						+ DirectJNgineServlet.GlobalParameters.BATCH_REQUESTS_MIN_THREADS_POOOL_SIZE + "'), which was "
						+ batchRequestsMinThreadsPoolSize);
			}
			this.globalConfiguration = new GlobalConfiguration(contextPath, providersUrl, debug, gsonBuilderConfiguratorClass,
					jsonRequestProcessorThreadClass, dispatcherClass, minify, batchRequestsMultithreadingEnabled, batchRequestsMinThreadsPoolSize,
					batchRequestsMaxThreadsPoolSize, batchRequestsThreadKeepAliveSeconds, batchRequestsMaxThreadsPerRequest, createSourceFiles);
		}
		return globalConfiguration;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getProvidersUrl() {
		return providersUrl;
	}

	public void setProvidersUrl(String providersUrl) {
		this.providersUrl = providersUrl;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Class<? extends GsonBuilderConfigurator> getGsonBuilderConfiguratorClass() {
		return gsonBuilderConfiguratorClass;
	}

	public void setGsonBuilderConfiguratorClass(Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass) {
		this.gsonBuilderConfiguratorClass = gsonBuilderConfiguratorClass;
	}

	public Class<? extends JsonRequestProcessorThread> getJsonRequestProcessorThreadClass() {
		return jsonRequestProcessorThreadClass;
	}

	public void setJsonRequestProcessorThreadClass(Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass) {
		this.jsonRequestProcessorThreadClass = jsonRequestProcessorThreadClass;
	}

	public Class<? extends Dispatcher> getDispatcherClass() {
		throw new UnsupportedOperationException();
	}

	public boolean getBatchRequestsMultithreadingEnabled() {
		return batchRequestsMultithreadingEnabled;
	}

	public void setBatchRequestsMultithreadingEnabled(boolean batchRequestsMultithreadingEnabled) {
		this.batchRequestsMultithreadingEnabled = batchRequestsMultithreadingEnabled;
	}

	public int getBatchRequestsMinThreadsPoolSize() {
		return batchRequestsMinThreadsPoolSize;
	}

	public void setBatchRequestsMinThreadsPoolSize(int batchRequestsMinThreadsPoolSize) {
		this.batchRequestsMinThreadsPoolSize = batchRequestsMinThreadsPoolSize;
	}

	public int getBatchRequestsMaxThreadsPoolSize() {
		return batchRequestsMaxThreadsPoolSize;
	}

	public void setBatchRequestsMaxThreadsPoolSize(int batchRequestsMaxThreadsPoolSize) {
		this.batchRequestsMaxThreadsPoolSize = batchRequestsMaxThreadsPoolSize;
	}

	public int getBatchRequestsThreadKeepAliveSeconds() {
		return batchRequestsThreadKeepAliveSeconds;
	}

	public void setBatchRequestsThreadKeepAliveSeconds(int batchRequestsThreadKeepAliveSeconds) {
		this.batchRequestsThreadKeepAliveSeconds = batchRequestsThreadKeepAliveSeconds;
	}

	public int getBatchRequestsMaxThreadsPerRequest() {
		return batchRequestsMaxThreadsPerRequest;
	}

	public void setBatchRequestsMaxThreadsPerRequest(int batchRequestsMaxThreadsPerRequest) {
		this.batchRequestsMaxThreadsPerRequest = batchRequestsMaxThreadsPerRequest;
	}

	public boolean isMinify() {
		return minify;
	}

	public void setMinify(boolean minify) {
		this.minify = minify;
	}

	public boolean isCreateSourceFiles() {
		return createSourceFiles;
	}

	public void setCreateSourceFiles(boolean createSourceFiles) {
		this.createSourceFiles = createSourceFiles;
	}


}
