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
package org.cfr.matcha.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.cfr.matcha.IDirectContext;
import org.cfr.matcha.IRequest;
import org.cfr.matcha.MatchaException;
import org.cfr.matcha.handler.processor.form.UploadFormPostRequestProcessor;
import org.cfr.matcha.servlet.HttpServletDirectRequest;

import com.softwarementors.extjs.djn.router.RequestType;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class UploadFormHandler extends AbstractHandler {

    /**
    *
    */
    public static final List<RequestType> ACCEPTED_REQUEST_TYPELIST =
            Collections.unmodifiableList(Arrays.asList(RequestType.FORM_UPLOAD_POST));

    /**
     *
     */
    private final FileUpload uploader = UploadFormPostRequestProcessor.createFileUploader();

    public final static List<RequestType> acceptedRequestTypeList =
            Collections.unmodifiableList(Arrays.asList(RequestType.FORM_UPLOAD_POST));

    @Override
    public List<RequestType> getAcceptedRequestType() {
        return ACCEPTED_REQUEST_TYPELIST;
    }

    @Override
    public void process(final IRequest request) throws MatchaException {
        request.setResponseContentType(IDirectContext.HTML_CONTENT_TYPE);
        UploadFormPostRequestProcessor processor = request.getContext().getRequestRouter().createUploadFromProcessor();
        try {
            if (!(request instanceof HttpServletDirectRequest)) {
                throw new MatchaException(
                        "UploadFormHandler use UploadFormPostRequestProcessor wich need to read the request. "
                                + request.getClass() + " don't provide " + HttpServletRequest.class);
            }

            request.getContext()
                    .getRequestRouter()
                    .processUploadFormPostRequest(processor,
                        getFileItems(((HttpServletDirectRequest) request).getRequest()),
                        request.getWriter());

        } catch (FileUploadException e) {
            processor.handleFileUploadException(e);
        } catch (IOException e) {
            throw new MatchaException("Unexpected IO error during form uploading process", e);
        }

    }

    /**
     *
     * @param request
     * @return
     * @throws FileUploadException
     */
    @SuppressWarnings("unchecked")
    protected List<FileItem> getFileItems(final HttpServletRequest request) throws FileUploadException {
        return this.uploader.parseRequest(request);
    }
}
