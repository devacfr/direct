package org.cfr.direct.config;

import java.util.ArrayList;
import java.util.List;

import org.cfr.direct.action.IDirectAction;
import org.cfr.direct.dispatcher.SpringDispatcher;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.impl.DirectHandler;
import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.ApiConfiguration;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.RequestRouter;

public class DirectContextTest extends EasyMockTestCase {

    /**
     * providersUrl is mandatory one exception is expected
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void exceptProvidersUrlConstructorTest() throws Exception {
        String providersUrl = "";
        String contextName = "contextName";

        DirectContext context = new DirectContext();
        context.init("", "ns", contextName, providersUrl);
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
        String jsDefaultApiPath = "jsDefaultApiPath";

        DirectContext context = new DirectContext();

        context.setDirectHandlers(getMockDirectHandlers());

        RequestRouter requestRouter = mock(RequestRouter.class);
        context.setRequestRouter(requestRouter);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();
        assertNotNull(context.getDirectDispatcher());
        assertNotNull(context.getDirectConfiguration());
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
        String jsDefaultApiPath = "jsDefaultApiPath";

        List<IDirectAction> actions = new ArrayList<IDirectAction>();

        DirectConfiguration configuration = mock(DirectConfiguration.class);

        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);

        SpringDispatcher dispatcher = mock(SpringDispatcher.class);

        DirectContext context = new DirectContext();

        context.setActions(actions);
        List<ApiConfiguration> apiConfiguration = getMockApiConfigurations();
        context.setApiConfigurations(apiConfiguration);
        context.setDirectConfiguration(configuration);
        context.setDirectDispatcher(dispatcher);
        List<IDirectHandler> directHandlers = getMockDirectHandlers();
        context.setDirectHandlers(directHandlers);
        Registry registry = mock(Registry.class);
        context.setRegistry(registry);
        RequestRouter requestRouter = mock(RequestRouter.class);
        context.setRequestRouter(requestRouter);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);
        assertNotNull(context.getApiConfigurations().iterator().next().getClasses());

        verify();

        assertEquals(actions, context.getDirectActions());
        assertEquals(apiConfiguration, context.getApiConfigurations());
        assertEquals(configuration, context.getDirectConfiguration());
        assertEquals(dispatcher, context.getDirectDispatcher());
        assertEquals(directHandlers, context.getDirectHandlers());
        assertEquals(registry, context.getRegistry());
        assertEquals(requestRouter, context.getRequestRouter());
    }

    @Test
    public void configurationSettedTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";
        String jsDefaultApiPath = "jsDefaultApiPath";

        List<IDirectAction> actions = new ArrayList<IDirectAction>();

        GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);
        DirectConfiguration configuration = mock(DirectConfiguration.class);

        EasyMock.expect(configuration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();
        EasyMock.expect(configuration.getProvidersUrl()).andReturn(providersUrl);

        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);

        DirectContext context = new DirectContext();
        //   context.setProvidersUrl(null);
        context.setDirectConfiguration(configuration);

        context.setActions(actions);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertEquals(configuration, context.getDirectConfiguration());
        assertEquals(directHandlers, context.getDirectHandlers());
        assertTrue(context.getDirectActions().size() == 1);
        assertTrue(context.getApiConfigurations().size() == 1);
    }

    @Test
    public void missingProviderConfigurationTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";
        String jsDefaultApiPath = "jsDefaultApiPath";

        List<IDirectAction> actions = new ArrayList<IDirectAction>();

        GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);
        DirectConfiguration configuration = mock(DirectConfiguration.class);

        EasyMock.expect(configuration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();

        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);

        DirectContext context = new DirectContext();
        context.setDirectConfiguration(configuration);
        context.setActions(actions);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertEquals(configuration, context.getDirectConfiguration());
        assertTrue(context.getDirectActions().size() == 1);
        assertTrue(context.getApiConfigurations().size() == 1);
    }

    @Test
    public void initSetOneApiFromOneActionTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";
        String jsDefaultApiPath = "jsDefaultApiPath";

        List<IDirectAction> actions = new ArrayList<IDirectAction>();

        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);

        DirectContext context = new DirectContext();

        context.setActions(actions);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertNotNull(context.getDirectConfiguration());

        assertTrue(context.getDirectActions().size() == 1);

        assertTrue(context.getApiConfigurations().size() == 1);
    }

    @Test
    public void initByApiConfigurationTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";
        String jsDefaultApiPath = "jsDefaultApiPath";

        DirectContext context = new DirectContext();
        context.setApiConfigurations(getMockApiConfigurations());

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertNotNull(context.getDirectConfiguration());

        List<ApiConfiguration> apiConfigurations = context.getApiConfigurations();
        assertTrue(apiConfigurations.size() == 1);
    }

    @Test
    public void initSetMultiApisTest() throws Exception {
        String providersUrl = "providersUrl";
        String contextName = "contextName";
        String jsDefaultApiPath = "jsDefaultApiPath";

        List<IDirectAction> actions = new ArrayList<IDirectAction>();
        IDirectAction action = mock(IDirectAction.class);
        actions.add(action);

        List<IDirectAction> actions2 = new ArrayList<IDirectAction>();
        IDirectAction action2 = mock(IDirectAction.class);
        actions2.add(action2);

        DirectContext context = new DirectContext();
        context.setActions(actions);

        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        context.setDirectHandlers(directHandlers);

        replay();

        context.init(jsDefaultApiPath, "ns", contextName, providersUrl);

        verify();

        assertNotNull(context.getDirectDispatcher());
        assertNotNull(context.getDirectConfiguration());
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
        //IDirectAction action = mock(IDirectAction.class);
        actionClasses.add(IDirectAction.class);
        EasyMock.expect(api.getClasses()).andReturn(actionClasses);
        EasyMock.expect(api.getName()).andReturn("MockApiName").anyTimes();
        EasyMock.expect(api.getApiNamespace()).andReturn("MockApiNamespace").anyTimes();
        EasyMock.expect(api.getFullApiFileName()).andReturn("MockfullApiFileName").anyTimes();
        EasyMock.expect(api.getActionsNamespace()).andReturn("MockactionsNamespace").anyTimes();
        EasyMock.expect(api.getApiFile()).andReturn("content-api.js").anyTimes();
        List<ApiConfiguration> apis = new ArrayList<ApiConfiguration>();
        apis.add(api);
        return apis;
    }
}
