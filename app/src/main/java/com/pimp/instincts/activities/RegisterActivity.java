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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pimp.instincts.R;
import com.pimp.instincts.model.User;
import com.pimp.instincts.utils.LogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(RegisterActivity.class);

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.college_et)
    EditText collegeEt;
    @BindView(R.id.department_et)
    EditText departmentEt;
    @BindView(R.id.year_et)
    EditText yearEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.go_btn)
    Button goBtn;
    @BindView(R.id.register_cardview)
    CardView registerCardview;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ShowEnterAnimation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
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

        try {
            populateProfile();
        } catch (Exception e) {
            LogHelper.e(TAG, e.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                registerCardview.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(registerCardview,
                registerCardview.getWidth() / 2, 0, fab.getWidth() / 2, registerCardview.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                registerCardview.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(registerCardview,
                registerCardview.getWidth() / 2, 0, registerCardview.getHeight(),
                fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                registerCardview.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    public void goOnClick(View view) {
        if (!validate()) return;
        auth.createUserWithEmailAndPassword(emailEt.getText().toString().trim(),
                passwordEt.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        LogHelper.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    task.getException().getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if (yearEt.getText().toString().trim().equals(""))
                                yearEt.setText("0");
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            User user = new User(currentUser.getUid(),
                                    nameEt.getText().toString(),
                                    emailEt.getText().toString(),
                                    collegeEt.getText().toString(),
                                    departmentEt.getText().toString(),
                                    Integer.parseInt(yearEt.getText().toString()),
                                    mobileEt.getText().toString());

                            databaseReference.child("users").child(currentUser.getUid()).setValue(user);

                            Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
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

    public void populateProfile() {
        nameEt.setText(currentUser.getDisplayName());
        emailEt.setText(currentUser.getEmail());

        Query userQuery = databaseReference.child("users").child(currentUser.getUid());
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    nameEt.setText(user.getName());
                    emailEt.setText(user.getEmail());
                    collegeEt.setText(user.getCollege());
                    departmentEt.setText(user.getDepartment());
                    yearEt.setText(String.valueOf(user.getYear()));
                    mobileEt.setText(user.getMobile());
                } catch (Exception e) {
                    Log.e("populateProfile", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
