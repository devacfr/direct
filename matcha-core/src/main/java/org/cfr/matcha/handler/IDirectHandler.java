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
package org.cfr.matcha.handler;

import javax.annotation.Nonnull;

import org.cfr.matcha.IRequest;
import org.cfr.matcha.MatchaException;

/**
 *
 * @author devacfr
 *
 */
public interface IDirectHandler {

    /**
     *
     * @param request
     * @return
     */
    boolean acceptRequest(@Nonnull final IRequest request);

    /**
     *
     * @param handlerContext
     */
    void process(IRequest handlerContext) throws MatchaException;
}
