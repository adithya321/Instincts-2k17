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
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pimp.instincts.R;
import com.pimp.instincts.ui.detail.Card;
import com.pimp.instincts.ui.detail.Category;
import com.pimp.instincts.ui.detail.Contact;
import com.pimp.instincts.ui.detail.Line;
import com.pimp.instincts.utils.LogHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.drakeet.multitype.Items;

public class EventDetailActivity extends EventDetailBaseActivity {
    private static final String TAG = LogHelper.makeLogTag(EventDetailActivity.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateHeader(ImageView icon) {
        switch (event.getType()) {
            case "null":
                icon.setImageResource(R.drawable.ic_stage);
                break;
            case "ELC":
                icon.setImageResource(R.drawable.ic_costume);
                break;
            case "Saaral":
                icon.setImageResource(R.drawable.ic_writer);
                break;
            case "Music":
                icon.setImageResource(R.drawable.ic_microphone);
                break;
            case "Variety":
                icon.setImageResource(R.drawable.ic_mask);
                break;
            case "Dance":
                icon.setImageResource(R.drawable.ic_spotlights);
                break;
            case "Quiz":
                icon.setImageResource(R.drawable.ic_walk_of_fame);
                break;

            default:
                icon.setImageResource(R.drawable.ic_stage);
        }
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        if (!event.getDescription().equals("")) {
            items.add(new Card(event.getDescription(), null));
            items.add(new Line());
        }
        if (!event.getLocation().equals("")) {
            items.add(new Category("Location"));

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy HH:mm");
            SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
            try {
                Date startTime = sdf1.parse(event.getStartTime());
                Date endTime = sdf1.parse(event.getEndTime());
                items.add(new Contact(R.drawable.ic_map, event.getLocation(), sdf2.format(startTime)
                        + " - " + sdf3.format(endTime)));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            items.add(new Line());
        }
        if (!event.getRules().equals("")) {
            items.add(new Category("Rules"));
            items.add(new Card(event.getRules(), null));
            items.add(new Line());
        }
        if (!event.getInfo().equals("")) {
            items.add(new Category("Info"));
            items.add(new Card(event.getInfo(), null));
            items.add(new Line());
        }
        if (!event.getContact1().equals("")) {
            items.add(new Category("Contact"));
            items.add(new Contact(R.drawable.ic_call, event.getContact1().split(":")[0],
                    event.getContact1().split(":")[1]));
        }
        if (!event.getContact2().equals("")) {
            items.add(new Category("Contact"));
            items.add(new Contact(R.drawable.ic_call, event.getContact2().split(":")[0],
                    event.getContact2().split(":")[1]));
        }
    }

    @Nullable
    @Override
    protected CharSequence onCreateTitle() {
        toolbar.setTitle(event.getName());
        return super.onCreateTitle();
    }

    @Override
    protected void onActionClick(View action) {
        super.onActionClick(action);
        CardView cardView = (CardView) action;
        RelativeLayout relativeLayout = (RelativeLayout) cardView.getChildAt(0);
        ImageView imageView = (ImageView) relativeLayout.getChildAt(0);
        switch ((int) imageView.getTag()) {
            case R.drawable.ic_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                        event.getContact1().split(":")[1]));
                startActivity(intent);
                break;

            case R.drawable.ic_map:
                startActivity(new Intent(EventDetailActivity.this, MapsActivity.class)
                        .putExtra("location", event.getLocation()));
                break;
        }
    }
}
