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
package org.cfr.matcha.api.response;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.cfr.commons.util.Assert;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 *
 * @param <T>
 */
@XmlRootElement()
public class DataSource<T> {

    /**
     *
     */
    private final long countTotal;

    /**
     *
     */
    private final List<T> data;

    /**
     *
     */
    private final int pageSize;

    /**
     *
     */
    private final int start;

    /**
     *
     * @param data
     * @param pageSize
     * @param start
     * @param totalCount
     */
    public DataSource(final List<T> data, final int pageSize, final int start, final long totalCount) {
        Assert.notNull(data);
        this.countTotal = totalCount;
        this.data = data;
        this.pageSize = pageSize;
        this.start = start;
    }

    /**
     *
     * @return
     */
    public long getCountTotal() {
        return countTotal;
    }

    /**
     *
     * @return
     */
    public Collection<T> getData() {
        return data;
    }

    /**
     *
     * @return
     */
    public List<T> list() {
        return this.data;
    }

    /**
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     *
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     *
     * @return
     */
    public int size() {
        return data.size();
    }

}
