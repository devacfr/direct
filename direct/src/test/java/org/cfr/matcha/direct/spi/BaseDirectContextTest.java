package org.cfr.matcha.direct.spi;

import java.util.ArrayList;
import java.util.List;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.direct.di.IInjector;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.impl.DirectHandler;
import org.cfr.matcha.direct.handler.impl.DirectRequestRouter;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;

public class BaseDirectContextTest extends EasyMockTestCase {

    private IInjector injector;

    private String jsApiPath = "target/";

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        injector = mock(IInjector.class);
    }

    /**
     * JsApiPath is mandatory one exception is expected
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void exceptProvidersUrlConstructorTest() throws Exception {
        String providersUrl = "";
        String contextName = "contextName";

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);
        context.setJsApiPath(jsApiPath);

        context.init();
    }

    /**
     * Initialize context without any action must work
     * 
     * @throws Exception
     */
    @Test
    public void initSetNoActionTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        context.setDirectHandlers(getMockDirectHandlers());

        IRequestRouter requestRouter = mock(IRequestRouter.class);

        context.setRequestRouter(requestRouter);

        replay();

        context.init();

        verify();
        assertNotNull(context.getDirectDispatcher());
        assertNotNull(context.getGlobalConfiguration());
        assertTrue(context.getApiConfigurations().size() == 1);

    }

    /**
     * Verifying if all setter works
     * 
     * @throws Exception
     */
    @Test
    public void setterTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        List<Object> actions = new ArrayList<Object>();

        Object action = mock(Object.class);
        actions.add(action);

        DefaultDispatcher dispatcher = mock(DefaultDispatcher.class);

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        context.setActions(actions);
        List<ApiConfiguration> apiConfiguration = getMockApiConfigurations();
        context.setApiConfigurations(apiConfiguration);
        context.setDirectDispatcher(dispatcher);
        List<IDirectHandler> directHandlers = getMockDirectHandlers();
        context.setDirectHandlers(directHandlers);
        Registry registry = mock(Registry.class);
        context.setRegistry(registry);
        DirectRequestRouter requestRouter = mock(DirectRequestRouter.class);

        context.setRequestRouter(requestRouter);

        replay();

        context.init();
        assertNotNull(context.getApiConfigurations().iterator().next().getClasses());

        verify();

        assertEquals(actions, context.getDirectActions());
        assertEquals(apiConfiguration, context.getApiConfigurations());
        assertEquals(dispatcher, context.getDirectDispatcher());
        assertEquals(directHandlers, context.getDirectHandlers());
        assertEquals(registry, context.getRegistry());
        assertEquals(requestRouter, context.getRequestRouter());
    }

    @Test
    public void configurationSettedTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        List<Object> actions = new ArrayList<Object>();

        Object action = mock(Object.class);
        actions.add(action);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        context.setActions(actions);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init();

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertEquals(directHandlers, context.getDirectHandlers());
        assertTrue(context.getDirectActions().size() == 1);
        assertTrue(context.getApiConfigurations().size() == 1);

    }

    @Test
    public void missingProviderConfigurationTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        List<Object> actions = new ArrayList<Object>();

        Object action = mock(Object.class);
        actions.add(action);

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setActions(actions);
        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init();

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertTrue(context.getDirectActions().size() == 1);
        assertTrue(context.getApiConfigurations().size() == 1);

    }

    @Test
    public void initSetOneApiFromOneActionTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        List<Object> actions = Lists.newArrayList(mock(Object.class));

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        context.setActions(actions);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init();

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertTrue(context.getDirectActions().size() == 1);

        assertTrue(context.getApiConfigurations().size() == 1);
    }

    @Test
    public void initByApiConfigurationTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setApiConfigurations(getMockApiConfigurations());

        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init();

        verify();

        assertNotNull(context.getDirectDispatcher());

        List<ApiConfiguration> apiConfigurations = context.getApiConfigurations();
        assertTrue(apiConfigurations.size() == 1);
    }

    @Test
    public void initSetMultiApisTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";

        List<Object> actions = Lists.newArrayList(mock(Object.class));

        BaseDirectContext context = new BaseDirectContext();
        context.setInjector(injector);
        context.setActions(actions);

        context.setJsApiPath(jsApiPath);
        context.setNamespace("ns");
        context.setName(contextName);
        context.setProvidersUrl(providersUrl);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init();

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertTrue(context.getApiConfigurations().size() == 1);
    }

    private List<IDirectHandler> getMockDirectHandlers() {
        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        return directHandlers;

    }

    private List<ApiConfiguration> getMockApiConfigurations() {
        ApiConfiguration api = mock(ApiConfiguration.class);

        List<Class<?>> actionClasses = new ArrayList<Class<?>>();
        actionClasses.add(Object.class);
        EasyMock.expect(api.getClasses()).andReturn(actionClasses);
        EasyMock.expect(api.getName()).andReturn("MockApiName").anyTimes();
        EasyMock.expect(api.getApiNamespace()).andReturn("MockApiNamespace").anyTimes();
        EasyMock.expect(api.getFullApiFileName()).andReturn(jsApiPath + "MockfullApiFileName").anyTimes();
        EasyMock.expect(api.getActionsNamespace()).andReturn("MockactionsNamespace").anyTimes();
        EasyMock.expect(api.getApiFile()).andReturn("content-api.js").anyTimes();
        List<ApiConfiguration> apis = new ArrayList<ApiConfiguration>();
        apis.add(api);
        return apis;
    }
}
