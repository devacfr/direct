package org.cfr.matcha.direct.handler.processor.form;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.cfr.matcha.api.form.Form;
import org.cfr.matcha.api.form.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.standard.form.upload.DiskFileItemFactory2;

public class UploadFormPostRequestProcessor extends FormPostRequestProcessorBase {

    private static final Logger logger = LoggerFactory.getLogger(UploadFormPostRequestProcessor.class);

    public UploadFormPostRequestProcessor(Registry registry, Dispatcher dispatcher, GlobalConfiguration globalConfiguration) {
        super(registry, dispatcher, globalConfiguration);
    }

    public void process(List<FileItem> fileItems, Writer writer) throws IOException {
        assert fileItems != null;
        assert writer != null;

        Form formParameters = new Form();
        Map<String, FileItem> fileFields = new HashMap<String, FileItem>();
        for (FileItem item : fileItems) {
            if (item.isFormField()) {
                formParameters.add(item.getFieldName(), item.getString());
            } else {
                fileFields.put(item.getFieldName(), item);
            }
        }

        if (logger.isDebugEnabled()) { // Avoid expensive function calls if logging is not needed
            logger.debug("Request data (UPLOAD FORM)=>" + getFormParametersLogString(formParameters) + " FILES: "
                    + getFileParametersLogString(fileFields));
        }
        String result = process(formParameters, fileFields);

        String resultString = "<html><body><textarea>";
        resultString += result;
        resultString += ("</textarea></body></html>");
        writer.write(resultString);
        if (logger.isDebugEnabled()) {
            logger.debug("ResponseData data (UPLOAD FORM)=>" + resultString);
        }
    }

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

    public void handleFileUploadException(FileUploadException e) {
        assert e != null;

        com.softwarementors.extjs.djn.router.processor.standard.form.upload.FileUploadException ex = com.softwarementors.extjs.djn.router.processor.standard.form.upload.FileUploadException.forFileUploadException(e);
        logger.error(ex.getMessage(), ex);
        throw ex;
    }

}