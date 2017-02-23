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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.pimp.instincts.R;
import com.pimp.instincts.ui.WheelMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        ImageView spinArrowImageView = (ImageView) findViewById(R.id.spin_arrow);
        spinArrowImageView.getLayoutParams().width = displayMetrics.widthPixels / 2;

        WheelMenu wheelMenu = (WheelMenu) findViewById(R.id.wheelMenu);
        wheelMenu.setTranslationX(displayMetrics.widthPixels / 2);
        wheelMenu.setDivCount(6);
        wheelMenu.setWheelImage(R.drawable.spin_wheel);
        wheelMenu.setSnapToCenterFlag(false);
        wheelMenu.setWheelChangeListener(new WheelMenu.WheelChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                switch (selectedPosition) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, EventDetailActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "ABOUT", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "CONTACT", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, MapsActivity.class)
                                .putExtra("location", ""));
                        break;
                    case 5:
                        ZGallery.with(MainActivity.this, getImageList())
                                .setToolbarColorResId(R.color.colorPrimary)
                                .setTitle("Gallery")
                                .setGalleryBackgroundColor(ZColor.BLACK)
                                .setToolbarTitleColor(ZColor.WHITE)
                                .show();
                        break;
                }
            }
        });
    }

    public void registerOnClick(View view) {
    }

    private ArrayList<String> getImageList() {
        ArrayList<String> imagesList = new ArrayList<>();
        for (int i = 1; i <= 13; i++)
            imagesList.add("http://ssninstincts.org.in/img/gallery/big/" + i + ".jpg");
        return imagesList;
    }
}
