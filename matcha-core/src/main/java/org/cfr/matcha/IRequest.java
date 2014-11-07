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
package org.cfr.matcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Nonnull;

import com.softwarementors.extjs.djn.router.RequestType;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IRequest {

    /**
     * @return the DirectContext
     */
    @Nonnull
    IDirectContext getContext();

    /**
     * @return the requestType
     */
    @Nonnull
    RequestType getRequestType();

    /**
     * @return the Reader
     */
    @Nonnull
    BufferedReader getReader() throws IOException;;

    /**
     * @return the Writer
     */
    @Nonnull
    PrintWriter getWriter() throws IOException;;

    /**
     * Returns any extra path information associated with the URL the client sent when it made this request. The extra
     * path information follows the servlet path but precedes the query string and will start with a "/" character.
     *
     * <p>This method returns <code>null</code> if there
     * was no extra path information.
     *
     *
     * @return  a <code>String</code>, decoded by the web container, specifying
     *          extra path information that comes after the servlet path but before the query string in the request URL;
     *          or <code>null</code> if the URL does not have any extra path information
     *
     */
    @Nonnull
    String getPathInfo();

    /**
     *
     * @param contentType
     */
    void setResponseContentType(@Nonnull String contentType);

}