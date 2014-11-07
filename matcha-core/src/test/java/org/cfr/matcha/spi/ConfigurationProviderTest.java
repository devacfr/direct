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

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.IConfigurationSupport;
import org.cfr.matcha.spi.ConfigurationProvider;
import org.junit.Test;

import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessorThread;

public class ConfigurationProviderTest extends EasyMockTestCase {

    @Test
    public void emptyConstructorTest() {

        IConfigurationSupport directConfiguration = new ConfigurationProvider();

        assertEquals(directConfiguration.isDebug(), GlobalConfiguration.DEFAULT_DEBUG_VALUE);
        assertEquals(directConfiguration.isMinify(), GlobalConfiguration.DEFAULT_MINIFY_VALUE);
        assertEquals(directConfiguration.getBatchRequestsMultithreadingEnabled(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE);
        assertEquals(directConfiguration.getBatchRequestsMaxThreadsPoolSize(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE);
        assertEquals(directConfiguration.getBatchRequestsMinThreadsPoolSize(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE);

        assertEquals(directConfiguration.getBatchRequestsThreadKeepAliveSeconds(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS);
        assertEquals(directConfiguration.getBatchRequestsMaxThreadsPerRequest(),
            GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST);

    }

    @Test
    public void setConfigDirectConfigurationTest() {
        String contextPath = "contextPath";
        String providersUrl = "providersUrl";
        ConfigurationProvider directConfiguration = new ConfigurationProvider();

        directConfiguration.setContextPath(contextPath);
        directConfiguration.setProvidersUrl(providersUrl);

        boolean debug = !GlobalConfiguration.DEFAULT_DEBUG_VALUE;
        directConfiguration.setDebug(debug);
        boolean minify = !GlobalConfiguration.DEFAULT_MINIFY_VALUE;
        directConfiguration.setMinify(minify);
        boolean batchRequestsMultithreadingEnabled =
                !GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;
        directConfiguration.setBatchRequestsMultithreadingEnabled(batchRequestsMultithreadingEnabled);
        int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMinThreadsPoolSize(batchRequestsMinThreadsPoolSize);
        int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(batchRequestsMaxThreadsPoolSize);
        int batchRequestsThreadKeepAliveSeconds =
                GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS + 3;
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
        IConfigurationSupport directConfiguration = new ConfigurationProvider();
        directConfiguration.getDispatcherClass();
    }

    @Test
    public void afterPropertiesSetTest() throws Exception {

        ConfigurationProvider directConfiguration = new ConfigurationProvider();

        boolean debug = !GlobalConfiguration.DEFAULT_DEBUG_VALUE;
        directConfiguration.setDebug(debug);
        boolean minify = !GlobalConfiguration.DEFAULT_MINIFY_VALUE;
        directConfiguration.setMinify(minify);
        boolean batchRequestsMultithreadingEnabled =
                !GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MULTITHREADING_ENABLED_VALUE;
        directConfiguration.setBatchRequestsMultithreadingEnabled(batchRequestsMultithreadingEnabled);
        int batchRequestsMinThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMinThreadsPoolSize(batchRequestsMinThreadsPoolSize);
        int batchRequestsMaxThreadsPoolSize = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREAD_POOL_SIZE + 3;
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(batchRequestsMaxThreadsPoolSize);
        int batchRequestsThreadKeepAliveSeconds =
                GlobalConfiguration.DEFAULT_BATCH_REQUESTS_THREAD_KEEP_ALIVE_SECONDS + 3;
        directConfiguration.setBatchRequestsThreadKeepAliveSeconds(batchRequestsThreadKeepAliveSeconds);
        int batchRequestsMaxThreadsPerRequest = GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MAX_THREADS_PER_REQUEST + 3;
        directConfiguration.setBatchRequestsMaxThreadsPerRequest(batchRequestsMaxThreadsPerRequest);

        directConfiguration.setProvidersUrl("providersUrl");

    }

    @Test(expected = IllegalStateException.class)
    public void excepIllegalStateThreadPoolTest() throws Exception {
        ConfigurationProvider directConfiguration = new ConfigurationProvider();
        directConfiguration.setBatchRequestsMaxThreadsPoolSize(GlobalConfiguration.DEFAULT_BATCH_REQUESTS_MIN_THREAD_POOL_SIZE - 1);
        GlobalConfiguration config = directConfiguration.getGlobalConfiguration();
        assertNotNull(config);

    }

    @Test
    public void getGlobalConfigurationtest() throws Exception {
        ConfigurationProvider conf = new ConfigurationProvider();
        GlobalConfiguration globalConfiguration = conf.getGlobalConfiguration();

        assertEquals(conf.isDebug(), globalConfiguration.getDebug());
        assertEquals(conf.isMinify(), globalConfiguration.getMinify());
        assertEquals(conf.getBatchRequestsMultithreadingEnabled(),
            globalConfiguration.getBatchRequestsMultithreadingEnabled());
        assertEquals(conf.getBatchRequestsMaxThreadsPoolSize(),
            globalConfiguration.getBatchRequestsMaxThreadsPoolSize());
        assertEquals(conf.getBatchRequestsMinThreadsPoolSize(),
            globalConfiguration.getBatchRequestsMinThreadsPoolSize());
        assertEquals(conf.getBatchRequestsThreadKeepAliveSeconds(),
            globalConfiguration.getBatchRequestsThreadKeepAliveSeconds());
        assertEquals(conf.getBatchRequestsMaxThreadsPerRequest(),
            globalConfiguration.getBatchRequestsMaxThreadsPerRequest());
    }

    @Test
    public void recallGetGlobalConfigurationtest() throws Exception {
        ConfigurationProvider conf = new ConfigurationProvider();
        GlobalConfiguration globalConfiguration1 = conf.getGlobalConfiguration();
        GlobalConfiguration globalConfiguration2 = conf.getGlobalConfiguration();

        assertEquals(globalConfiguration1, globalConfiguration2);
    }
}
