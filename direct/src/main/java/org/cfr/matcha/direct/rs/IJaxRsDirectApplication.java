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