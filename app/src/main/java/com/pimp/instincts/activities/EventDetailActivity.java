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
import android.view.View;
import android.widget.ImageView;

import com.pimp.instincts.R;
import com.pimp.instincts.ui.detail.Card;
import com.pimp.instincts.ui.detail.Category;
import com.pimp.instincts.ui.detail.Contact;
import com.pimp.instincts.ui.detail.Line;

import me.drakeet.multitype.Items;

public class EventDetailActivity extends EventDetailBaseActivity {

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

            default:
                icon.setImageResource(R.drawable.ic_stage);
        }
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category(event.getName()));
        items.add(new Card(event.getDescription(), null));
        items.add(new Line());
        items.add(new Category("Rules"));
        items.add(new Card(event.getRules(), null));
        items.add(new Line());
        items.add(new Category("Contact"));
        items.add(new Contact(R.mipmap.ic_launcher_round, event.getContact1(), event.getContact1()));
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
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(event.getContact1()));
        startActivity(intent);
    }
}
