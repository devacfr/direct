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
package org.cfr.matcha.direct.handler.processor.form;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.cfr.matcha.api.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;

public class FormPostRequestProcessor extends FormPostRequestProcessorBase {

    private static final Logger logger = LoggerFactory.getLogger(FormPostRequestProcessor.class);

    public FormPostRequestProcessor(Registry registry, Dispatcher dispatcher, GlobalConfiguration globalConfiguration) {
        super(registry, dispatcher, globalConfiguration);
    }

    public void process(Reader reader, Writer writer) throws IOException {
        String requestString = IOUtils.toString(reader);
        if (logger.isDebugEnabled()) {
            logger.debug("Request data (SIMPLE FORM)=>" + requestString);
        }
        Form formParameters = new Form(requestString);
        String result = process(formParameters, new HashMap<String, FileItem>());
        writer.write(result);
        if (logger.isDebugEnabled()) {
            logger.debug("ResponseData data (SIMPLE FORM)=>" + result);
        }
    }
}