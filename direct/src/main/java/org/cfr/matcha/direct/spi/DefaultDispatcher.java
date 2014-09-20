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
package org.cfr.matcha.direct.spi;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.softwarementors.extjs.djn.api.RegisteredMethod;
import com.softwarementors.extjs.djn.router.dispatcher.DispatcherBase;

public class DefaultDispatcher extends DispatcherBase {

    private final Map<Class<? extends Object>, Object> mapActions;

    public DefaultDispatcher(Map<Class<? extends Object>, Object> mapActions) {
        this.mapActions = mapActions;
    }

    public DefaultDispatcher(@Nonnull final Collection<Object> actions) {
        mapActions = Maps.newHashMap();
        for (Object action : actions) {
            Class<? extends Object> cls = action.getClass();
            mapActions.put(cls, action);
        }
    }

    @Override
    protected Object getInvokeInstanceForNonStaticMethod(@Nonnull RegisteredMethod method) throws Exception {
        Object actionInstance = null;
        Class<?> instanceClass = method.getActionClass();

        if (mapActions.containsKey(instanceClass)) {
            actionInstance = mapActions.get(instanceClass);
        } else {
            throw new IllegalStateException("No instance in the dispatcher for the requested directAction class "
                    + instanceClass);
        }

        return actionInstance;
    }

}