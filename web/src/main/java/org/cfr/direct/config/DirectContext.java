package org.cfr.direct.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cfr.direct.dispatcher.SpringDispatcher;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.impl.DirectRequestRouter;
import org.cfr.matcha.api.direct.IDirectAction;
import org.springframework.util.Assert;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.scanner.Scanner;

/**
 * Api initialiser
 *
 */
public class DirectContext {

    /**
     * List of actions to be published in js Api
     */
    private List<IDirectAction> directActions;

    private List<IDirectHandler> directHandlers;

    private List<ApiConfiguration> apiConfigurations;

    /**
     * redefined configuration only used when application start to redefine GlobalConfiguration
     */
    private DirectConfiguration directConfiguration;

    /**
     * directDispatcher class used to dispatch action's call
     */
    private SpringDispatcher directDispatcher;

    // Non-mutable => no need to worry about thread-safety => can be an 'instance' variable
    private DirectRequestRouter requestRouter;

    private Registry registry;

    public DirectContext() {
    }

    /**
     * Registry initialization
     */
    public void init(String jsDefaultApiPath, String namespace, String contextName, String providersUrl) {

        Assert.notEmpty(directHandlers, "No directHandler defined");

        // config initilization
        if (this.directConfiguration == null) {
            Assert.hasText(providersUrl, "providersUrl is required");
            this.directConfiguration = createDirectConfiguration(providersUrl);

        } else if (StringUtils.isEmpty(directConfiguration.getProvidersUrl())) {
            Assert.hasText(providersUrl, "providersUrl is required");
            String url = providersUrl;
            if (url.charAt(0) == '/') {
                url.substring(1);
            }
            directConfiguration.setProvidersUrl(url);

        }

        String apiFile = contextName + ".js";
        //build js api relative path
        StringBuilder fullApiFileNameBuilder = new StringBuilder();
        fullApiFileNameBuilder.append(jsDefaultApiPath);
        fullApiFileNameBuilder.append(System.getProperty("file.separator"));
        fullApiFileNameBuilder.append(apiFile);

        if (directActions == null) {
            directActions = Collections.emptyList();
        }

        if (directDispatcher == null) {
            this.directDispatcher = new SpringDispatcher(directActions);
        }

        if (apiConfigurations == null) {
            apiConfigurations = createApiConfigurations(contextName,
                apiFile,
                fullApiFileNameBuilder.toString(),
                namespace,
                "",
                directActions);
        }

        if (registry == null) {
            registry = createRegistry(directConfiguration, apiConfigurations);
        }

        if (requestRouter == null) {
            requestRouter = createRequestRouter(registry, directConfiguration, directDispatcher);
        }

    }

    public DirectConfiguration createDirectConfiguration(String providerUrl) {
        Assert.hasText(providerUrl, "providerUrl is required");
        return new DirectConfiguration(providerUrl);
    }

    protected Registry createRegistry(DirectConfiguration directConfiguration, List<ApiConfiguration> apiConfigs) {
        Registry registry = new Registry(directConfiguration.getGlobalConfiguration());

        Scanner scanner = new DirectScanner(registry);
        scanner.scanAndRegisterApiConfigurations(apiConfigs);

        return registry;
    }

    protected DirectRequestRouter createRequestRouter(Registry registry, DirectConfiguration directConfiguration,
                                                      Dispatcher dispatcher) {
        return new DirectRequestRouter(registry, directConfiguration.getGlobalConfiguration(), dispatcher);

    }

    protected List<ApiConfiguration> createApiConfigurations(String name, String apiFile, String fullApiFileName,
                                                             String apiNamespace, String actionsNamespace,
                                                             List<IDirectAction> actions) {

        List<ApiConfiguration> apiConfigs = new ArrayList<ApiConfiguration>();

        List<Class<?>> listActionClass = new ArrayList<Class<?>>(this.directActions.size());
        for (IDirectAction action : this.directActions) {
            listActionClass.add(action.getClass());
        }
        apiConfigs.add(new ApiConfiguration(name, apiFile, fullApiFileName, apiNamespace + '.' + name, "",
                listActionClass));
        return apiConfigs;
    }

    /**
     * @return the directActions
     */
    public List<IDirectAction> getDirectActions() {
        return directActions;
    }

    /**
     * @param directActions the directActions to set
     */
    public void setActions(List<IDirectAction> directActions) {
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
     * @return the directConfiguration
     */
    public DirectConfiguration getDirectConfiguration() {
        return directConfiguration;
    }

    /**
     * @param directConfiguration the directConfiguration to set
     */
    public void setDirectConfiguration(DirectConfiguration directConfiguration) {
        this.directConfiguration = directConfiguration;
    }

    /**
     * @return the directDispatcher
     */
    public SpringDispatcher getDirectDispatcher() {
        return directDispatcher;
    }

    /**
     * @param directDispatcher the directDispatcher to set
     */
    public void setDirectDispatcher(SpringDispatcher directDispatcher) {
        this.directDispatcher = directDispatcher;
    }

    /**
     * @return the requestRouter
     */
    public DirectRequestRouter getRequestRouter() {
        return requestRouter;
    }

    /**
     * @param requestRouter the requestRouter to set
     */
    public void setRequestRouter(DirectRequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    public GlobalConfiguration getGlobalConfiguration() {
        if (directConfiguration == null)
            return null;
        return directConfiguration.getGlobalConfiguration();
    }

    /**
     * @return the registry
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @param registry the registry to set
     */
    public void setRegistry(Registry registry) {
        this.registry = registry;
    }
}
