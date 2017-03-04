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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;
import com.pimp.instincts.R;
import com.pimp.instincts.model.LocalJSONSource;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.wheelview)
    WheelView wheelView;
    @BindView(R.id.spin_arrow)
    ImageView spinArrow;
    @BindView(R.id.cloud1)
    ImageView cloud1;
    @BindView(R.id.cloud2)
    ImageView cloud2;
    @BindView(R.id.cloud3)
    ImageView cloud3;
    @BindView(R.id.cloud4)
    ImageView cloud4;
    @BindView(R.id.cloud5)
    ImageView cloud5;
    @BindView(R.id.cloud6)
    ImageView cloud6;

    private FirebaseUser firebaseUser;

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
        ButterKnife.bind(this);

        new LocalJSONSource(this);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        logo.bringToFront();

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("", 0x0);
            entries.add(entry);
        }

        wheelView.setWheelRadius(displayMetrics.widthPixels / 2);
        wheelView.setWheelItemRadius(displayMetrics.widthPixels / 6);
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(HomeActivity.this, SponsorsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, HospitalityActivity.class));
                        break;
                    case 3:
                        if (firebaseUser == null)
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        else
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
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

        moveClouds();

        spinArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelView.setSelected(ThreadLocalRandom.current().nextInt(0, 5 + 1));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) wheelView.setWheelDrawable(R.drawable.spin_wheel_register);
        else wheelView.setWheelDrawable(R.drawable.spin_wheel_profile);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void logoOnClick(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    private void moveClouds() {
        Animation translateAnimation10 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_1_0);
        Animation translateAnimation11 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_1_1);
        Animation translateAnimation12 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_1_2);
        Animation translateAnimation13 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_1_3);
        Animation translateAnimation2 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_2);
        Animation translateAnimation3 = AnimationUtils.loadAnimation(this, R.anim.translate_cloud_3);
        cloud1.setAnimation(translateAnimation10);
        cloud2.setAnimation(translateAnimation11);
        cloud3.setAnimation(translateAnimation12);
        cloud4.setAnimation(translateAnimation13);
        cloud5.setAnimation(translateAnimation2);
        cloud6.setAnimation(translateAnimation3);
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
}
