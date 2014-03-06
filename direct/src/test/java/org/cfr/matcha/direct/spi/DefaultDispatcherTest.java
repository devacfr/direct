package org.cfr.matcha.direct.spi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;
import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredStandardMethod;

public class DefaultDispatcherTest extends EasyMockTestCase {

    @Test
    public void constructWithListTest() {
        List<Object> actions = Lists.newArrayList(mock(Object.class));
        replay();
        new DefaultDispatcher(actions);
        verify();
    }

    @Test
    public void constructWithMapTest() {
        Map<Class<? extends Object>, Object> mapActions = new HashMap<Class<? extends Object>, Object>();
        Object action = mock(Object.class);
        mapActions.put(action.getClass(), action);

        replay();
        new DefaultDispatcher(mapActions);
        verify();

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void getActionInstanceTest() {

        List<Object> actions = new ArrayList<Object>();
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
        DefaultDispatcher dispatcher = new DefaultDispatcher(actions);

        Object retrievedAction = dispatcher.dispatch(registeredStandardMethod, new Object[] { "parameter" });
        verify();

        assertEquals("class org.cfr.matcha.direct.spi.MyActioncalled with data parameter", retrievedAction);

    }

}
