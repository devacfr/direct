package org.cfr.matcha.direct.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestHandler {

    /**
     * Process the given request, generating a response.
     * @param request current HTTP request
     * @param response current HTTP response
     * @throws ServletException in case of general errors
     * @throws IOException in case of I/O errors
     */
    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}