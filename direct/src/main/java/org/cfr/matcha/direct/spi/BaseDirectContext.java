package org.cfr.matcha.direct.spi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.collection.CollectionUtil;
import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.direct.IDirectContext;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.impl.BaseHandler;
import org.cfr.matcha.direct.handler.impl.DirectRequestRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
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
    private Set<Object> directActions;

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

    public BaseDirectContext() {
    }

    /**
     * Registry initialization
     * @throws Exception 
     */
    @Override
    public void init() throws Exception {
        if (!initialized) {

            Assert.hasText(namespace, "namespace is required");
            Assert.hasText(name, "name is required");
            Assert.hasText(getProvidersUrl(), "providersUrl is required");
            //        Assert.notNull(contextPath, "contextPath is required");

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
                directActions = Sets.newHashSet();
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

    public void reset() {
        // TODO [devacfr] this is wrong because the customization is not possible anymore.
        initialized = false;
        this.directDispatcher = null;
        this.directHandlers = null;
        this.apiConfigurations = null;
        this.registry = null;
        this.requestRouter = null;

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
                                                          @Nonnull String actionsNamespace,
                                                          @Nonnull Collection<?> actions) {

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

    /**
     * @return the directActions
     */
    public Set<Object> getDirectActions() {
        return directActions;
    }

    /**
     * @param directActions the directActions to set
     */
    public void setActions(@Nonnull final Set<Object> directActions) {
        if (directActions != null) {
            this.directActions = Collections.unmodifiableSet(directActions);
        }
    }

    public void registerAction(Object bean) {
        if (this.directActions == null) {
            this.directActions = Sets.newHashSet();
        }
        this.directActions.add(bean);
    }

    public static boolean isAction(@Nullable final Object bean) {
        if (bean == null)
            return false;
        return findAnnotation(bean.getClass(), DirectAction.class) != null;
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
        this.directHandlers = Collections.unmodifiableList(directHandlers);
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

    /**
     * Find a single {@link Annotation} of <code>annotationType</code> from the supplied {@link Class},
     * traversing its interfaces and superclasses if no annotation can be found on the given class itself.
     * <p>This method explicitly handles class-level annotations which are not declared as
     * {@link java.lang.annotation.Inherited inherited} <i>as well as annotations on interfaces</i>.
     * <p>The algorithm operates as follows: Searches for an annotation on the given class and returns
     * it if found. Else searches all interfaces that the given class declares, returning the annotation
     * from the first matching candidate, if any. Else proceeds with introspection of the superclass
     * of the given class, checking the superclass itself; if no annotation found there, proceeds
     * with the interfaces that the superclass declares. Recursing up through the entire superclass
     * hierarchy if no match is found.
     * Note : copy From SpringFramework
     * @param clazz the class to look for annotations on
     * @param annotationType the annotation class to look for
     * @return the annotation found, or <code>null</code> if none found
     */
    private static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        Assert.notNull(clazz, "Class must not be null");
        A annotation = clazz.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        for (Class<?> ifc : clazz.getInterfaces()) {
            annotation = findAnnotation(ifc, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (!Annotation.class.isAssignableFrom(clazz)) {
            for (Annotation ann : clazz.getAnnotations()) {
                annotation = findAnnotation(ann.annotationType(), annotationType);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass == Object.class) {
            return null;
        }
        return findAnnotation(superClass, annotationType);
    }

}
