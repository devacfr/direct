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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @param <T>
 */
@XmlRootElement()
public class PaginatedDataSource<T> extends DataSource<T> {

    /**
     *
     */
    private Collection<T> results = null;

    /**
     *
     * @param data
     * @param pageSize
     * @param start
     * @param totalCount
     */
    public PaginatedDataSource(final List<T> data, final int pageSize, final int start, final long totalCount) {
        super(data, pageSize, start, totalCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> getData() {
        return getPagingData(this.getStart());
    }

    /**
     *
     * @param start
     * @return
     */
    protected synchronized Collection<T> getPagingData(final int start) {
        int length = list().size();

        if (results != null) {
            return results;
        }
        if ((getPageSize() >= 0 || start >= 0) && length > 0) {

            List<T> tmp = new ArrayList<T>(getPageSize());
            int end = start + getPageSize();

            if (end > length || end == 0) {
                end = length;
            }

            for (int i = start; i < end; i++) {
                tmp.add(list().get(i));
            }
            results = Collections.unmodifiableCollection(tmp);
            return results;
        }

        return list();
    }
}
