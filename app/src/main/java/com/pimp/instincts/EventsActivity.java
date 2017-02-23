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

package com.pimp.instincts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.pimp.instincts.about.Card;
import com.pimp.instincts.about.Category;
import com.pimp.instincts.about.Contact;
import com.pimp.instincts.about.EventDetailActivity;
import com.pimp.instincts.about.Line;

import me.drakeet.multitype.Items;

public class EventsActivity extends EventDetailActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateHeader(ImageView icon) {
        icon.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("Online Quiz"));
        items.add(new Card("Sit at home and earn money every hour! No, we are not a fake ad that " +
                "skipped past your adblock. The Online Quiz for Instincts 2017 will give you a " +
                "chance to show off your knowledge of everything under the sun! " +
                "Follow the facebook page https://www.facebook.com/instincts.ssn/ " +
                "and get ready for quizzing action in the evenings leading up to Instincts!", null));
        items.add(new Line());
        items.add(new Category("Rules"));
        items.add(new Card("1. Need a facebook account. (Quick, get Zuckerberg to sponsor Instincts!)\n" +
                "2. Rules will be posted on facebook.", null));
        items.add(new Line());
        items.add(new Category("Contact"));
        items.add(new Contact(R.mipmap.ic_launcher_round, "Sreenivas", "9789813312"));
        items.add(new Contact(R.mipmap.ic_launcher_round, "Saketh", "8056026523"));
    }

    @Nullable
    @Override
    protected CharSequence onCreateTitle() {
        toolbar.setTitle("Online Quiz");
        return super.onCreateTitle();
    }

    @Override
    protected void onActionClick(View action) {
        super.onActionClick(action);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9789813312"));
        startActivity(intent);
    }
}
