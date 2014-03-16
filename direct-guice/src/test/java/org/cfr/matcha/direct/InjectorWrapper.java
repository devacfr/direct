package org.cfr.matcha.direct;

public interface InjectorWrapper {

    public <T> T getBeanOfType(Class<T> cls);
}
