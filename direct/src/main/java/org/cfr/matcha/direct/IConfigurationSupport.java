package org.cfr.matcha.direct;

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