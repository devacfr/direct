package org.cfr.matcha.direct;

import javax.inject.Named;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectMethod;

@DirectAction
@Named
public class MyAction {

    @DirectMethod
    public String myMethod(String test) {
        return this.getClass() + "called with data " + test;
    }
}
