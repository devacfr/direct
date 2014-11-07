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

import com.softwarementors.extjs.djn.DirectJNgineException;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class MatchaException extends DirectJNgineException {

    /**
     *  serialVersionUID.
     */
    private static final long serialVersionUID = 3116047088623234248L;

    public MatchaException(final String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public MatchaException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
