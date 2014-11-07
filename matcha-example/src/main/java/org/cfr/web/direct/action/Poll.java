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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.inject.Named;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectPollMethod;
import org.joda.time.DateTime;

@Named
@DirectAction
public class Poll {

    @DirectPollMethod
    public String message(Map<String, String> parameters) {
        assert parameters != null;

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm:ss");
        return "Current time in server: " + formatter.format(now);
    }

    public static class LiveUpdateState {

        public Date date;

        public float visits;

        public float views;

        public float veins;
    }

    private DateTime date;

    private LiveUpdateState last = null;

    private final Random r = new Random();

    @DirectPollMethod
    public LiveUpdateState liveUpdate(Map<String, String> parameters) {
        LiveUpdateState val = new LiveUpdateState();
        if (parameters.containsKey("startDate")) {
            date = DateTime.parse(parameters.get("startDate"));
            last = null;
        }
        if (last == null) {
            val.date = date.toDate();
            val.veins = Math.max(r.nextFloat() * 100, 0);
            val.views = Math.max(r.nextFloat() * 100, 0);
            val.visits = Math.max(r.nextFloat() * 100, 0);
        } else {
            date = date.plusDays(1);
            val.date = date.toDate();
            val.veins = Math.max(last.veins + (r.nextFloat() - 0.5f) * 20, 0);
            val.views = Math.max(last.veins + (r.nextFloat() - 0.5f) * 20, 0);
            val.visits = Math.max(last.visits + (r.nextFloat() - 0.5f) * 20, 0);
        }
        last = val;
        return val;
    }

}
