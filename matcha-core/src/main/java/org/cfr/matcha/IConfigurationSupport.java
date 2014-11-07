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
package org.cfr.matcha;

import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessorThread;

/**
 *
 * @author devacfr
 * @since 1.0
 */
public interface IConfigurationSupport {

    String getContextPath();

    void setContextPath(String contextPath);

    void setProvidersUrl(String providersUrl);

    String getProvidersUrl();

    /**
     *
     * @return
     */
    boolean isDebug();

    /**
     *
     * @param debug
     */
    void setDebug(boolean debug);

    /**
     *
     * @return
     */
    Class<? extends GsonBuilderConfigurator> getGsonBuilderConfiguratorClass();

    /**
     *
     * @param gsonBuilderConfiguratorClass
     */
    void setGsonBuilderConfiguratorClass(Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass);

    /**
     *
     * @return
     */
    Class<? extends JsonRequestProcessorThread> getJsonRequestProcessorThreadClass();

    /**
     *
     * @param jsonRequestProcessorThreadClass
     */
    void
    setJsonRequestProcessorThreadClass(Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass);

    /**
     *
     * @return
     */
    Class<? extends Dispatcher> getDispatcherClass();

    /**
     *
     * @return
     */
    boolean getBatchRequestsMultithreadingEnabled();

    /**
     *
     * @param batchRequestsMultithreadingEnabled
     */
    void setBatchRequestsMultithreadingEnabled(boolean batchRequestsMultithreadingEnabled);

    /**
     *
     * @return
     */
    int getBatchRequestsMinThreadsPoolSize();

    /**
     *
     * @param batchRequestsMinThreadsPoolSize
     */
    void setBatchRequestsMinThreadsPoolSize(int batchRequestsMinThreadsPoolSize);

    /**
     *
     * @return
     */
    int getBatchRequestsMaxThreadsPoolSize();

    /**
     *
     * @param batchRequestsMaxThreadsPoolSize
     */
    void setBatchRequestsMaxThreadsPoolSize(int batchRequestsMaxThreadsPoolSize);

    /**
     *
     * @return
     */
    int getBatchRequestsThreadKeepAliveSeconds();

    /**
     *
     * @param batchRequestsThreadKeepAliveSeconds
     */
    void setBatchRequestsThreadKeepAliveSeconds(int batchRequestsThreadKeepAliveSeconds);

    /**
     *
     * @return
     */
    int getBatchRequestsMaxThreadsPerRequest();

    /**
     *
     * @param batchRequestsMaxThreadsPerRequest
     */
    void setBatchRequestsMaxThreadsPerRequest(int batchRequestsMaxThreadsPerRequest);

    /**
     *
     * @return
     */
    boolean isMinify();

    /**
     *
     * @param minify
     */
    void setMinify(boolean minify);

    /**
     *
     * @return
     */
    boolean isCreateSourceFiles();

    /**
     *
     * @param createSourceFiles
     */
    void setCreateSourceFiles(boolean createSourceFiles);

}