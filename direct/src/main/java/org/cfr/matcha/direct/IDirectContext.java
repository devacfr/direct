package org.cfr.matcha.direct;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.spi.IRequestRouter;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;

/**
 * 
 * @author devacfr
 * @since 1.0
 */
public interface IDirectContext {

    void init() throws Exception;

    IRequestRouter getRequestRouter();

    IRequestRouter createRequestRouter(@Nonnull Registry registry, @Nonnull GlobalConfiguration configuration,
                                       @Nonnull Dispatcher dispatcher);

    List<ApiConfiguration> createApiConfigurations(@Nonnull String name, @Nonnull String apiFile,
                                                   @Nonnull String fullApiFileName, @Nonnull String apiNamespace,
                                                   @Nonnull String actionsNamespace, @Nonnull Collection<?> actions);

    List<IDirectHandler> createDirectHandlers();
}