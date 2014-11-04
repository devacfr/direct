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
package org.cfr.matcha.api.request;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.Assert;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class Query {

    /**
     *
     * @author devacfr<christophefriederich@mac.com>
     *
     */
    public static enum SortDirection {

        /**
         *
         */
        Ascending,

        /**
         *
         */
        Descending;

        /**
         *
         * @return
         */
        public boolean isAscending() {
            return SortDirection.Ascending == this;
        }

    }

    /**
     * Field from.
     */
    private int start = 0;

    /**
     * Field count.
     */
    private int limit = 0;

    /**
     *
     */
    @Nonnull
    private final String query;

    /**
     *
     */
    @Nullable
    private String sortProperty;

    /**
     *
     */
    @Nonnull
    private SortDirection sortDirection = SortDirection.Ascending;

    /**
     *
     */
    public Query(final String query) {
        this.query = Assert.hasText(query, "query is required");
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
    public int getLimit() {
        return limit;
    }

    /**
     *
     * @return
     */
    public @Nonnull String getQuery() {
        return query;
    }

    /**
     *
     * @return
     */
    public @Nullable String getSortProperty() {
        return sortProperty;
    }

    /**
     *
     * @return
     */
    public SortDirection getSortDirection() {
        return sortDirection;
    }

    /**
     *
     * @param start
     */
    public void setStart(final int start) {
        this.start = start;
    }

    /**
     *
     * @param limit
     */
    public void setLimit(final int limit) {
        this.limit = limit;
    }

    /**
     *
     * @param sortProperty
     */
    public void setSortProperty(@Nullable final String sortProperty) {
        this.sortProperty = sortProperty;
    }

    /**
     *
     * @param sortDirection
     */
    public void setSortDirection(@Nonnull final SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

}
