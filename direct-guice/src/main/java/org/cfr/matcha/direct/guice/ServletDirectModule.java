package org.cfr.matcha.direct.guice;

import org.cfr.matcha.direct.servlet.IServletDirectContext;
import org.cfr.matcha.direct.servlet.ServletDirectContext;

import com.google.inject.name.Names;

public class ServletDirectModule extends BaseDirectModule {

    @Override
    protected void configure() {
        bind(IServletDirectContext.class).to(ServletDirectContext.class);
        super.configure();
        bind(IServletDirectContext.class).annotatedWith(Names.named("DirectContext")).to(ServletDirectContext.class);
    }

}
