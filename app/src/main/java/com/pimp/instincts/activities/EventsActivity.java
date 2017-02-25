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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pimp.instincts.InstinctsApplication;
import com.pimp.instincts.R;
import com.pimp.instincts.adapters.EventsAdapter;
import com.pimp.instincts.model.Event;
import com.pimp.instincts.utils.LogHelper;
import com.pimp.instincts.utils.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EventsActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(EventsActivity.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        InstinctsApplication instinctsApplication = (InstinctsApplication) getApplicationContext();
        RealmHelper realmHelper = instinctsApplication.getRealmHelper();
        Realm realm = realmHelper.getRealmInstance();
        RealmResults<Event> eventRealmResults = realm.where(Event.class)
                .distinct("type").sort("type", Sort.ASCENDING);
        RecyclerView eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);

        EventsAdapter adapter = new EventsAdapter(this, eventRealmResults);
        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
