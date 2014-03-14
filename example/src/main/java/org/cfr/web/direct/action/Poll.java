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

    @DirectPollMethod(event = "message")
    public String handleMessagePoll(Map<String, String> parameters) {
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

    @DirectPollMethod(event = "liveUpdate")
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
            val.veins = (float) Math.max(last.veins + (r.nextFloat() - 0.5) * 20, 0);
            val.views = (float) Math.max(last.veins + (r.nextFloat() - 0.5) * 20, 0);
            val.visits = (float) Math.max(last.visits + (r.nextFloat() - 0.5) * 20, 0);
        }
        last = val;
        return val;
    }
}
