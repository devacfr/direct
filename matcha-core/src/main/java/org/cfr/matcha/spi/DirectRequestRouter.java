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
package org.cfr.matcha.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.cfr.commons.util.Assert;
import org.cfr.matcha.handler.processor.form.FormPostRequestProcessor;
import org.cfr.matcha.handler.processor.form.UploadFormPostRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.poll.PollRequestProcessor;
import com.softwarementors.extjs.djn.router.processor.standard.json.JsonRequestProcessor;

/**
 *
 * @author devacfr
 * @since 1.0
 */
public class DirectRequestRouter implements IRequestRouter {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectRequestRouter.class);

    /**
     *
     */
    private final Registry registry;

    /**
     *
     */
    private final Dispatcher dispatcher;

    /**
     *
     */
    private final GlobalConfiguration globalConfiguration;

    /**
     *
     * @param registry
     * @param globalConfiguration
     * @param dispatcher
     */
    public DirectRequestRouter(final Registry registry, final GlobalConfiguration globalConfiguration,
            final Dispatcher dispatcher) {
        this.registry = registry;
        this.dispatcher = dispatcher;
        this.globalConfiguration = globalConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processSimpleFormPostRequest(Reader reader, Writer writer) throws IOException {
        FormPostRequestProcessor processor =
                new FormPostRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration);
        processor.process(reader, writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadFormPostRequestProcessor createUploadFromProcessor() {
        return new UploadFormPostRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processUploadFormPostRequest(@Nonnull final UploadFormPostRequestProcessor processor,
                                             @Nonnull final List<FileItem> fileItems, @Nonnull final Writer writer)
                                                     throws IOException {
        Assert.notNull(processor, "processor parmeter is required");
        processor.process(fileItems, writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processJsonRequest(final Reader reader, final Writer writer) throws IOException {
        new JsonRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration).process(reader, writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processPollRequest(final Reader reader, final Writer writer, final String pathInfo) throws IOException {
        new PollRequestProcessor(this.registry, this.dispatcher, this.globalConfiguration).process(reader,
            writer,
            pathInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFileUploadException(@Nonnull final UploadFormPostRequestProcessor processor,
                                          @Nonnull final FileUploadException e) {
        Assert.notNull(processor);
        Assert.notNull(e);

        processor.handleFileUploadException(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processSourceRequest(@Nonnull final BufferedReader reader, @Nonnull final PrintWriter writer,
                                     @Nonnull final String pathInfo) {
        Assert.notNull(reader);
        Assert.notNull(writer);
        Assert.notNull(pathInfo);

        int lastIndex = pathInfo.lastIndexOf(SOURCE_NAME_PREFIX);
        int position = lastIndex + SOURCE_NAME_PREFIX.length();
        String sourceName = pathInfo.substring(position + 1);
        if (!this.registry.hasSource(sourceName)) {
            RequestException ex = RequestException.forSourceNotFound(sourceName);
            LOGGER.error(sourceName);
            throw ex;
        }
        String code = this.registry.getSource(sourceName);
        assert code != null;
        writer.append(code);
    }

    /**
     *
     * @param pathInfo
     * @return
     */
    public static boolean isSourceRequest(final String pathInfo) {
        Assert.notNull(pathInfo);

        int lastIndex = pathInfo.lastIndexOf(SOURCE_NAME_PREFIX);
        boolean isSource = lastIndex >= 0;
        return isSource;
    }
}
