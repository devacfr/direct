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
package org.cfr.matcha.api.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.cfr.matcha.api.request.Query.SortDirection;

import com.google.gson.GsonBuilder;

@XmlRootElement()
public class QueryRequest {

    private static GsonBuilder gson = new GsonBuilder();

    public static Query createQuery(QueryRequest queryRequest) {
        Query query = new Query();
        query.setQuery(queryRequest.getQuery());
        query.setStart(queryRequest.getStart());
        query.setLimit(queryRequest.getLimit());
        if (queryRequest.getSortDirection() != null) {
            if ("ASC".equals(queryRequest.getSortDirection())) {
                query.setSortDirection(SortDirection.Ascending);
            } else {
                query.setSortDirection(SortDirection.Descending);
            }
        }
        query.setSortProperty(queryRequest.getSortProperty());
        return query;
    }

    /**
     * Field from.
     */
    private final int start = 0;

    /**
     * Field count.
     */
    private final int limit = 0;

    private String query;

    private String sortProperty;

    private String sortDirection;

    private Filter[] filters;

    // TODO [devacfr] request restlet constructor, find better to remove restlet dependency
    //    public QueryRequest(Request request) {
    //        Form form = request.getResourceRef().getQueryAsForm();
    //        query = form.getFirstValue("query");
    //        if (form.getFirstValue("start") != null)
    //            start = Integer.parseInt(form.getFirstValue("start"));
    //        if (form.getFirstValue("limit") != null)
    //            limit = Integer.parseInt(form.getFirstValue("limit"));
    //        if (form.getFirstValue("filter") != null) {
    //
    //            JSONArray ar = JSONArray.fromObject(form.getFirstValue("filter"));
    //            Filter[] fs = new Filter[ar.size()];
    //            int x = 0;
    //            for (@SuppressWarnings("unchecked")
    //            Iterator<JSONObject> i = ar.iterator(); i.hasNext();) {
    //                JSONObject jsonObject = i.next();
    //                Filter f = (Filter) JSONObject.toBean(jsonObject, Filter.class);
    //                fs[x++] = f;
    //            }
    //            filters = fs;
    //        }
    //        sortProperty = form.getFirstValue("sort");
    //        sortDirection = form.getFirstValue("dir");
    //    }

    public static Filter[] jsonToFilter(String filter) {
        if (StringUtils.isEmpty(filter)) {
            return null;
        }

        return gson.create().fromJson(filter, Filter[].class);
    }

    public Filter[] getFilters() {
        return filters;
    }

    public Filter getFilter(String propertyName) {
        if (filters == null || filters.length == 0 || StringUtils.isEmpty(propertyName)) {
            return null;
        }
        for (Filter f : filters) {
            if (f.getProperty().equals(propertyName)) {
                return f;
            }
        }
        return null;
    }

    public int getLimit() {
        return limit;
    }

    public String getQuery() {
        return query;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public int getStart() {
        return start;
    }

}
