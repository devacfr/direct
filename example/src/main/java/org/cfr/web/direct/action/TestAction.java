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
package org.cfr.web.direct.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Named
@DirectAction
public class TestAction {

    @DirectMethod
    public String doEcho(String data) {
        return data;
    }

    @DirectMethod
    public double multiply(String num) {
        double num_ = Double.parseDouble(num);
        return num_ * 8.0;
    }

    public static class Data {

        public String firstName;

        public String lastName;

        public int age;
    }

    @DirectMethod
    public String showDetails(Data data) {
        return "Hi " + data.firstName + " " + data.lastName + ", you are " + data.age + " years old.";
    }

    public static class Node {

        public String id;

        public String text;

        public boolean leaf;
    }

    @DirectMethod
    public List<Node> getTree(String id) {
        List<Node> result = new ArrayList<Node>();
        if ("root".equals(id)) {
            for (int i = 1; i <= 5; ++i) {
                Node node = new Node();
                node.id = "n" + i;
                node.text = "Node " + i;
                node.leaf = false;
                result.add(node);
            }
        } else if (id.length() == 2) {
            String num = id.substring(1);
            for (int i = 1; i <= 5; ++i) {
                Node node = new Node();
                node.id = id + i;
                node.text = "Node " + num + "." + i;
                node.leaf = true;
                result.add(node);
            }
        }
        return result;
    }

    public static class GridRow {

        public GridRow(String name, int turnover) {
            this.name = name;
            this.turnover = turnover;
        }

        public String name;

        public int turnover;
    }

    public static class SortInfo {

        public String property;

        public String direction;
    }

    @DirectMethod
    public List<GridRow> getGrid(JsonArray params) {
        // We know this is the structure, but the 'right' way to do this is
        // to define a Java class that maps the information we are receiving
        JsonObject sortInfo = (JsonObject) params.get(0).getAsJsonObject().get("sort").getAsJsonArray().get(0);

        assert sortInfo != null;
        List<GridRow> data = new ArrayList<GridRow>();
        String field = sortInfo.get("property").getAsString();
        String direction = sortInfo.get("direction").getAsString();

        if ("name".equals(field)) {
            data.add(new GridRow("ABC Accounting", 50000));
            data.add(new GridRow("Ezy Video Rental", 106300));
            data.add(new GridRow("Greens Fruit Grocery", 120000));
            data.add(new GridRow("Icecream Express", 73000));
            data.add(new GridRow("Ripped Gym", 88400));
            data.add(new GridRow("Smith Auto Mechanic", 222980));
        } else {
            data.add(new GridRow("ABC Accounting", 50000));
            data.add(new GridRow("Icecream Express", 73000));
            data.add(new GridRow("Ripped Gym", 88400));
            data.add(new GridRow("Ezy Video Rental", 106300));
            data.add(new GridRow("Greens Fruit Grocery", 120000));
            data.add(new GridRow("Smith Auto Mechanic", 222980));
        }

        if ("DESC".equals(direction)) {
            Collections.reverse(data);
        }

        return data;
    }
}
