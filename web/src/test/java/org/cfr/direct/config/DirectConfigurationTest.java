package org.cfr.direct.config;

import org.cfr.direct.testing.EasyMockTestCase;
import org.junit.Test;

import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessorThread;

public class DirectConfigurationTest extends EasyMockTestCase {

    @Test
    public void constructorTest() {
        String contextPath = "contextPath";
        String providersUrl = "providersUrl";
        boolean debug = true;
        Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass = GsonBuilderConfigurator.class;
        Class<? extends JsonRequestProcessorThread> jsonRequestProcessorThreadClass = JsonRequestProcessorThread.class;
        Class<? extends Dispatcher> dispatcherClass = Dispatcher.class;
        boolean minify = true;
        boolean batchRequestsMultithreadingEnabled = true;
        int batchRequestsMinThreadsPoolSize = 1;
        int batchRequestsMaxThreadsPoolSize = 2;
        int batchRequestsThreadKeepAliveSeconds = 3;
        int batchRequestsMaxThreadsPerRequest = 4;

        DirectConfiguration directConfiguration = new DirectConfiguration(contextPath, providersUrl, debug, gsonBuilderConfiguratorClass,
                jsonRequestProcessorThreadClass, dispatcherClass, minify, batchRequestsMultithreadingEnabled, batchRequestsMinThreadsPoolSize,
                batchRequestsMaxThreadsPoolSize, batchRequestsThreadKeepAliveSeconds, batchRequestsMaxThreadsPerRequest);

        assertEquals(contextPath, directConfiguration.getContextPath());
        assertEquals(providersUrl, directConfiguration.getProvidersUrl());
        assertEquals(debug, directConfiguration.isDebug());
        assertEquals(gsonBuilderConfiguratorClass, directConfiguration.getGsonBuilderConfiguratorClass());
        assertEquals(jsonRequestProcessorThreadClass, directConfiguration.getJsonRequestProcessorThreadClass());
        //assertEquals(dispatcherClass, directConfiguration.getDispatcherClass());
        assertEquals(minify, directConfiguration.isMinify());
        assertEquals(batchRequestsMultithreadingEnabled, directConfiguration.getBatchRequestsMultithreadingEnabled());
        assertEquals(batchRequestsMinThreadsPoolSize, directConfiguration.getBatchRequestsMinThreadsPoolSize());
        assertEquals(batchRequestsMaxThreadsPoolSize, directConfiguration.getBatchRequestsMaxThreadsPoolSize());
        assertEquals(batchRequestsThreadKeepAliveSeconds, directConfiguration.getBatchRequestsThreadKeepAliveSeconds());
        assertEquals(batchRequestsMaxThreadsPerRequest, directConfiguration.getBatchRequestsMaxThreadsPerRequest());

    }

