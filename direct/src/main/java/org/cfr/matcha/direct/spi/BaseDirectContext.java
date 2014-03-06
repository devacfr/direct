package org.cfr.matcha.direct.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.cfr.commons.util.collection.CollectionUtil;
import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.direct.IDirectContext;
import org.cfr.matcha.direct.di.IInjector;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.impl.BaseHandler;
import org.cfr.matcha.direct.handler.impl.DirectRequestRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.jscodegen.CodeFileGenerator;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.scanner.Scanner;

/**
 * Api initialiser
 *
 */
public class BaseDirectContext extends ConfigurationProvider implements IDirectContext {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * List of actions to be published in js Api
     */
    private List<Object> directActions;

    private List<IDirectHandler> directHandlers;

    private List<ApiConfiguration> apiConfigurations;

    /**
     * directDispatcher class used to dispatch action's call
     */
    private DefaultDispatcher directDispatcher;

    private IRequestRouter requestRouter;

    private Registry registry;

    private String jsApiPath = org.apache.commons.lang.StringUtils.EMPTY;

    private String namespace;

    private String name;

    protected boolean initialized = false;

    @Inject()
    private IInjector injector;

    public BaseDirectContext() {
    }

    /**
     * Registry initialization
     * @throws Exception 
     */
    public void init() throws Exception {
        if (!initialized) {

            Assert.hasText(namespace, "namespace is required");
            Assert.hasText(name, "name is required");
            Assert.hasText(getProvidersUrl(), "providersUrl is required");
            //        Assert.notNull(contextPath, "contextPath is required");

            if (directActions == null) {
                Map<String, Object> beans = injector.getInstancesWithAnnotation(DirectAction.class);
                if (beans != null) {
                    Collection<Object> actions = beans.values();
                    directActions = Lists.newArrayList(actions);
                }
                if (directActions == null) {
                    directActions = Collections.emptyList();
                }
            }

            // config initilization
            String url = getProvidersUrl();
            if (!StringUtils.isEmpty(url)) {
                if (url.charAt(0) == '/') {
                    url.substring(1);
                }
                setProvidersUrl(url);
            }

            String apiFile = name + ".js";
            //build js api relative path
            StringBuilder fullApiFileNameBuilder = new StringBuilder();
            fullApiFileNameBuilder.append(jsApiPath);
            fullApiFileNameBuilder.append(System.getProperty("file.separator"));
            fullApiFileNameBuilder.append(apiFile);

            if (directActions == null) {
                directActions = Collections.emptyList();
            }

            if (directDispatcher == null) {
                this.directDispatcher = new DefaultDispatcher(directActions);
            }

            if (this.directHandlers == null || this.directHandlers.isEmpty()) {
                this.directHandlers = createDirectHandlers();
            }
            if (apiConfigurations == null) {
                apiConfigurations = createApiConfigurations(name,
                    apiFile,
                    fullApiFileNameBuilder.toString(),
                    namespace,
                    "",
                    directActions);
            }

            if (registry == null) {
                registry = createRegistry(getGlobalConfiguration(), apiConfigurations);
            }

            if (requestRouter == null) {
                requestRouter = createRequestRouter(registry, getGlobalConfiguration(), directDispatcher);
            }

            if (!CollectionUtil.isEmpty(getRegistry().getApis())) {
                CodeFileGenerator.updateSource(getRegistry(), isCreateSourceFiles());

            }
            initialized = true;
        }
    }

    protected Registry createRegistry(GlobalConfiguration configuration, List<ApiConfiguration> apiConfigs) {
        Registry registry = new Registry(configuration);

        Scanner scanner = new DirectScanner(registry);
        scanner.scanAndRegisterApiConfigurations(apiConfigs);

        return registry;
    }

    @Override
    public DirectRequestRouter createRequestRouter(@Nonnull Registry registry,
                                                   @Nonnull GlobalConfiguration configuration,
                                                   @Nonnull Dispatcher dispatcher) {
        return new DirectRequestRouter(registry, configuration, dispatcher);

    }

    @Override
    public List<ApiConfiguration> createApiConfigurations(@Nonnull String name, @Nonnull String apiFile,
                                                          @Nonnull String fullApiFileName,
                                                          @Nonnull String apiNamespace,
                                                          @Nonnull String actionsNamespace, @Nonnull List<?> actions) {

        List<ApiConfiguration> apiConfigs = new ArrayList<ApiConfiguration>();

        List<Class<?>> listActionClass = new ArrayList<Class<?>>(this.directActions.size());
        for (Object action : this.directActions) {
            listActionClass.add(action.getClass());
        }
        apiConfigs.add(new ApiConfiguration(name, apiFile, fullApiFileName, apiNamespace + '.' + name, "",
                listActionClass));
        return apiConfigs;
    }

    @Override
    public List<IDirectHandler> createDirectHandlers() {
        return BaseHandler.DefaultDirectHandlers;
    }

    public IInjector getInjector() {
        return injector;
    }

    public void setInjector(IInjector injector) {
        this.injector = injector;
    }

    /**
     * @return the directActions
     */
    public List<?> getDirectActions() {
        return directActions;
    }

    /**
     * @param directActions the directActions to set
     */
    public void setActions(List<Object> directActions) {
        this.directActions = directActions;
    }

    /**
     * @return the directHandlers
     */
    public List<IDirectHandler> getDirectHandlers() {
        return directHandlers;
    }

    /**
     * @param directHandlers the directHandlers to set
     */
    public void setDirectHandlers(List<IDirectHandler> directHandlers) {
        this.directHandlers = directHandlers;
    }

    /**
     * @return the apiConfigurations
     */
    public List<ApiConfiguration> getApiConfigurations() {
        return apiConfigurations;
    }

    /**
     * @param apiConfigurations the apiConfigurations to set
     */
    public void setApiConfigurations(List<ApiConfiguration> apiConfigurations) {
        this.apiConfigurations = apiConfigurations;
    }

    /**
     * @return the directDispatcher
     */
    public DefaultDispatcher getDirectDispatcher() {
        return directDispatcher;
    }

    /**
     * @param directDispatcher the directDispatcher to set
     */
    public void setDirectDispatcher(DefaultDispatcher directDispatcher) {
        this.directDispatcher = directDispatcher;
    }

    /**
     * @return the requestRouter
     */
    @Override
    public IRequestRouter getRequestRouter() {
        return requestRouter;
    }

    public void setRequestRouter(IRequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    /**
     * @return the registry
     */
    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getJsApiPath() {
        return jsApiPath;
    }

    public void setJsApiPath(String jsApiPath) {
        this.jsApiPath = jsApiPath;
    }

}
