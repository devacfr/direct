package org.cfr.matcha.direct.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.cfr.matcha.direct.handler.processor.form.UploadFormPostRequestProcessor;

public interface IRequestRouter {

    public static final String SOURCE_NAME_PREFIX = "/src";

    void processSimpleFormPostRequest(Reader reader, Writer writer) throws IOException;

    UploadFormPostRequestProcessor createUploadFromProcessor();

    void
            processUploadFormPostRequest(UploadFormPostRequestProcessor processor, List<FileItem> fileItems,
                                         Writer writer) throws IOException;

    void processJsonRequest(Reader reader, Writer writer) throws IOException;

    void processPollRequest(Reader reader, Writer writer, String pathInfo) throws IOException;

    void handleFileUploadException(UploadFormPostRequestProcessor processor, FileUploadException e);

    void processSourceRequest(BufferedReader reader, PrintWriter writer, String pathInfo);

}