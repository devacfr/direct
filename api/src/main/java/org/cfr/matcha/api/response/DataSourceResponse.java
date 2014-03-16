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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public final class DataSourceResponse<T> extends DefaultResourceResponse {

    private boolean removable = true;

    private boolean additable = true;

    private boolean editable = true;

    private transient CallbackDataSource<T> callbackDataSource;

    private int dataSourceSize;

    private int limit;

    private int start;

    private long totalCount;

    private Collection<?> dataSource;

    public DataSourceResponse(DataSource<T> dataSource) {
        this(dataSource, null);
    }

    public DataSourceResponse(DataSource<T> dataSource, CallbackDataSource<T> callbackDataSource) {
        this.callbackDataSource = callbackDataSource;
        doPopulate(dataSource);
    }

    private void doPopulate(DataSource<T> dts) {
        if (callbackDataSource != null) {
            dataSource = callbackDataSource.populate(dts.list());
        } else {
            dataSource = dts.list();
        }

        dataSourceSize = dts.size();
        limit = dts.getPageSize();
        start = dts.getStart();
        totalCount = dts.getCountTotal();

    }

    public Collection<?> getDataSource() {
        return dataSource;
    }

    public int getDataSourceSize() {
        return dataSourceSize;
    }

    public int getLimit() {
        return limit;
    }

    public int getStart() {
        return start;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public boolean isAdditable() {
        return additable;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setAdditable(boolean adding) {
        this.additable = adding;
    }

    protected void setDataSource(Collection<?> dataSource) {
        this.dataSource = dataSource;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
}