    @Test
    public void emptyConstructorTest() {

        DirectConfiguration directConfiguration = new DirectConfiguration("providersUrl");

        assertEquals(directConfiguration.isDebug(), GlobalConfiguration.DEFAULT_DEBUG_VALUE);
        assertEquals(directConfiguration.isMinify(), GlobalConfiguration.DEFAULT_MINIFY_VALUE);
        assertEquals(directConfiguration.getBatchRequestsMultithreadingEnabled(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE);
        assertEquals(directConfiguration.getBatchRequestsMaxThreadsPoolSize(), GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE);
        assertEquals(directConfiguration.getBatchRequestsMinThreadsPoolSize(), GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE);

        assertEquals(directConfiguration.getBatchRequestsThreadKeepAliveSeconds(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS);
        assertEquals(directConfiguration.getBatchRequestsMaxThreadsPerRequest(), GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST);

    }

    @Test
    public void setConfigDirectConfigurationTest() {
        String contextPath = "contextPath";
        String providersUrl = "providersUrl";
        DirectConfiguration directConfiguration = new DirectConfiguration();

        directConfiguration.setContextPath(contextPath);
        directConfiguration.setProvidersUrl(providersUrl);

        boolean debug = !GlobalConfiguration.DEFAULT_DEBUG_VALUE;
        directConfiguration.setDebug(debug);
        boolean minify = !GlobalConfiguration.DEFAULT_MINIFY_VALUE;
        directConfiguration.setMinify(minify);
        boolean batchRequestsMultithreadingEnabled = !GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;
        directConfiguration.setBatchRequestsMultithreadingEnabled(batchRequestsMultithreadingEnabled);
        int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMinThreadsPoolSize(batchRequestsMinThreadsPoolSize);
        int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(batchRequestsMaxThreadsPoolSize);
        int batchRequestsThreadKeepAliveSeconds = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS + 3;
        directConfiguration.setBatchRequestsThreadKeepAliveSeconds(batchRequestsThreadKeepAliveSeconds);
        int batchRequestsMaxThreadsPerRequest = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST + 3;
        directConfiguration.setBatchRequestsMaxThreadsPerRequest(batchRequestsMaxThreadsPerRequest);

        directConfiguration.setGsonBuilderConfiguratorClass(GsonBuilderConfigurator.class);
        directConfiguration.setJsonRequestProcessorThreadClass(JsonRequestProcessorThread.class);

        assertEquals(providersUrl, directConfiguration.getProvidersUrl());
        assertEquals(contextPath, directConfiguration.getContextPath());

        assertEquals(debug, directConfiguration.isDebug());
        assertEquals(minify, directConfiguration.isMinify());
        assertEquals(batchRequestsMultithreadingEnabled, directConfiguration.getBatchRequestsMultithreadingEnabled());
        assertEquals(batchRequestsMinThreadsPoolSize, directConfiguration.getBatchRequestsMinThreadsPoolSize());
        assertEquals(batchRequestsMaxThreadsPoolSize, directConfiguration.getBatchRequestsMaxThreadsPoolSize());

        assertEquals(batchRequestsThreadKeepAliveSeconds, directConfiguration.getBatchRequestsThreadKeepAliveSeconds());
        assertEquals(batchRequestsMaxThreadsPerRequest, directConfiguration.getBatchRequestsMaxThreadsPerRequest());

        assertEquals(GsonBuilderConfigurator.class, directConfiguration.getGsonBuilderConfiguratorClass());
        assertEquals(JsonRequestProcessorThread.class, directConfiguration.getJsonRequestProcessorThreadClass());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unsupportedOperationTest() {
        DirectConfiguration directConfiguration = new DirectConfiguration("providersUrl");
        directConfiguration.getDispatcherClass();
    }

    @Test
    public void afterPropertiesSetTest() throws Exception {

        DirectConfiguration directConfiguration = new DirectConfiguration("providersUrl");

        boolean debug = !GlobalConfiguration.DEFAULT_DEBUG_VALUE;
        directConfiguration.setDebug(debug);
        boolean minify = !GlobalConfiguration.DEFAULT_MINIFY_VALUE;
        directConfiguration.setMinify(minify);
        boolean batchRequestsMultithreadingEnabled = !GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;
        directConfiguration.setBatchRequestsMultithreadingEnabled(batchRequestsMultithreadingEnabled);
        int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMinThreadsPoolSize(batchRequestsMinThreadsPoolSize);
        int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(batchRequestsMaxThreadsPoolSize);
        int batchRequestsThreadKeepAliveSeconds = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS + 3;
        directConfiguration.setBatchRequestsThreadKeepAliveSeconds(batchRequestsThreadKeepAliveSeconds);
        int batchRequestsMaxThreadsPerRequest = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST + 3;
        directConfiguration.setBatchRequestsMaxThreadsPerRequest(batchRequestsMaxThreadsPerRequest);

        directConfiguration.setProvidersUrl("providersUrl");

    }

    @Test(expected = IllegalStateException.class)
    public void excepIllegalStateThreadPoolTest() throws Exception {
        DirectConfiguration directConfiguration = new DirectConfiguration("providersUrl");
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE - 1);
        directConfiguration.getGlobalConfiguration();

    }

    @Test
    public void getGlobalConfigurationtest() throws Exception {
        DirectConfiguration conf = new DirectConfiguration("providersUrl");
        GlobalConfiguration globalConfiguration = conf.getGlobalConfiguration();

        assertEquals(conf.isDebug(), globalConfiguration.getDebug());
        assertEquals(conf.isMinify(), globalConfiguration.getMinify());
        assertEquals(conf.getBatchRequestsMultithreadingEnabled(), globalConfiguration.getBatchRequestsMultithreadingEnabled());
        assertEquals(conf.getBatchRequestsMaxThreadsPoolSize(), globalConfiguration.getBatchRequestsMaxThreadsPoolSize());
        assertEquals(conf.getBatchRequestsMinThreadsPoolSize(), globalConfiguration.getBatchRequestsMinThreadsPoolSize());
        assertEquals(conf.getBatchRequestsThreadKeepAliveSeconds(), globalConfiguration.getBatchRequestsThreadKeepAliveSeconds());
        assertEquals(conf.getBatchRequestsMaxThreadsPerRequest(), globalConfiguration.getBatchRequestsMaxThreadsPerRequest());
    }

    @Test
    public void recallGetGlobalConfigurationtest() throws Exception {
        DirectConfiguration conf = new DirectConfiguration("providersUrl");
        GlobalConfiguration globalConfiguration1 = conf.getGlobalConfiguration();
        GlobalConfiguration globalConfiguration2 = conf.getGlobalConfiguration();

        assertEquals(globalConfiguration1, globalConfiguration2);
    }
}
