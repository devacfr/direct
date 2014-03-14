package org.cfr.matcha.direct.guice;

import java.util.Set;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;
import org.cfr.matcha.direct.rs.IJaxRsDirectApplication;
import org.cfr.matcha.direct.rs.JaxRsDirectApplication;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class JaxRsDirectModule extends BaseDirectModule {

    private String namespace;

    private String name;

    @Override
    protected void configure() {
        Assert.notNull(namespace, "namespace is required");
        Assert.notNull(name, "name is required");
        bindConstant().annotatedWith(Names.named("direct.namespace")).to(namespace);
        bindConstant().annotatedWith(Names.named("direct.name")).to(name);
        super.configure();
        bind(IJaxRsDirectApplication.class).annotatedWith(Names.named("DirectApplication"))
                .to(GuiceDirectApplication.class);

    }

    public JaxRsDirectModule namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public JaxRsDirectModule name(String name) {
        this.name = name;
        return this;
    }

    private static class GuiceDirectApplication extends JaxRsDirectApplication {

        @Inject
        @Override
        public void setNamespace(@Named("direct.namespace") String namespace) {
            super.setNamespace(namespace);
        }

        @Inject
        @Override
        public void setName(@Named("direct.name") String name) {
            super.setName(name);
        }

        @Inject
        @Override
        public void setActions(@Named("direct.actions") @Nonnull Set<Object> directActions) {
            super.setActions(directActions);
        }

    }
}
