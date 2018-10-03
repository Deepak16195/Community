package com.rdavepatient.soft.Community;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.Community.Acitvity.Edit_Profile;
import com.rdavepatient.soft.Community.Models.OTPverify;
import com.rdavepatient.soft.Community.Models.Registers;
import com.rdavepatient.soft.Community.Utile.Utiles;
import com.rdavepatient.soft.Community.remote.APIService;
import com.rdavepatient.soft.Community.remote.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Registration_Page extends AppCompatActivity {

    private FloatingActionButton fab;
    private CardView cvAdd;
    APIService mApiService;
    ProgressDialog pDialog;
    Dialog dialog;
    Registers.UserEntity Registerdata;
    EditText et_username, et_Email, et_Mobile, et_tpassword;
    Button bt_go;
    int Userid;
    String Mobileno;
    String OTPChack = "1234", InputOTP;
    EditText first, Second, Third, Fourth;
    TextView resendotp;
    public static final String MY_PREFS_NAME = "Community";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_two);
        ShowEnterAnimation();
        mApiService = ApiUtils.getAPIService();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OtpDialog();

                if (et_username.getText().length() == 0) {
                    et_username.setError(Html.fromHtml("<font color='White' >Enter First Name</font>"));
                    et_username.requestFocus();
                    return;
                }

                if (Utiles.isValidEmail(et_Email.getText().toString())) {
                } else {
                    et_Email.setError(Html.fromHtml("<font color='White' >Invalid Email Addresss</font>"));
                    return;
                }
                if (et_Mobile.getText().length() == 0) {
                    et_Mobile.setError(Html.fromHtml("<font color='White' >Enter Mobile Number</font>"));
                    et_Mobile.requestFocus();
                    return;
                }

                if (et_Mobile.getText().length() < 10) {
                    et_Mobile.setError(Html.fromHtml("<font color='White' >Invalid Mobile Number</font>"));
                    et_Mobile.requestFocus();
                    return;
                }

                if (et_tpassword.getText().toString().length() < 8) {
                    et_tpassword.setError(Html.fromHtml("<font color='White' >Password must be atleast 8 Char</font>"));
                    et_tpassword.requestFocus();
                    return;
                }
                Registerdata = new Registers.UserEntity();
                Registerdata.setUserName(et_username.getText().toString());
                Registerdata.setEmailId(et_Email.getText().toString());
                Registerdata.setUserid(0);
                Registerdata.setMobileNo(et_Mobile.getText().toString());
                Registerdata.setPassword(et_tpassword.getText().toString());
                User_Registestion(Registerdata);

            }
        });

    }


    private void User_Registestion(Registers.UserEntity data) {
        mApiService.User_register(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utiles.customMessage(Registration_Page.this, e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Registers data) {
                        if (data.getStatus() == 101) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.bt_go), "Email Id Already Exist", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.bt_go), "Mobile No Already Exist", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }

                        if (data.getStatus() == 200) {
                            Mobileno = et_Mobile.getText().toString();
                            //sendotptouser(Mobileno);
                            Userid = data.getUser().getUserid();
                            OtpDialog();
                        }
                    }
                });


    }


    public void OtpDialog() {
        dialog = new Dialog(Registration_Page.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
        first = (EditText) dialog.findViewById(R.id.first);
        Second = (EditText) dialog.findViewById(R.id.Second);
        Third = (EditText) dialog.findViewById(R.id.Third);
        Fourth = (EditText) dialog.findViewById(R.id.Fourth);
        resendotp = (TextView) dialog.findViewById(R.id.resendotp);

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendotptouser(mo);
                Utiles.custoAlert(Registration_Page.this, "OTP Resended");
            }
        });


        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Second.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Third.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Fourth.requestFocus();
            }
        });


        Fourth.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (first.getText().toString().isEmpty()) {
                    first.setFocusable(true);
                    return;
                }
                if (Second.getText().toString().isEmpty()) {
                    Second.setFocusable(true);
                    return;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Third.getText().toString().isEmpty()) {
                    Third.setFocusable(true);
                    return;
                }

                InputOTP = first.getText().toString() + Second.getText().toString() + Third.getText().toString() + Fourth.getText().toString();

                String mobilena = et_Mobile.getText().toString();
                String otps = InputOTP.toString();
                varifyOTPdata(mobilena, otps);

            }
        });
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        et_username = findViewById(R.id.et_username);
        et_Email = findViewById(R.id.et_Email);
        et_Mobile = findViewById(R.id.et_Mobile);
        et_tpassword = findViewById(R.id.et_tpassword);
        bt_go = findViewById(R.id.bt_go);

    }

    private void varifyOTPdata(String mobile, String OTP) {
        mApiService.VerifyOtp(mobile, OTP).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OTPverify>() {
                    @Override
                    public void onCompleted() {
                        //categoryAdapter = new CategoryAdapter(Products.this, mCategory, "Products");
                        //recyclerView.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }

                    @Override
                    public void onNext(OTPverify response) {
                        if (response.getStatus() == 101) {
                            Toast.makeText(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_LONG).show();
                            first.setText("");
                            Second.setText("");
                            Third.setText("");
                            Fourth.setText("");
                            first.requestFocus();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            dialog.dismiss();
                            String Username = et_username.getText().toString();
                            String Email = et_Email.getText().toString();
                            String Mobile = et_Mobile.getText().toString();
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Emails", Email.toString());
                            editor.putString("UserName", Username.toString());
                            editor.putString("Mobile", Mobile.toString());
                            editor.putInt("UserID", Userid);
                            editor.apply();
                            Registration_Page.this.finish();
                            Intent mIntent = new Intent(Registration_Page.this, Edit_Profile.class);
                            mIntent.putExtra("mobile", Mobile.toString());
                            mIntent.putExtra("Userid", Userid);
                            mIntent.putExtra("hide", 1);
                            mIntent.putExtra("emails", Email.toString());
                            mIntent.putExtra("UserName", Username.toString());
                            startActivity(mIntent);

                        }
                    }
                });
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
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


    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus_iocn);
                Registration_Page.super.onBackPressed();
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


    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
}
