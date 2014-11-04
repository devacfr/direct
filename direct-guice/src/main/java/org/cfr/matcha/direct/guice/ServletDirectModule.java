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
package org.cfr.matcha.direct.guice;

import org.cfr.matcha.direct.servlet.IServletDirectContext;
import org.cfr.matcha.direct.servlet.ServletDirectContext;

import com.google.inject.name.Names;

public class ServletDirectModule extends BaseDirectModule {

    @Override
    protected void configure() {
        bind(IServletDirectContext.class).to(ServletDirectContext.class);
        super.configure();
        bind(IServletDirectContext.class).annotatedWith(Names.named("DirectContext")).to(ServletDirectContext.class);
    }

}
