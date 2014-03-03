package org.cfr.direct.handler.processor.form;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
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