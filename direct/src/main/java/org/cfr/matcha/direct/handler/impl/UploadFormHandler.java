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
package org.cfr.matcha.direct.handler.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.handler.processor.form.UploadFormPostRequestProcessor;
import org.cfr.matcha.direct.servlet.context.DirectHandlerContext;

import com.softwarementors.extjs.djn.router.RequestType;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class UploadFormHandler extends BaseHandler {

    /**
    *
    */
   public static final List<RequestType> ACCEPTED_REQUEST_TYPELIST =
           Collections.unmodifiableList(Arrays.asList(RequestType.FORM_UPLOAD_POST));
   
   
    /**
     *
     */
    private final ServletFileUpload uploader = UploadFormPostRequestProcessor.createFileUploader();

    public final static List<RequestType> acceptedRequestTypeList =
            Collections.unmodifiableList(Arrays.asList(RequestType.FORM_UPLOAD_POST));

    @Override
    public List<RequestType> getAcceptedRequestType() {
        return ACCEPTED_REQUEST_TYPELIST;
    }

    @Override
    protected void doProcess(final IDirectHandlerContext handlerContext) throws Exception {
        handlerContext.setResponseContentType(HTML_CONTENT_TYPE); // MUST be "text/html" for uploads to work!
        UploadFormPostRequestProcessor processor =
                handlerContext.getContext().getRequestRouter().createUploadFromProcessor();
        try {
            if (!(handlerContext instanceof DirectHandlerContext)) {
                throw new IllegalAccessException(
                        "UploadFormHandler use UploadFormPostRequestProcessor wich need to read the request. "
                                + handlerContext.getClass() + " don't provide " + HttpServletRequest.class);
            }

            handlerContext.getContext()
                    .getRequestRouter()
                    .processUploadFormPostRequest(processor,
                        getFileItems(((DirectHandlerContext) handlerContext).getRequest()),
                        handlerContext.getWriter());

        } catch (FileUploadException e) {
            processor.handleFileUploadException(e);
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
