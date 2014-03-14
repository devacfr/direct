package org.cfr.matcha.direct.rs;

import javax.ws.rs.core.UriInfo;

import org.cfr.matcha.direct.IDirectContext;

import com.softwarementors.extjs.djn.router.RequestType;

public interface IJaxRsDirectApplication extends IDirectContext {

    public static final String PROVIDER_URL = "/direct";

    /**
     * 
     * @param input
     * @param uriInfo
     * @param requestType
     * @return
     */
    String handleProcess(String input, UriInfo uriInfo, RequestType requestType);

    /**
     * 
     * @param jsFileName
     * @param minified
     * @return
     */
    String generateSource(String jsFileName, boolean minified);

}