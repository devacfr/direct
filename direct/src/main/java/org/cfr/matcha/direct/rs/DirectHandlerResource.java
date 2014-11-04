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
package org.cfr.matcha.direct.rs;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.softwarementors.extjs.djn.router.RequestType;

@Named
@Path(IJaxRsDirectApplication.PROVIDER_URL)
public class DirectHandlerResource {

    /** Action Context  */
    @Inject
    @Named("DirectApplication")
    private IJaxRsDirectApplication directApplication;

    public DirectHandlerResource() {
    }

    /**
     * JSON method using POST Method
     *
     * @param uriInfo
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String handleJSONPost(@Context UriInfo uriInfo, String json) {

        return handle(json, uriInfo, RequestType.JSON);
    }

    /**
     * POLL method using GET Method
     *
     * @param uriInfo
     * @return
     */
    @GET
    @Path("poll")
    public String handlePollGet(@Context UriInfo uriInfo) {

        return handle("", uriInfo, RequestType.POLL);
    }

    /**
     * POLL method using POST Method
     *
     * @param uriInfo
     * @return
     */
    @POST
    @Path("poll")
    public String handlePollPost(@Context UriInfo uriInfo) {

        return handle("", uriInfo, RequestType.POLL);
    }

    /**
     * FileUpload method
     *
     * @param input
     * @param uriInfo
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String handleFormUrlEncodedPost(@Context UriInfo uriInfo, String input) {

        return handle(input, uriInfo, RequestType.FORM_SIMPLE_POST);
    }

    protected String handle(String input, UriInfo uriInfo, RequestType requestType) {
        return directApplication.handleProcess(input, uriInfo, requestType);
    }

    public void setDirectApplication(IJaxRsDirectApplication directManager) {
        this.directApplication = directManager;
    }

}
