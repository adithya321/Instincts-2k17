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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pimp.instincts.InstinctsApplication;
import com.pimp.instincts.R;
import com.pimp.instincts.adapters.EventsAdapter;
import com.pimp.instincts.model.Event;
import com.pimp.instincts.utils.LogHelper;
import com.pimp.instincts.utils.RealmHelper;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventsListActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(EventsListActivity.class);
    public Map<Integer, String> sectionNoToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event);

        String type = getIntent().getStringExtra("section_type");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sectionNoToString = new HashMap<>();
        sectionNoToString.put(0, "Quiz");
        sectionNoToString.put(1, "Fine Arts");
        sectionNoToString.put(2, "Variety");
        sectionNoToString.put(3, "ELC");
        sectionNoToString.put(4, "Music");
        sectionNoToString.put(5, "Dance");
        sectionNoToString.put(6, "LOP");
        sectionNoToString.put(7, "Photography");
        sectionNoToString.put(8, "Saaral");
        sectionNoToString.put(9, "Film Club");
        sectionNoToString.put(10, "Others");

        InstinctsApplication instinctsApplication = (InstinctsApplication) getApplication();
        RealmHelper realmHelper = instinctsApplication.getRealmHelper();
        Realm realm = realmHelper.getRealmInstance();
        RealmResults<Event> eventRealmResults = realm.where(Event.class)
                .equalTo("type", getIntent().getStringExtra("section_type"))
                .findAllSorted("startTime");
        RecyclerView eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);

        EventsAdapter adapter = new EventsAdapter(this, eventRealmResults);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
