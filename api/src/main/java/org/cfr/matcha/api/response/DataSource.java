/**
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

@XmlRootElement()
public class DataSource<T> {

    private final long countTotal;

    private final List<T> data;

    private final int pageSize;

    private final int start;

    public DataSource(List<T> data, int pageSize, int start, long totalCount) {
        Assert.notNull(data);
        this.countTotal = totalCount;
        this.data = data;
        this.pageSize = pageSize;
        this.start = start;
    }

    public long getCountTotal() {
        return countTotal;
    }

    public Collection<T> getData() {
        return data;
    }

    public List<T> list() {
        return this.data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStart() {
        return start;
    }

    public int size() {
        return data.size();
    }

}
