package org.cfr.matcha.direct.dispatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.api.direct.IDirectAction;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredStandardMethod;

public class DirectSpringDispatcherTest extends EasyMockTestCase {

    @Test
    public void constructWithListTest() {
        List<IDirectAction> actions = new ArrayList<IDirectAction>();
        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);
        replay();
        new SpringDispatcher(actions);
        verify();
    }

    @Test
    public void constructWithMapTest() {
        Map<Class<? extends IDirectAction>, IDirectAction> mapActions = new HashMap<Class<? extends IDirectAction>, IDirectAction>();
        IDirectAction action = mock(IDirectAction.class);
        mapActions.put(action.getClass(), action);

        replay();
        new SpringDispatcher(mapActions);
        verify();

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void getActionInstanceTest() {

        List<IDirectAction> actions = new ArrayList<IDirectAction>();
        MyAction action = new MyAction();
        actions.add(action);

        RegisteredAction registeredAction = mock(RegisteredAction.class);

        Class actionClass = action.getClass();
        EasyMock.expect(registeredAction.getActionClass()).andReturn(actionClass).anyTimes();

        RegisteredStandardMethod registeredStandardMethod = mock(RegisteredStandardMethod.class);
        Method m = ReflectionUtils.findMethod(action.getClass(), "myMethod", new Class<?>[] { String.class });
        expect(registeredStandardMethod.getParameterCount()).andReturn(1).anyTimes();
        expect(registeredStandardMethod.getMethod()).andReturn(m).anyTimes();
        expect(registeredStandardMethod.getActionClass()).andReturn(actionClass);

        replay();
        SpringDispatcher dispatcher = new SpringDispatcher(actions);

        Object retrievedAction = dispatcher.dispatch(registeredStandardMethod, new Object[] { "parameter" });
        verify();

        assertEquals("class org.cfr.matcha.direct.dispatcher.MyActioncalled with data parameter", retrievedAction);

    }

}
