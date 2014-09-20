/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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