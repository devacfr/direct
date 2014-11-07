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
package org.cfr.matcha.handler.processor.form;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.cfr.commons.util.Assert;
import org.cfr.matcha.api.form.Form;
import org.cfr.matcha.api.form.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.form.upload.DiskFileItemFactory2;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class UploadFormPostRequestProcessor extends FormPostRequestProcessorBase {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFormPostRequestProcessor.class);

    public UploadFormPostRequestProcessor(Registry registry, Dispatcher dispatcher,
            GlobalConfiguration globalConfiguration) {
        super(registry, dispatcher, globalConfiguration);
    }

    /**
     *
     * @param fileItems
     * @param writer
     * @throws IOException
     */
    public void process(@Nonnull final List<FileItem> fileItems, @Nonnull final Writer writer) throws IOException {
        Assert.notNull(fileItems);
        Assert.notNull(writer);

        Form formParameters = new Form();
        Map<String, FileItem> fileFields = new HashMap<String, FileItem>();
        for (FileItem item : fileItems) {
            if (item.isFormField()) {
                formParameters.add(item.getFieldName(), item.getString());
            } else {
                fileFields.put(item.getFieldName(), item);
            }
        }

        if (LOGGER.isDebugEnabled()) { // Avoid expensive function calls if logging is not needed
            LOGGER.debug("Request data (UPLOAD FORM)=>" + getFormParametersLogString(formParameters) + " FILES: "
                    + getFileParametersLogString(fileFields));
        }
        String result = process(formParameters, fileFields);

        String resultString = "<html><body><textarea>";
        resultString += result;
        resultString += "</textarea></body></html>";
        writer.write(resultString);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ResponseData data (UPLOAD FORM)=>" + resultString);
        }
    }

    /**
     *
     * @param formParameters
     * @return
     */
    private static String getFormParametersLogString(Form formParameters) {
        StringBuilder result = new StringBuilder();
        for (Parameter entry : formParameters) {
            String fieldName = entry.getName();
            String value = entry.getValue();
            result.append(fieldName);
            result.append("=");
            result.append(value);
            result.append(";");
        }
        return result.toString();
    }

    /**
     *
     * @param fileFields
     * @return
     */
    private static String getFileParametersLogString(Map<String, FileItem> fileFields) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, FileItem> entry : fileFields.entrySet()) {
            FileItem fileItem = entry.getValue();
            String fieldName = entry.getKey();
            result.append(fieldName);
            result.append("=");
            String fileName = fileItem.getName();
            result.append(fileName);
            result.append(";");
        }
        return result.toString();
    }

    /**
     *
     * @return
     */
    public static ServletFileUpload createFileUploader() {
        // Create a factory for disk-based file items
        DiskFileItemFactory2 factory = new DiskFileItemFactory2();
        // Set factory constraints so that files are never written to disk
        final int MAX_UPLOAD_MEMORY_SIZE = Integer.MAX_VALUE;
        // If request size bigger than this, store files to disk
        factory.setSizeThreshold(MAX_UPLOAD_MEMORY_SIZE);
        // Avoid creating a cleaning thread which Google AppEngine will not like!
        factory.setFileCleaningTracker(null);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set upload handler limits
        upload.setSizeMax(MAX_UPLOAD_MEMORY_SIZE);
        upload.setFileSizeMax(MAX_UPLOAD_MEMORY_SIZE);
        return upload;
    }

    /**
     *
     * @param e
     */
    public void handleFileUploadException(@Nonnull final FileUploadException e) {
        Assert.notNull(e);

        com.softwarementors.extjs.djn.router.processor.standard.form.upload.FileUploadException ex = com.softwarementors.extjs.djn.router.processor.standard.form.upload.FileUploadException
                .forFileUploadException(e);
        LOGGER.error(ex.getMessage(), ex);
        throw ex;
    }

}