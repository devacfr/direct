package org.cfr.web.direct.action;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.fileupload.FileItem;
import org.cfr.commons.util.Assert;
import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectFormPostMethod;
import org.cfr.matcha.api.direct.DirectMethod;
import org.cfr.matcha.api.form.Form;
import org.cfr.matcha.api.response.JSONFormResponse;

@Named
@DirectAction
public class Profile {

    // Dynamic data: the data itself is a dynamic map, so we can return arbitrary data!
    public static class LocationInfo {

        public boolean success = true;

        public Map<String, String> data = new HashMap<String, String>();

    }

    // Fixed format data: the data itself is an inner Data class
    public static class PhoneInfo {

        public static class Data {

            public String cell;

            public String office;

            public String home;
        }

        public boolean success = true;

        public Data data = new Data();
    }

    // Fixed format data: the data itself is an inner Data class
    public static class BasicInfo {

        public static class Data {

            public String foo;

            public String name;

            public String company;

            public String email;
        }

        public boolean success = true;

        public Data data = new Data();
    }

    @DirectMethod
    public BasicInfo getBasicInfo(Long userId, String foo) {
        Assert.notNull(userId);
        Assert.notNull(foo);

        BasicInfo result = new BasicInfo();
        result.data.foo = foo;
        result.data.name = "Aaron Conran";
        result.data.company = "Ext JS, LLC";
        result.data.email = "aaron@extjs.com";
        return result;
    }

    @DirectMethod
    public PhoneInfo getPhoneInfo(Long userId) {
        Assert.notNull(userId);

        PhoneInfo result = new PhoneInfo();
        result.data.cell = "443-555-1234";
        result.data.office = "1-800-CALLEXT";
        result.data.home = "";
        return result;
    }

    @DirectMethod
    public LocationInfo getLocationInfo(Long userId) {
        Assert.notNull(userId);

        LocationInfo result = new LocationInfo();
        result.data.put("street", "1234 Red Dog Rd.");
        result.data.put("city", "Seminole");
        result.data.put("state", "FL");
        result.data.put("zip", "33776");
        return result;
    }

    @DirectFormPostMethod
    public JSONFormResponse updateBasicInfo(Form formParameters,
                                            Map<String, FileItem> fileFields) {
        Assert.notNull(formParameters);
        Assert.notNull(fileFields);

        JSONFormResponse result = new JSONFormResponse();
        String email = formParameters.getFirstValue("email");
        if (email.equals("aaron@extjs.com")) {
            result.addFieldError("email", "already taken");
        }
        return result;
    }
}
