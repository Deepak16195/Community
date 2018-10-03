package com.rdavepatient.soft.Community.Acitvity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.Community.Models.Commentrepone;
import com.rdavepatient.soft.Community.Models.OTPverify;
import com.rdavepatient.soft.Community.Models.OtpData;
import com.rdavepatient.soft.Community.R;
import com.rdavepatient.soft.Community.Utile.Utiles;
import com.rdavepatient.soft.Community.Utility.Utils;
import com.rdavepatient.soft.Community.remote.APIService;
import com.rdavepatient.soft.Community.remote.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Forget_Password extends AppCompatActivity implements View.OnClickListener {

    protected EditText editMobilePassword;
    protected TextView Confirm,Changepassword;
    EditText first, Second, Third, Fourth;
    TextView resendotp;
    String OTPChack = "1234", InputOTP;
    AlertDialog dialog;
    public static final String MY_PREFS_NAME = "Community";
    int UserID;
    APIService mApiService;
    EditText edit_new_Password,edit_Re_Password;
    String sNewPassword,sConfirmPassword;
    String mnobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forget__password);
        initView();
        SharedPreferences preferences = this.getApplication().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        UserID = preferences.getInt("UserID", 0);
        mApiService = ApiUtils.getAPIService();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Confirm) {


            if (editMobilePassword.getText().length() == 0 || editMobilePassword.getText().length() <= 9) {
                editMobilePassword.setError(Html.fromHtml("<font color='White' >Enter 10 digit Mobile No</font>"));
                editMobilePassword.requestFocus();
                return;
            }

             mnobileno = editMobilePassword.getText().toString();
            ForgetPassword(mnobileno);


        }
    }

    public void OtpDialog() {
        final Dialog dialog = new Dialog(Forget_Password.this);
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
                // sendotptouser(mo);
                Utiles.custoAlert(Forget_Password.this, "OTP resended");
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
                if (OTPChack.equals(InputOTP.toString())) {

                    dialog.dismiss();

                    String mobilena = mnobileno;
                    String otps = InputOTP.toString();
                    varifyOTPdata(mobilena, otps);



                } else {
                    //dialog.dismiss();
                    //Utiles.custoAlert(RegisterActivityTwo.this, "OTP Not Verified Re-enter Again");
                    Toast.makeText(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplication(),OTPChack+"",Toast.LENGTH_LONG).show();
                    first.setText("");
                    Second.setText("");
                    Third.setText("");
                    Fourth.setText("");
                    first.requestFocus();
                    dialog.show();

                }
            }
        });
    }


    private void varifyOTPdata(String mobile,String OTP) {
        mApiService.VerifyOtp(mobile,OTP).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

                            UserID = response.getModel().getUserid();
                            AlterdilogCreation();


                        }
                    }
                });
    }


    private void initView() {
        editMobilePassword = (EditText) findViewById(R.id.edit_Mobile_Password);
        Confirm = (TextView) findViewById(R.id.Confirm);
        Confirm.setOnClickListener(Forget_Password.this);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void AlterdilogCreation() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.updatepassword, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(Forget_Password.this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        dialog = alert.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setLayout(600, 500);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;
        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);
        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 1.01f);
        // Set alert dialog height equal to screen height 70%
        //int dialogWindowHeight = (int) (displayHeight * 0.5f);
        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        //  layoutParams.height = dialogWindowHeight;
        // Apply the newly created layout parameters to the alert dialog window

        dialog.getWindow().setAttributes(layoutParams);
        edit_new_Password = alertLayout.findViewById(R.id.edit_new_Password);
        edit_Re_Password = alertLayout.findViewById(R.id.edit_Re_Password);
        Changepassword= alertLayout.findViewById(R.id.Changepassword);

        Changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmPassword()){
                    String New =edit_Re_Password.getText().toString().trim();
                    if(UserID == 0 )
                    {
                        Utils.customMessage(Forget_Password.this, "Can't Update");
                    }else
                    {
                        ChangePasswords(UserID,New);
                    }

                }
                else {
                    Utils.customMessage(Forget_Password.this, "Password doesn't match!");
                }

            }
        });



    }


    private void ChangePasswords(int U_id,String NewPassword) {
        mApiService.ChangePassword(U_id,NewPassword,"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Commentrepone>() {
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
                    public void onNext(Commentrepone response) {
                        if (response.getStatus() == 101) {
                            Toast.makeText(getApplication(), "OTP Not Verified Re-enter Again", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            Forget_Password.this.finish();
                            Toast.makeText(getApplication(), "Password Updated", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private boolean confirmPassword() {
        if (!isEmpty(edit_new_Password)) {
            sNewPassword = edit_new_Password.getText().toString().trim();
            sConfirmPassword = edit_Re_Password.getText().toString().trim();
            if (sNewPassword.equals(sConfirmPassword)) {
                return true;
            }

        }
        return false;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;

    }



    private void ForgetPassword(String Mobile) {
        mApiService.ForgetPassword(Mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OtpData>() {
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
                    public void onNext(OtpData response) {
                        if (response.getStatus() == 101) {
                            Toast.makeText(getApplication(), "Number Not Exists", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            editMobilePassword.setText("");
                            OTPChack = response.getOtp() + "";
                           OtpDialog();
                        }
                    }
                });
    }

}
