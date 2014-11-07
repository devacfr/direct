/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.matcha.spi;

import org.cfr.matcha.IConfigurationSupport;

import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessorThread;
import com.softwarementors.extjs.djn.servlet.DirectJNgineServlet;
import com.softwarementors.extjs.djn.servlet.ssm.SsmDispatcher;

public class ConfigurationProvider implements IConfigurationSupport {

    private boolean debug = GlobalConfiguration.DEFAULT_DEBUG_VALUE;

    private final Class<? extends Dispatcher> dispatcherClass = SsmDispatcher.class;

    private Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass =
            GlobalConfiguration.DEFAULT_GSON_BUILDER_CONFIGURATOR_CLASS;

    private Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass =
            GlobalConfiguration.DEFAULT_JSON_REQUEST_PROCESSOR_THREAD_CLASS;

    private boolean batchRequestsMultithreadingEnabled =
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;

    private int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE;

    private int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE;

    private int batchRequestsThreadKeepAliveSeconds =
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS;

    private int batchRequestsMaxThreadsPerRequest = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST;

    private boolean minify = GlobalConfiguration.DEFAULT_MINIFY_VALUE;

    private GlobalConfiguration globalConfiguration;

    private boolean createSourceFiles = GlobalConfiguration.DEFAULT_CREATE_SOURCE_FILES;

    private String providersUrl;

    private String contextPath;

    public GlobalConfiguration createConfiguration() {
        if (batchRequestsMinThreadsPoolSize > batchRequestsMaxThreadsPoolSize) {
            throw new IllegalStateException("The maximum batch request pool ('"
                    + DirectJNgineServlet.GlobalParameters.BATCH_REQUESTS_MAX_THREADS_POOOL_SIZE + "') size was "
                    + batchRequestsMaxThreadsPoolSize
                    + ". It must be greater or equal to the minimum request pool size ('"
                    + DirectJNgineServlet.GlobalParameters.BATCH_REQUESTS_MIN_THREADS_POOOL_SIZE + "'), which was "
                    + batchRequestsMinThreadsPoolSize);
        }
        GlobalConfiguration configuration =
                new GlobalConfiguration(contextPath, providersUrl, debug, gsonBuilderConfiguratorClass,
                        jsonRequestProcessorThreadClass, dispatcherClass, minify, batchRequestsMultithreadingEnabled,
                        batchRequestsMinThreadsPoolSize, batchRequestsMaxThreadsPoolSize,
                        batchRequestsThreadKeepAliveSeconds, batchRequestsMaxThreadsPerRequest, createSourceFiles);
        return configuration;
    }

    public GlobalConfiguration getGlobalConfiguration() {
        if (this.globalConfiguration == null) {
            this.globalConfiguration = createConfiguration();
        }
        return globalConfiguration;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void setProvidersUrl(String providersUrl) {
        this.providersUrl = providersUrl;
    }

    @Override
    public String getProvidersUrl() {
        return providersUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebug() {
        return debug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends GsonBuilderConfigurator> getGsonBuilderConfiguratorClass() {
        return gsonBuilderConfiguratorClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGsonBuilderConfiguratorClass(Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass) {
        this.gsonBuilderConfiguratorClass = gsonBuilderConfiguratorClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends JsonRequestProcessorThread> getJsonRequestProcessorThreadClass() {
        return jsonRequestProcessorThreadClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public
    void
    setJsonRequestProcessorThreadClass(Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass) {
        this.jsonRequestProcessorThreadClass = jsonRequestProcessorThreadClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Dispatcher> getDispatcherClass() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBatchRequestsMultithreadingEnabled() {
        return batchRequestsMultithreadingEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBatchRequestsMultithreadingEnabled(boolean batchRequestsMultithreadingEnabled) {
        this.batchRequestsMultithreadingEnabled = batchRequestsMultithreadingEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBatchRequestsMinThreadsPoolSize() {
        return batchRequestsMinThreadsPoolSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBatchRequestsMinThreadsPoolSize(int batchRequestsMinThreadsPoolSize) {
        this.batchRequestsMinThreadsPoolSize = batchRequestsMinThreadsPoolSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBatchRequestsMaxThreadsPoolSize() {
        return batchRequestsMaxThreadsPoolSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBatchRequestsMaxThreadsPoolSize(int batchRequestsMaxThreadsPoolSize) {
        this.batchRequestsMaxThreadsPoolSize = batchRequestsMaxThreadsPoolSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBatchRequestsThreadKeepAliveSeconds() {
        return batchRequestsThreadKeepAliveSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBatchRequestsThreadKeepAliveSeconds(int batchRequestsThreadKeepAliveSeconds) {
        this.batchRequestsThreadKeepAliveSeconds = batchRequestsThreadKeepAliveSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBatchRequestsMaxThreadsPerRequest() {
        return batchRequestsMaxThreadsPerRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBatchRequestsMaxThreadsPerRequest(int batchRequestsMaxThreadsPerRequest) {
        this.batchRequestsMaxThreadsPerRequest = batchRequestsMaxThreadsPerRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMinify() {
        return minify;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinify(boolean minify) {
        this.minify = minify;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCreateSourceFiles() {
        return createSourceFiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateSourceFiles(boolean createSourceFiles) {
        this.createSourceFiles = createSourceFiles;
    }

}
