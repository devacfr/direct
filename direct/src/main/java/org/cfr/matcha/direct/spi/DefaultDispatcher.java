package org.cfr.matcha.direct.spi;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.softwarementors.extjs.djn.api.RegisteredMethod;
import com.softwarementors.extjs.djn.router.dispatcher.DispatcherBase;

public class DefaultDispatcher extends DispatcherBase {

    private Map<Class<? extends Object>, Object> mapActions;

    public DefaultDispatcher(Map<Class<? extends Object>, Object> mapActions) {
        this.mapActions = mapActions;
    }

    public DefaultDispatcher(@Nonnull final List<Object> actions) {
        mapActions = Maps.newHashMap();
        for (Object action : actions) {
            Class<? extends Object> cls = action.getClass();
            mapActions.put(cls, action);
        }
    }

    @Override
    protected Object getInvokeInstanceForNonStaticMethod(RegisteredMethod method) throws Exception {
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