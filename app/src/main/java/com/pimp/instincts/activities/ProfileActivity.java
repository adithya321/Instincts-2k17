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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pimp.instincts.R;
import com.pimp.instincts.model.User;
import com.pimp.instincts.utils.LogHelper;

import net.glxn.qrgen.android.QRCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.auron.library.vcard.VCard;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(ProfileActivity.class);

    @BindView(R.id.qr_image_view)
    ImageView qrImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name_et)
    TextInputEditText nameEt;
    @BindView(R.id.college_et)
    EditText collegeEt;
    @BindView(R.id.department_et)
    EditText departmentEt;
    @BindView(R.id.year_et)
    EditText yearEt;
    @BindView(R.id.mobile_et)
    EditText mobileEt;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        VCard vCard = new VCard();
        vCard.setName(firebaseUser.getUid());
        qrImageView.setImageBitmap(QRCode.from(vCard.buildString()).bitmap());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        try {
            populateProfile();
        } catch (Exception e) {
            LogHelper.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
                break;
            case R.id.action_info:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setMessage("Show this QR Code at the registration desk to complete " +
                                "general registration. You will have to pay the respective amount " +
                                "for each event at its location.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateProfile() {
        nameEt.setText(sharedPreferences.getString("name", ""));
        collegeEt.setText(sharedPreferences.getString("college", ""));
        departmentEt.setText(sharedPreferences.getString("department", ""));
        yearEt.setText(sharedPreferences.getString("year", ""));
        mobileEt.setText(sharedPreferences.getString("mobile", ""));
    }

    public void updateOnClick(View view) {
        try {
            if (yearEt.getText().toString().trim().equals(""))
                yearEt.setText("0");

            User user = new User(currentUser.getUid(),
                    nameEt.getText().toString(),
                    currentUser.getEmail(),
                    collegeEt.getText().toString(),
                    departmentEt.getText().toString(),
                    Integer.parseInt(yearEt.getText().toString()),
                    mobileEt.getText().toString(), false);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", nameEt.getText().toString());
            editor.putString("college", collegeEt.getText().toString());
            editor.putString("department", departmentEt.getText().toString());
            editor.putString("year", yearEt.getText().toString());
            editor.putString("mobile", mobileEt.getText().toString());
            editor.apply();

            Toast.makeText(this, "Updating...", Toast.LENGTH_LONG).show();
            databaseReference.child("users").child(currentUser.getUid()).setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this,
                                        task.getException().getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Updated.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
