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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;
import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.pimp.instincts.R;
import com.pimp.instincts.model.LocalJSONSource;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);

        setContentView(R.layout.activity_home);

        new LocalJSONSource(this);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        ImageView spinArrowImageView = (ImageView) findViewById(R.id.spin_arrow);
        spinArrowImageView.getLayoutParams().width = displayMetrics.widthPixels / 2;

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("", 0x0);
            entries.add(entry);
        }

        WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        wheelView.setWheelRadius(displayMetrics.widthPixels / 2);
        wheelView.setWheelItemRadius(displayMetrics.widthPixels / 6);
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                        break;
                    case 1:
                        ZGallery.with(HomeActivity.this, getImageList())
                                .setToolbarColorResId(R.color.navigationBar)
                                .setTitle("Gallery")
                                .setGalleryBackgroundColor(ZColor.BLACK)
                                .setToolbarTitleColor(ZColor.WHITE)
                                .show();
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, HospitalityActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(HomeActivity.this, EventsActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
                        break;
                }
            }
        });
        wheelView.setAdapter(new WheelAdapter(entries));
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

    private ArrayList<String> getImageList() {
        ArrayList<String> imagesList = new ArrayList<>();
        for (int i = 1; i <= 23; i++)
            imagesList.add("http://ssninstincts.org.in/img/gallery/big/" + i + ".jpg");
        return imagesList;
    }

    static class WheelAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        public WheelAdapter(List<Map.Entry<String, Integer>> items) {
            super(items);
        }

        @Override
        public Drawable getDrawable(int position) {
            Drawable[] drawable = new Drawable[]{createOvalDrawable()};
            return new LayerDrawable(drawable);
        }

        private Drawable createOvalDrawable() {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(0x0);
            return shapeDrawable;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
