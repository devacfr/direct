package org.cfr.matcha.direct.guice;

import javax.annotation.Nonnull;

import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public abstract class BaseDirectModule extends AbstractModule {

    private Multibinder<Object> actionBinder;

    @Override
    protected void configure() {
        actionBinder = Multibinder.newSetBinder(binder(), Object.class, Names.named("direct.actions"));
        configureActions();
        this.addInitializeListener();
    }

    protected void configureActions() {
        //        bindAction().to(FooAction.class);
        //        bindAction().to(BarAction.class);
        //        // If you need to instantiate an action through a Provider, do this.
        //        bindAction().toProvider(BazActionProvider.class);
        //        // You can also scope stuff:
        //        bindAction().to(MySingletonAction.class).in(Singleton.class);
    }

    protected final LinkedBindingBuilder<Object> bindAction() {
        return actionBinder.addBinding();
    }

    protected void addInitializeListener() {
        Matcher<? super TypeLiteral<?>> matcher = new SubClassesOf(BaseDirectContext.class);

        this.bindListener(matcher, new TypeListener() {

            @Override
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {

                    @Override
                    public void afterInjection(Object injectee) {
                        BaseDirectContext m = (BaseDirectContext) injectee;
                        try {
                            m.init();
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                });
            }
        });
    }

    private static class SubClassesOf extends AbstractMatcher<TypeLiteral<?>> {

        private final Class<?> baseClass;

        private SubClassesOf(@Nonnull final Class<?> baseClass) {
            this.baseClass = baseClass;
        }

        @Override
        public boolean matches(TypeLiteral<?> t) {
            return baseClass.isAssignableFrom(t.getRawType());
        }
    }

}
