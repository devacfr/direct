package org.cfr.matcha.direct.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cfr.matcha.api.direct.IDirectAction;

import com.softwarementors.extjs.djn.api.RegisteredMethod;
import com.softwarementors.extjs.djn.router.dispatcher.DispatcherBase;

public class SpringDispatcher extends DispatcherBase {

    private Map<Class<? extends IDirectAction>, IDirectAction> mapActions;

    public SpringDispatcher(Map<Class<? extends IDirectAction>, IDirectAction> mapActions) {
        this.mapActions = mapActions;
    }

    public SpringDispatcher(List<IDirectAction> actions) {
        mapActions = new HashMap<Class<? extends IDirectAction>, IDirectAction>(actions.size());
        for (IDirectAction action : actions) {
            mapActions.put(action.getClass(), action);
        }
    }

    @Override
    protected Object getInvokeInstanceForNonStaticMethod(RegisteredMethod method) throws Exception {
        IDirectAction actionInstance = null;
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