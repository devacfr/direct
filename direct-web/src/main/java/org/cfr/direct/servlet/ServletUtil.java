package org.cfr.direct.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwarementors.extjs.djn.StringUtils;
import com.softwarementors.extjs.djn.router.RequestRouter;
import com.softwarementors.extjs.djn.router.RequestType;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.poll.PollRequestProcessor;
import com.softwarementors.extjs.djn.servlet.DirectJNgineServlet;
import com.softwarementors.extjs.djn.servlet.ServletUtils;

public class ServletUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServletUtil.class);

    /**
     * DO NOT MODIFY This method is extracted from DirectJNgineServlet to change the visibility
     *  {@link DirectJNgineServlet#getFromRequestContentType(HttpServletRequest)}
     * @param request
     * @return
     */
    public static RequestType getFromRequestContentType(HttpServletRequest request) {
        assert request != null;

        String contentType = request.getContentType();
        String pathInfo = request.getPathInfo();

        if (!StringUtils.isEmpty(pathInfo) && pathInfo.startsWith(PollRequestProcessor.PATHINFO_POLL_PREFIX)) {
            return RequestType.POLL;
        } else if (StringUtils.startsWithCaseInsensitive(contentType, "application/json")) {
            return RequestType.JSON;
        } else if (StringUtils.startsWithCaseInsensitive(contentType, "application/x-www-form-urlencoded") && request.getMethod()
                .equalsIgnoreCase("post")) {
            return RequestType.FORM_SIMPLE_POST;
        } else if (ServletFileUpload.isMultipartContent(request)) {
            return RequestType.FORM_UPLOAD_POST;
        } else if (RequestRouter.isSourceRequest(pathInfo)) {
            return RequestType.SOURCE;
        } else {
            String requestInfo = ServletUtils.getDetailedRequestInformation(request);
            RequestException ex = RequestException.forRequestFormatNotRecognized();
            logger.error("Error during file uploader: " + ex.getMessage() + "\nAdditional request information: " + requestInfo, ex);
            throw ex;
        }
    }
}
