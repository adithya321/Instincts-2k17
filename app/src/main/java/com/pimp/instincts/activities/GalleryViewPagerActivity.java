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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.pimp.instincts.R;
import com.pimp.instincts.adapters.GalleryViewPagerAdapter;

import java.util.List;
import java.util.Map;

public class GalleryViewPagerActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    public static int selectedIndex = -1;
    private GalleryViewPagerAdapter galleryViewPagerAdapter;
    private ViewPager viewPager;
    private final SharedElementCallback enterTransitionCallback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            View view = null;

            if (viewPager.getChildCount() > 0) {
                view = galleryViewPagerAdapter.getCurrentView(viewPager);
                view = view.findViewById(R.id.gallery_image);
            }

            if (view != null) {
                sharedElements.put(names.get(0), view);
            }
        }
    };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            selectedIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private ViewTreeObserver.OnGlobalLayoutListener pagerLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(pagerLayoutListener);
            ActivityCompat.startPostponedEnterTransition(GalleryViewPagerActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.postponeEnterTransition(this);
        ActivityCompat.setEnterSharedElementCallback(this, enterTransitionCallback);

        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);

        setContentView(R.layout.activity_view_pager_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        galleryViewPagerAdapter = new GalleryViewPagerAdapter(this);

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(galleryViewPagerAdapter);

        int position = selectedIndex = getIntent().getIntExtra(EXTRA_POSITION, 0);
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(pageChangeListener);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(pagerLayoutListener);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
