package org.cfr.matcha.direct;

import java.util.List;

import org.cfr.matcha.api.direct.IDirectAction;
import org.cfr.matcha.direct.config.DirectConfiguration;
import org.cfr.matcha.direct.config.DirectContext;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.impl.BaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;
import com.softwarementors.extjs.djn.jscodegen.CodeFileGenerator;

public class BaseDirectFactory implements IDirectFactory, BeanFactoryAware, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** spring beanName prefix key */
    private static final String PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT = "org.cfr.direct.directContext.";

    /**
     * Action Context
     */
    private DirectContext directContext;

    /**
     * List of actions to be published in js Api
     */
    @Autowired
    private List<IDirectAction> directActions;

    private String jsDefaultApiPath = org.apache.commons.lang.StringUtils.EMPTY;

    private String namespace;

    private String name;

    private String providersUrl;

    private DefaultListableBeanFactory beanFactory;

    private boolean defaultCreateSourceFiles;

    private boolean debug;

    private Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass;

    public void init() throws Exception {
        Assert.hasText(namespace, "namespace is required");
        Assert.hasText(name, "name is required");
        Assert.hasText(providersUrl, "providersUrl is required");

        // create directContext if not exist
        if (directContext == null) {
            directContext = createDirectContext();
        }
        directContext.setDirectHandlers(createDirectHandlers());
        directContext.setActions(this.directActions);

        // Autowired directContext
        beanFactory.autowireBeanProperties(this.directContext, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        beanFactory.initializeBean(this.directContext, PREFIX_SPRING_BEAN_NAME_DIRECTCONTEXT + name);

        DirectConfiguration configuration = directContext.createDirectConfiguration(providersUrl);
        directContext.setDirectConfiguration(configuration);

        if (this.gsonBuilderConfiguratorClass != null) {
            configuration.setGsonBuilderConfiguratorClass(this.gsonBuilderConfiguratorClass);
        }

        configuration.setDebug(isDebug());
        configuration.setCreateSourceFiles(isDefaultCreateSourceFiles());

        directContext.init(jsDefaultApiPath, namespace, name, providersUrl);

        if (!CollectionUtils.isEmpty(directContext.getRegistry().getApis())) {
            CodeFileGenerator.updateSource(directContext.getRegistry(), directContext.getDirectConfiguration()
                    .isCreateSourceFiles());

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void setGsonBuilderConfiguratorClass(Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass) {
        this.gsonBuilderConfiguratorClass = gsonBuilderConfiguratorClass;
    }

    @Override
    public Class<? extends GsonBuilderConfigurator> getGsonBuilderConfiguratorClass() {
        return gsonBuilderConfiguratorClass;
    }

    protected List<IDirectHandler> createDirectHandlers() {
        return BaseHandler.DefaultDirectHandlers;
    }

    public DirectContext getDirectContext() {
        return directContext;
    }

    /**
     * {@inheritDoc}
     */

    protected String getDefaultJsApiPath() {
        return jsDefaultApiPath;
    }

    /**
     * {@inheritDoc}
     */
    protected void setDefaultJsApiPath(String jsApiPath) {
        this.jsDefaultApiPath = jsApiPath;
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

    protected DirectContext createDirectContext() {
        return new DirectContext();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    protected boolean isDefaultCreateSourceFiles() {
        return defaultCreateSourceFiles;
    }

    protected void setDefaultCreateSourceFiles(boolean createSourceFiles) {
        this.defaultCreateSourceFiles = createSourceFiles;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
