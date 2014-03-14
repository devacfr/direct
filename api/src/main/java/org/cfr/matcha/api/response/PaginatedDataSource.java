package org.cfr.matcha.api.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class PaginatedDataSource<T> extends DataSource<T> {

    private Collection<T> results = null;

    public PaginatedDataSource(List<T> data, int pageSize, int start, long totalCount) {
        super(data, pageSize, start, totalCount);
        this.results = null;
    }

    @Override
    public Collection<T> getData() {
        return getPagingData(this.getStart());
    }

    protected synchronized Collection<T> getPagingData(int start) {
        int length = list().size();

        if ((getPageSize() >= 0 || start >= 0) && length > 0) {
            if (results == null) {
                List<T> tmp = new ArrayList<T>(getPageSize());
                int end = start + getPageSize();

                if (end > length || end == 0) {
                    end = length;
                }

                for (int i = start; i < end; i++) {
                    tmp.add(list().get(i));
                }
                results = Collections.unmodifiableCollection(tmp);
            }

            return results;
        }

        return list();
    }
}
