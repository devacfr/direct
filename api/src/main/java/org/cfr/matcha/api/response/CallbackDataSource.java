package org.cfr.matcha.api.response;

import java.util.Collection;

public interface CallbackDataSource<T> {

    Collection<?> populate(Collection<T> source);
}
