/*
 * Instincts
 * Copyright (C) 2017  Adithya J
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.pimp.instincts.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.pimp.instincts.InstinctsApplication;
import com.pimp.instincts.R;
import com.pimp.instincts.model.Event;
import com.pimp.instincts.utils.DateUtils;
import com.pimp.instincts.utils.LogHelper;
import com.pimp.instincts.utils.RealmHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.pimp.instincts.utils.DateUtils.dateToCalendar;

public class DayViewActivity extends AppCompatActivity implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener {
    private static final String TAG = LogHelper.makeLogTag(DayViewActivity.class);

    private WeekView weekView;
    private RealmResults<Event> eventRealmResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        InstinctsApplication instinctsApplication = (InstinctsApplication) getApplicationContext();
        RealmHelper realmHelper = instinctsApplication.getRealmHelper();
        Realm realm = realmHelper.getRealmInstance();
        eventRealmResults = realm.where(Event.class)
                .equalTo("type", getIntent().getStringExtra("type")).findAll();

        weekView = (WeekView) findViewById(R.id.weekView);
        weekView.setOnEventClickListener(this);
        weekView.setMonthChangeListener(this);
        weekView.setEventLongPressListener(this);
        weekView.setNumberOfVisibleDays(2);

        setupDateTimeInterpreter();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Calendar calendar = DateUtils.dateToCalendar(simpleDateFormat.parse("08-03-2017 09:00:00"));
            weekView.goToDate(calendar);
            weekView.goToHour(7);
        } catch (Exception e) {
            Log.e("DATE", e.toString());
        }
    }

    private void setupDateTimeInterpreter() {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" dd/MM", Locale.getDefault());
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                if (hour == 12) return 12 + " PM";
                return hour > 12 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();

        for (Event event : eventRealmResults) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = simpleDateFormat.parse(event.getStartTime());
                Calendar startTime = dateToCalendar(date);
                date = simpleDateFormat.parse(event.getEndTime());
                Calendar endTime = dateToCalendar(date);
                WeekViewEvent weekViewEvent = new WeekViewEvent(event.getId(), event.getName(),
                        startTime, endTime);
                weekViewEvent.setColor(Color.parseColor(getColorForType(event.getType())));
                weekViewEvent.setLocation("\n" + event.getLocation());
                events.add(weekViewEvent);
            } catch (Exception e) {
                LogHelper.e(TAG, e.toString());
            }
        }

        List<WeekViewEvent> matchedEvents = new ArrayList<>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
        EventDetailBaseActivity.event = eventRealmResults.where()
                .equalTo("id", weekViewEvent.getId()).findFirst();
        startActivity(new Intent(DayViewActivity.this, EventDetailActivity.class));
    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year &&
                event.getStartTime().get(Calendar.MONTH) == month - 1) ||
                (event.getEndTime().get(Calendar.YEAR) == year &&
                        event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private String getColorForType(String type) {
        switch (type) {
            case "null":
                return "#F2B300";
            case "ELC":
                return "#CC66FF";
            case "Saaral":
                return "#5B9BD5";
            case "Music":
                return "#548235";
            case "Variety":
                return "#F2B300";
            case "Dance":
                return "#FF6600";
            case "Quiz":
                return "#C6E0B4";

            default:
                return "#F2B300";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
