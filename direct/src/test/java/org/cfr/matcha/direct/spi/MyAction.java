package org.cfr.matcha.direct.spi;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectMethod;

@DirectAction
public class MyAction {

    @DirectMethod
    public String myMethod(String test) {
        return this.getClass() + "called with data " + test;
    }
}
