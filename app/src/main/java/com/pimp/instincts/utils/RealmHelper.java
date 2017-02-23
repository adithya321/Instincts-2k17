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

package com.pimp.instincts.utils;

import android.content.Context;

import com.pimp.instincts.model.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    private static RealmConfiguration realmConfiguration;
    private Context context;
    private Realm realm;

    public RealmHelper(Context context) {
        this.context = context;

        this.realm = getNewRealmInstance();
    }

    public Realm getNewRealmInstance() {
        if (realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration.Builder()
                    .schemaVersion(1)
                    .migration(new Migration())
                    .build();
        }
        return Realm.getInstance(realmConfiguration);
    }

    public Realm getRealmInstance() {
        return realm;
    }
}
