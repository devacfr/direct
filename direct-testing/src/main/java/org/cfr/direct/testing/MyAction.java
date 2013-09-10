package org.cfr.direct.testing;

import org.cfr.direct.action.IDirectAction;

import com.softwarementors.extjs.djn.config.annotations.DirectMethod;

public class MyAction implements IDirectAction {

    @DirectMethod
    public String myMethod(String test) {
        return this.getClass() + "called with data " + test;
    }

}
