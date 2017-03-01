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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

public class EventsTabbedActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(EventsTabbedActivity.class);

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public Map<Integer, String> sectionNoToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        String type = getIntent().getStringExtra("section_type");
        for (Map.Entry<Integer, String> entry : sectionNoToString.entrySet()) {
            if (entry.getValue().equals(type)) {
                tabLayout.getTabAt(entry.getKey()).select();
                break;
            }
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

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_TYPE = "section_type";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(String sectionType) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_TYPE, sectionType);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_event, container, false);

            InstinctsApplication instinctsApplication = (InstinctsApplication) getActivity().getApplication();
            RealmHelper realmHelper = instinctsApplication.getRealmHelper();
            Realm realm = realmHelper.getRealmInstance();
            RealmResults<Event> eventRealmResults = realm.where(Event.class)
                    .equalTo("type", getArguments().getString(ARG_SECTION_TYPE))
                    .findAllSorted("startTime");
            RecyclerView eventsRecyclerView = (RecyclerView) rootView.findViewById(R.id.events_recycler_view);

            EventsAdapter adapter = new EventsAdapter(getActivity(), eventRealmResults);
            eventsRecyclerView.setAdapter(adapter);
            eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(sectionNoToString.get(position));
        }

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sectionNoToString.get(position);
        }
    }
}
