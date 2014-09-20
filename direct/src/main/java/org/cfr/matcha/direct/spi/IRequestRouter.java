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
package org.cfr.matcha.direct.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.cfr.matcha.direct.handler.processor.form.UploadFormPostRequestProcessor;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IRequestRouter {

    /**
     *
     */
    public static final String SOURCE_NAME_PREFIX = "/src";

    /**
     *
     * @param reader
     * @param writer
     * @throws IOException
     */
    void processSimpleFormPostRequest(Reader reader, Writer writer) throws IOException;

    /**
     *
     * @return
     */
    UploadFormPostRequestProcessor createUploadFromProcessor();

    /**
     *
     * @param processor
     * @param fileItems
     * @param writer
     * @throws IOException
     */
    void processUploadFormPostRequest(@Nonnull final UploadFormPostRequestProcessor processor,
                                      List<FileItem> fileItems, Writer writer) throws IOException;

    /**
     *
     * @param reader
     * @param writer
     * @throws IOException
     */
    void processJsonRequest(Reader reader, Writer writer) throws IOException;

    /**
     *
     * @param reader
     * @param writer
     * @param pathInfo
     * @throws IOException
     */
    void processPollRequest(Reader reader, Writer writer, String pathInfo) throws IOException;

    /**
     *
     * @param processor
     * @param e
     */
    void handleFileUploadException(@Nonnull final UploadFormPostRequestProcessor processor,
                                   @Nonnull final FileUploadException e);

    /**
     *
     * @param reader
     * @param writer
     * @param pathInfo
     */
    void processSourceRequest(BufferedReader reader, PrintWriter writer, String pathInfo);

}