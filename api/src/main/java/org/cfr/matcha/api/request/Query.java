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

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
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
    private String query;

    /**
     *
     */
    private String sortProperty;

    /**
     *
     */
    private SortDirection sortDirection = SortDirection.Ascending;

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
    public String getQuery() {
        return query;
    }

    /**
     *
     * @return
     */
    public String getSortProperty() {
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
     * @param query
     */
    public void setQuery(final String query) {
        this.query = query;
    }

    /**
     *
     * @param sortProperty
     */
    public void setSortProperty(final String sortProperty) {
        this.sortProperty = sortProperty;
    }

    /**
     *
     * @param sortDirection
     */
    public void setSortDirection(final SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

}
