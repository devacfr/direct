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
package org.cfr.matcha.spi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.MyAction;
import org.cfr.matcha.spi.DefaultDispatcher;
import org.easymock.EasyMock;
import org.easymock.internal.ReflectionUtils;
import org.junit.Test;

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

        assertEquals("class org.cfr.matcha.direct.MyActioncalled with data parameter", retrievedAction);

    }

}
