/*
 * Instincts 2k17
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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pimp.instincts.R;
import com.pimp.instincts.utils.LogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LogHelper.makeLogTag(EventsActivity.class);

    @BindView(R.id.events_root_ll)
    LinearLayout eventsRootLl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        for (int i = 1; i < eventsRootLl.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) eventsRootLl.getChildAt(i);
            for (int j = 0; j < viewGroup.getChildCount(); j++) {
                LinearLayout linearLayout = (LinearLayout) viewGroup.getChildAt(j);
                linearLayout.setOnClickListener(this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_search:
                startActivity(new Intent(EventsActivity.this, SearchActivity.class)
                        .putExtra("theme", R.style.EventsTheme));
                break;
            case R.id.action_food:
                startActivity(new Intent(EventsActivity.this, MapsActivity.class)
                        .putExtra("location", "Food Stalls"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) ((ViewGroup) view).getChildAt(1);
        startActivity(new Intent(this, EventsListActivity.class).putExtra("section_type",
                textView.getText().toString()));
    }
}
