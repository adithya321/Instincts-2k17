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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.lukedeighton.wheelview.WheelView;
import com.pimp.instincts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pimp.instincts.R.id.wheelview;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(wheelview)
    WheelView wheelView;
    @BindView(R.id.spin_arrow)
    ImageView spinArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        spinArrow.setTranslationX(-2000);
        wheelView.setTranslationX(2000);
        wheelView.setWheelRadius(displayMetrics.widthPixels / 2);
        wheelView.setWheelItemRadius(displayMetrics.widthPixels / 6);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSecondActivity();
            }
        }, 1000);
    }

    private void startSecondActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        Pair.create((View) logo, "logo"),
                        Pair.create((View) wheelView, "wheel"),
                        Pair.create((View) spinArrow, "arrow"));
        startActivity(intent, transitionActivityOptions.toBundle());
    }
}
