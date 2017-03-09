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

package com.pimp.instincts.model;

import android.content.Context;

import com.pimp.instincts.InstinctsApplication;
import com.pimp.instincts.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.realm.Realm;

public class LocalJSONSource {
    private static final String TAG = LogHelper.makeLogTag(LocalJSONSource.class);
    private Context context;

    public LocalJSONSource(Context context) {
        this.context = context;
        fetchJSONFromAssets();
    }

    private void fetchJSONFromAssets() {
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = context.getAssets().open("events.json");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray eventsJsonArray = jsonObject.getJSONArray("events");

            InstinctsApplication instinctsApplication = (InstinctsApplication) context.getApplicationContext();
            Realm realm = instinctsApplication.getRealmHelper().getRealmInstance();
            realm.beginTransaction();
            try {
                realm.createOrUpdateAllFromJson(Event.class, eventsJsonArray);
                realm.commitTransaction();
                LogHelper.e(TAG, "TRY");
            } catch (Exception e) {
                LogHelper.e(TAG, "IO : ", e);
                realm.cancelTransaction();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (JSONException e) {
            LogHelper.e(TAG, e.toString());
        } catch (Exception e) {
            LogHelper.e(TAG, e.toString());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
