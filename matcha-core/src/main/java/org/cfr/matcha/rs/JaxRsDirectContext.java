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
package org.cfr.matcha.rs;

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
import org.cfr.matcha.IRequest;
import org.cfr.matcha.spi.BaseDirectContext;

import com.google.common.collect.Sets;
import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.jscodegen.ApiCodeGenerator;
import com.softwarementors.extjs.djn.jscodegen.Minifier;
import com.softwarementors.extjs.djn.router.RequestType;

@Named("DirectApplication")
@Singleton
public class JaxRsDirectContext extends BaseDirectContext implements IJaxRsDirectApplication, Provider<Application> {

    private final Application rsApplication;

    public JaxRsDirectContext() {
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
                return Sets.<Class<?>> newHashSet(JaxRsDirectResource.class, JaxRsDirectJSResource.class);
            }
        };
    }

    @Override
    public String handleProcess(final String input, final UriInfo uriInfo, final RequestType requestType) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            writer = new PrintWriter(stringWriter);
            IRequest request = new JaxRsDirectRequest(this, requestType, uriInfo.getPath(), reader, writer);
            this.process(request);
            return stringWriter.toString();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    public void setCreateSourceFiles(final boolean createSourceFiles) {
        // do nothing
    }

    @Override
    public void setProvidersUrl(final String providersUrl) {
        // do nothing
    }

    @Override
    public String generateSource(final String jsFileName, final boolean minified) {
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

    protected String minifiedJsString(final String jsFileName, final String js) {
        String minified = null;
        try {
            minified = Minifier.minify(js, jsFileName, js.length());
        } catch (Exception e) {
            getLogger().warn("Minifying failed return not minified JS.", e);
        } finally {
            if (minified == null) {
                minified = js;
            }
        }
        return minified;
    }

}
