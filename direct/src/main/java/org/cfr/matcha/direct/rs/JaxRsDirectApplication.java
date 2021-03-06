package org.cfr.matcha.direct.rs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.rs.context.DirectJaxRsHandlerContext;
import org.cfr.matcha.direct.spi.BaseDirectContext;

import com.google.common.collect.Sets;
import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.jscodegen.ApiCodeGenerator;
import com.softwarementors.extjs.djn.jscodegen.Minifier;
import com.softwarementors.extjs.djn.router.RequestType;

@Named("DirectApplication")
@Singleton
public class JaxRsDirectApplication extends BaseDirectContext implements IJaxRsDirectApplication, Provider<Application> {

    private final Application rsApplication;

    public JaxRsDirectApplication() {
        this.rsApplication = createApplicaton();
        super.setCreateSourceFiles(false);
        super.setProvidersUrl(PROVIDER_URL);
    }

    @Override
    public Application get() {
        return rsApplication;
    }

    protected Application createApplicaton() {
        return new Application() {

            @Override
            public Set<Class<?>> getClasses() {
                return Sets.<Class<?>> newHashSet(DirectHandlerResource.class, DirectJSResource.class);
            }
        };
    }

    @Override
    public String handleProcess(String input, UriInfo uriInfo, RequestType requestType) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            writer = new PrintWriter(stringWriter);
            IDirectHandlerContext handlerContext = new DirectJaxRsHandlerContext(this, requestType, uriInfo.getPath(),
                    reader, writer);
            for (IDirectHandler handler : getDirectHandlers()) {
                handler.process(handlerContext);
            }
            return stringWriter.toString();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    public void setCreateSourceFiles(boolean createSourceFiles) {
        // do nothing
    }

    @Override
    public void setProvidersUrl(String providersUrl) {
        // do nothing
    }

    @Override
    public String generateSource(String jsFileName, boolean minified) {
        StringBuilder jsBuilder = new StringBuilder();
        for (RegisteredApi api : getRegistry().getApis()) {
            ApiCodeGenerator generator = new ApiCodeGenerator(getGlobalConfiguration(), api);
            generator.appendCode(jsBuilder, minified);

        }
        String jsGenerated = jsBuilder.toString();
        if (minified) {
            jsGenerated = minifiedJsString(jsFileName, jsGenerated);
        }
        return jsGenerated;
    }

    protected String minifiedJsString(String jsFileName, String js) {
        String minified = null;
        try {
            minified = Minifier.minify(js, jsFileName, js.length());
        } catch (Exception e) {
            logger.warn("Minifying failed return not minified JS.", e);
        } finally {
            if (minified == null) {
                minified = js;
            }
        }
        return minified;
    }

}
