package org.cfr.direct;

import com.softwarementors.extjs.djn.gson.GsonBuilderConfigurator;

public interface IDirectFactory {

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @param name
     */
    void setName(String name);

    /**
     * 
     * @return
     */
    String getNamespace();

    /**
     * 
     * @param namespace
     */
    void setNamespace(String namespace);

    /**
     * 
     * @return
     */
    String getProvidersUrl();

    /**
     * 
     * @param providersUrl
     */
    void setProvidersUrl(String providersUrl);

    boolean isDebug();

    void setDebug(boolean debug);

    /**
     * 
     * @param gsonBuilderConfiguratorClass
     */
    void setGsonBuilderConfiguratorClass(Class<? extends GsonBuilderConfigurator> gsonBuilderConfiguratorClass);

    /**
     * 
     * @return
     */
    Class<? extends GsonBuilderConfigurator> getGsonBuilderConfiguratorClass();

}