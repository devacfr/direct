package org.cfr.direct.handler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.cfr.direct.handler.processor.form.FormPostRequestProcessor;
import org.cfr.direct.handler.processor.form.UploadFormPostRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.poll.PollRequestProcessor;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessor;

public class DirectRequestRouter {

    private static final Logger logger = LoggerFactory.getLogger(DirectRequestRouter.class);

    private Registry registry;

    private Dispatcher dispatcher;

    private GlobalConfiguration globalConfiguration;

    public DirectRequestRouter(Registry registry, GlobalConfiguration globalConfiguration, Dispatcher dispatcher) {
        this.registry = registry;
        this.dispatcher = dispatcher;
        this.globalConfiguration = globalConfiguration;
    }

    public void processSimpleFormPostRequest(Reader reader, Writer writer) throws IOException {
        FormPostRequestProcessor processor = new FormPostRequestProcessor(this.registry, this.dispatcher,
                this.globalConfiguration);
        processor.process(reader, writer);
    }

    public UploadFormPostRequestProcessor createUploadFromProcessor() {
        return new UploadFormPostRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration);
    }

    public void processUploadFormPostRequest(UploadFormPostRequestProcessor processor, List<FileItem> fileItems,
                                             Writer writer) throws IOException {
        assert processor != null;

        processor.process(fileItems, writer);
    }

    public void processJsonRequest(Reader reader, Writer writer) throws IOException {
        new JsonRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration).process(reader, writer);
    }

    public void processPollRequest(Reader reader, Writer writer, String pathInfo) throws IOException {
        new PollRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration).process(reader,
            writer,
            pathInfo);
    }

    public void handleFileUploadException(UploadFormPostRequestProcessor processor, FileUploadException e) {
        assert e != null;

        processor.handleFileUploadException(e);
    }

    public static final String SOURCE_NAME_PREFIX = "/src";

    public void processSourceRequest(BufferedReader reader, PrintWriter writer, String pathInfo) {
        assert reader != null;
        assert writer != null;
        assert pathInfo != null;

        int lastIndex = pathInfo.lastIndexOf(SOURCE_NAME_PREFIX);
        int position = lastIndex + SOURCE_NAME_PREFIX.length();
        String sourceName = pathInfo.substring(position + 1);
        if (!this.registry.hasSource(sourceName)) {
            RequestException ex = RequestException.forSourceNotFound(sourceName);
            logger.error(sourceName);
            throw ex;
        }
        String code = this.registry.getSource(sourceName);
        assert code != null;
        writer.append(code);
    }

    public static boolean isSourceRequest(String pathInfo) {
        assert pathInfo != null;

        int lastIndex = pathInfo.lastIndexOf(SOURCE_NAME_PREFIX);
        boolean isSource = lastIndex >= 0;
        return isSource;
    }
}
