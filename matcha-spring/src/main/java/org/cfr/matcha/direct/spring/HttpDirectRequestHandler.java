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
package org.cfr.matcha.direct.spring;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.cfr.matcha.servlet.IServletDirectContext;
import org.springframework.web.HttpRequestHandler;

import com.softwarementors.extjs.djn.EncodingUtils;
import com.softwarementors.extjs.djn.Timer;

@Named
public class HttpDirectRequestHandler implements HttpRequestHandler {

    private final IServletDirectContext directManager;

    @Inject
    public HttpDirectRequestHandler(final IServletDirectContext directManager) {
        this.directManager = directManager;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    IOException {
        BufferedRequestWrapper req = new BufferedRequestWrapper(request);
        Timer timer = new Timer();
        try {

            String requestEncoding = req.getCharacterEncoding();
            // If we don't know what the request encoding is, assume it to
            // be UTF-8
            if (StringUtils.isEmpty(requestEncoding)) {
                request.setCharacterEncoding(EncodingUtils.UTF8);
            }
            response.setCharacterEncoding(EncodingUtils.UTF8);
            directManager.handleProcess(req, response);
        } finally {
            timer.stop();
            timer.logDebugTimeInMilliseconds("Total servlet processing time");
        }

    }

}
