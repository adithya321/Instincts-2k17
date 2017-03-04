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

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pimp.instincts.R;
import com.pimp.instincts.utils.LogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(LoginActivity.class);

    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.go_btn)
    Button goBtn;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

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

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        moveClouds();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @OnClick({R.id.go_btn, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                break;
            case R.id.go_btn:
                if (!validate()) return;
                Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
                auth.signInWithEmailAndPassword(emailEt.getText().toString().trim(),
                        passwordEt.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                LogHelper.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this,
                                            task.getException().getMessage().toString(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                    finish();
                                }
                            }
                        });
                break;
        }
    }

    private boolean validate() {
        if (emailEt.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (passwordEt.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
}
