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
package org.cfr.matcha.direct.handler.processor;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class StandardRequestData extends RequestData {

    /**
     *
     */
    private final String action;

    /**
     *
     */
    private final String method;

    /**
     *
     */
    private final String type;

    /**
     *
     */
    private final Long tid;

    /**
     *
     * @param type
     * @param action
     * @param method
     * @param tid
     */
    protected StandardRequestData(@Nonnull String type, @Nonnull String action, @Nonnull String method,
            @Nonnull Long tid) {
        Assert.hasText(type);
        Assert.hasText(action);
        Assert.hasText(method);
        Assert.notNull(tid);

        this.type = type;
        this.action = action;
        this.method = method;
        this.tid = tid;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return this.action;
    }

    /**
     *
     * @return
     */
    public String getMethod() {
        return this.method;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @return
     */
    public Long getTid() {
        return this.tid;
    }

    /**
     *
     * @return
     */
    public String getFullMethodName() {
        return getAction() + "." + getMethod();
    }
}