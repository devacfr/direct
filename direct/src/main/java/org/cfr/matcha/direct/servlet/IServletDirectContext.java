package org.cfr.matcha.direct.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cfr.matcha.direct.IDirectContext;


import com.softwarementors.extjs.djn.router.RequestType;

public interface IServletDirectContext extends IDirectContext {

    /**
     * 
     * @param request
     * @param response
     * @param type
     */
    public void handleProcess(HttpServletRequest request, HttpServletResponse response, RequestType type);

}