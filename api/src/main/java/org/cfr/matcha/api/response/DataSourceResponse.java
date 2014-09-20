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

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 *
 * @param <T>
 */
@XmlRootElement()
public final class DataSourceResponse<T> extends DefaultResourceResponse {

    /**
     *
     */
    private boolean removable = true;

    /**
     *
     */
    private boolean additable = true;

    /**
     *
     */
    private boolean editable = true;

    /**
     *
     */
    private transient CallbackDataSource<T> callbackDataSource;

    /**
     *
     */
    private int dataSourceSize;

    /**
     *
     */
    private int limit;

    /**
     *
     */
    private int start;

    /**
     *
     */
    private long totalCount;

    /**
     *
     */
    private Collection<?> dataSource;

    /**
     *
     * @param dataSource
     */
    public DataSourceResponse(final DataSource<T> dataSource) {
        this(dataSource, null);
    }

    /**
     *
     * @param dataSource
     * @param callbackDataSource
     */
    public DataSourceResponse(final DataSource<T> dataSource, final CallbackDataSource<T> callbackDataSource) {
        this.callbackDataSource = callbackDataSource;
        doPopulate(dataSource);
    }

    /**
     *
     * @param dts
     */
    private void doPopulate(final DataSource<T> dts) {
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

    /**
     *
     * @return
     */
    public Collection<?> getDataSource() {
        return dataSource;
    }

    /**
     *
     * @return
     */
    public int getDataSourceSize() {
        return dataSourceSize;
    }

    /**
     *
     * @return
     */
    public int getLimit() {
        return limit;
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
    public long getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @return
     */
    public boolean isAdditable() {
        return additable;
    }

    /**
     *
     * @return
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     *
     * @return
     */
    public boolean isRemovable() {
        return removable;
    }

    /**
     *
     * @param adding
     */
    public void setAdditable(final boolean adding) {
        this.additable = adding;
    }

    /**
     *
     * @param dataSource
     */
    protected void setDataSource(final Collection<?> dataSource) {
        this.dataSource = dataSource;
    }

    /**
     *
     * @param editable
     */
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    public void setRemovable(final boolean removable) {
        this.removable = removable;
    }
}
