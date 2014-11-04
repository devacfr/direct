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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlRootElement;

import org.cfr.commons.util.Assert;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
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
    private final DataSource<T> dataSource;

    /**
     *
     * @param dataSource
     */
    public DataSourceResponse(@Nonnull final DataSource<T> dataSource) {
        this(dataSource, null);
    }

    /**
     *
     * @param dataSource
     * @param callbackDataSource
     */
    public DataSourceResponse(@Nonnull final DataSource<T> dataSource,
            @Nullable final CallbackDataSource<T> callbackDataSource) {
        this.callbackDataSource = callbackDataSource;
        this.dataSource = Assert.notNull(dataSource);
    }

    /**
     *
     * @return
     */
    public Collection<?> getData() {
        if (callbackDataSource != null) {
            return callbackDataSource.populate(dataSource.list());
        } else {
            return dataSource.list();
        }
    }

    /**
     *
     * @return
     */
    public int getDataSourceSize() {
        return dataSource.size();
    }

    /**
     *
     * @return
     */
    public int getLimit() {
        return dataSource.getPageSize();
    }

    /**
     *
     * @return
     */
    public int getStart() {
        return dataSource.getStart();
    }

    /**
     *
     * @return
     */
    public long getTotalCount() {
        return dataSource.getCountTotal();
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
    public DataSourceResponse<T> additable(final boolean adding) {
        this.additable = adding;
        return this;
    }

    /**
     *
     * @param editable
     */
    public DataSourceResponse<T> editable(final boolean editable) {
        this.editable = editable;
        return this;
    }

    /**
     *
     * @param removable
     */
    public DataSourceResponse<T> removable(final boolean removable) {
        this.removable = removable;
        return this;
    }
}
