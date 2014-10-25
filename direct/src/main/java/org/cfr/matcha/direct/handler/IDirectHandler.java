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
package org.cfr.matcha.direct.handler;

import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;

/**
 * 
 * @author devacfr
 *
 */
public interface IDirectHandler {

    /**
     * 
     */
    public final String JSON_CONTENT_TYPE = "application/json";

    /**
     * 
     */
    public final String JAVASCRIPT_CONTENT_TYPE = "text/javascript"; // *YES*, shoul be "application/javascript", but
                                                                     // then there is IE, and the fact that this is
                                                                     // really cross-browser (sigh!)

    /**
     * 
     */
    public final String HTML_CONTENT_TYPE = "text/html";

    /**
     * 
     * @param handlerContext
     */
    void process(IDirectHandlerContext handlerContext);
}
