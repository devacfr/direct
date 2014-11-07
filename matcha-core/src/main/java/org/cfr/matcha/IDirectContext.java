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
package org.cfr.matcha;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.cfr.matcha.handler.IDirectHandler;
import org.cfr.matcha.spi.IRequestRouter;

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

    /**
    *
    */
    public final String JSON_CONTENT_TYPE = "application/json";

    /**
     *
     */
    public final String JAVASCRIPT_CONTENT_TYPE = "text/javascript";

    /**
     *
     */
    public final String HTML_CONTENT_TYPE = "text/html";

    /**
     *
     * @return
     */
    IRequestRouter getRequestRouter();

    /**
     *
     * @param registry
     * @param configuration
     * @param dispatcher
     * @return
     */
    IRequestRouter createRequestRouter(@Nonnull Registry registry, @Nonnull GlobalConfiguration configuration,
                                       @Nonnull Dispatcher dispatcher);

    /**
     *
     * @param name
     * @param apiFile
     * @param fullApiFileName
     * @param apiNamespace
     * @param actionsNamespace
     * @param actions
     * @return
     */
    List<ApiConfiguration> createApiConfigurations(@Nonnull String name, @Nonnull String apiFile,
                                                   @Nonnull String fullApiFileName, @Nonnull String apiNamespace,
                                                   @Nonnull String actionsNamespace, @Nonnull Collection<?> actions);

    /**
     *
     * @return
     */
    List<IDirectHandler> getDirectHandlers();

    /**
     *
     * @return
     */
    List<IDirectHandler> createDirectHandlers();

    /**
     *
     * @param action
     */
    void registerAction(Class<?> action) throws MatchaException;

    /**
     *
     * @param request
     */
    void process(IRequest request);
}