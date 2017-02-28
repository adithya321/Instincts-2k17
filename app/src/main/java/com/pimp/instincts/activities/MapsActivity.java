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

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pimp.instincts.R;
import com.pimp.instincts.utils.LogHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = LogHelper.makeLogTag(MapsActivity.class);
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private boolean locationEnabled = false;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    private Map<String, LatLng> stringToLatLngMap;
    private String location;
    private LatLngBounds SSN_COLLEGE = new LatLngBounds(new LatLng(12.748690, 80.188149),
            new LatLng(12.755693, 80.205421));
    private LatLng MAIN_AUDITORIUM = new LatLng(12.753018, 80.200039);
    private LatLng MAIN_STAGE = new LatLng(12.752338, 80.194252);
    private LatLng MINI_AUDITORIUM = new LatLng(12.750640, 80.193585);
    private LatLng ECE_SEMINAR_HALL = new LatLng(12.750743, 80.196205);
    private LatLng CENTRAL_SEMINAR_HALL = new LatLng(12.750409, 80.196144);
    private LatLng CSE_SEMINAR_HALL = new LatLng(12.751133, 80.197286);
    private LatLng IT_SEMINAR_HALL = new LatLng(12.751133, 80.196877);
    private LatLng IT_CLASSROOMS = new LatLng(12.751448, 80.196876);
    private LatLng CSE_CLASSROOMS = new LatLng(12.751438, 80.197301);
    private LatLng IT_LABS = new LatLng(12.751621, 80.196869);
    private LatLng FOUNTAIN = new LatLng(12.751590, 80.195825);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        location = getIntent().getStringExtra("location");

        stringToLatLngMap = new HashMap<>();
        stringToLatLngMap.put("Main Auditorium", MAIN_AUDITORIUM);
        stringToLatLngMap.put("Main Stage", MAIN_STAGE);
        stringToLatLngMap.put("Mini Auditorium", MINI_AUDITORIUM);
        stringToLatLngMap.put("ECE Seminar Hall", ECE_SEMINAR_HALL);
        stringToLatLngMap.put("Central Seminar Hall", CENTRAL_SEMINAR_HALL);
        stringToLatLngMap.put("CSE Seminar Hall", CSE_SEMINAR_HALL);
        stringToLatLngMap.put("IT Seminar Hall", IT_SEMINAR_HALL);
        stringToLatLngMap.put("IT Classrooms", IT_CLASSROOMS);
        stringToLatLngMap.put("CSE Classrooms", CSE_CLASSROOMS);
        stringToLatLngMap.put("IT Labs", IT_LABS);
        stringToLatLngMap.put("Fountain", FOUNTAIN);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        attemptEnableMyLocation();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMyLocationEnabled(locationEnabled);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        googleMap.setMinZoomPreference(15);
        googleMap.setMaxZoomPreference(20);

        googleMap.setLatLngBoundsForCameraTarget(SSN_COLLEGE);

        Set<Map.Entry<String, LatLng>> entries = stringToLatLngMap.entrySet();
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LatLng> entry = (Map.Entry) iterator.next();
            googleMap.addMarker(new MarkerOptions()
                    .position(entry.getValue()).title(entry.getKey()));
        }

        centerOnLocation();
    }

    private void centerOnLocation() {
        moveCameraToPosition(stringToLatLngMap.get(location));
    }

    private void moveCameraToPosition(LatLng position) {
        if (position == null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SSN_COLLEGE.getCenter(), 17));
        else {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(location));
            marker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(position).zoom(18).build()));
        }
    }

    public void attemptEnableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, PERMISSIONS[0]) ==
                PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                locationEnabled = true;
                return;
            }
        }

        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode != REQUEST_LOCATION_PERMISSION) {
            return;
        }

        if (permissions.length == PERMISSIONS.length &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                locationEnabled = true;
            }
        } else {
            Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_LONG).show();

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
