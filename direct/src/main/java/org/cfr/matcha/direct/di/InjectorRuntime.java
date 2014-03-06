package org.cfr.matcha.direct.di;

import javax.inject.Inject;

public abstract class InjectorRuntime {

    @Inject
    private static IInjector injector;

    public void setInjector(IInjector injector) {
        InjectorRuntime.injector = injector;
    }

    public static IInjector getInjector() {
        return injector;
    }
}
