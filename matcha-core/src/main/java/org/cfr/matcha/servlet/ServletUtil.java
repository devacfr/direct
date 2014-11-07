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
package org.cfr.matcha.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.StringUtils;
import com.softwarementors.extjs.djn.router.RequestRouter;
import com.softwarementors.extjs.djn.router.RequestType;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.poll.PollRequestProcessor;
import com.softwarementors.extjs.djn.servlet.ServletUtils;

/**
 * This utility class contains copy private method of DirectJNgineServlet class.
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public abstract class ServletUtil {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtil.class);

    /**
     * DO NOT MODIFY This method is extracted from DirectJNgineServlet to change the visibility
     * DirectJNgineServlet#getFromRequestContentType(HttpServletRequest).
     * @param request http request to define the type.
     * @return Returns the type of request.
     */
    public static RequestType getFromRequestContentType(final HttpServletRequest request) {
        assert request != null;

        String contentType = request.getContentType();
        String pathInfo = request.getPathInfo();

        if (!StringUtils.isEmpty(pathInfo) && pathInfo.startsWith(PollRequestProcessor.PATHINFO_POLL_PREFIX)) {
            return RequestType.POLL;
        } else if (StringUtils.startsWithCaseInsensitive(contentType, "application/json")) {
            return RequestType.JSON;
        } else if (StringUtils.startsWithCaseInsensitive(contentType, "application/x-www-form-urlencoded")
                && "post".equalsIgnoreCase(request.getMethod())) {
            return RequestType.FORM_SIMPLE_POST;
        } else if (ServletFileUpload.isMultipartContent(request)) {
            return RequestType.FORM_UPLOAD_POST;
        } else if (RequestRouter.isSourceRequest(pathInfo)) {
            return RequestType.SOURCE;
        } else {
            String requestInfo = ServletUtils.getDetailedRequestInformation(request);
            RequestException ex = RequestException.forRequestFormatNotRecognized();
            LOGGER.error("Error during file uploader: " + ex.getMessage() + "\nAdditional request information: "
                    + requestInfo, ex);
            throw ex;
        }
    }
}
