package com.rdavepatient.soft.Community;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.Community.Acitvity.Forget_Password;
import com.rdavepatient.soft.Community.Acitvity.MainActivity;
import com.rdavepatient.soft.Community.Models.Registers;
import com.rdavepatient.soft.Community.Utile.Utiles;
import com.rdavepatient.soft.Community.Utility.CheckInternet;
import com.rdavepatient.soft.Community.Utility.Utils;
import com.rdavepatient.soft.Community.remote.APIService;
import com.rdavepatient.soft.Community.remote.ApiUtils;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Login_Page extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    TextView Forg_Password;
    private FloatingActionButton fab;
    APIService mApiService;
    ProgressDialog pDialog;
    SharedPreferences sharedpreferences;
    public static final String MY_PREFS_NAME = "Community";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_one);
        mApiService = ApiUtils.getAPIService();
        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        Forg_Password = findViewById(R.id.Forget_Password);
    }

    private void setListener() {

        Forg_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login_Page.this, Forget_Password.class);
                startActivity(i);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login_Page.this, fab, fab.getTransitionName());
                startActivity(new Intent(Login_Page.this, Registration_Page.class), options.toBundle());
            }
        });
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etUsername.getText().length() == 0) {
                    etUsername.setError(Html.fromHtml("<font color='White' >Enter First Name</font>"));
                    etUsername.requestFocus();
                    return;
                }
                if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError(Html.fromHtml("<font color='White' >Password must be atleast 8 Char</font>"));
                    etPassword.requestFocus();
                    return;
                }

                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                Registers.UserEntity Registerdata = new Registers.UserEntity();
                Registerdata.setEmailId(etUsername.getText().toString());
                Registerdata.setPassword(etPassword.getText().toString());

                if (CheckInternet.isConnectingToInternet(Login_Page.this))
                {
                    CheckLogin(Registerdata);
                }
                else {
                    Utils.customMessage(Login_Page.this, "Please check your Internet connection!");
                }


            }
        });

    }

    private void CheckLogin(Registers.UserEntity Registerdata) {   // -> LoginANDCheckUser
        Utiles.showProgressDialog(Login_Page.this);
        ApiUtils.getAPIService().checkLogin(Registerdata).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Registers>() {
                    @Override
                    public void onCompleted() {
                        Utiles.hideProgressDialog();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utiles.hideProgressDialog();
                        Log.e("retro_error", e.toString());
                    }

                    @Override
                    public void onNext(Registers response) {


                        if (response.getStatus() == 101) {
//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.Forget_Password), "please provide registered email-id or mobile no.", Snackbar.LENGTH_LONG);
//                            snackbar.show();

                            Toasty.error(getApplication(), "Enter Registered Email-id or Mobile No", Toast.LENGTH_SHORT, true).show();
                            return;
                        }


                        if (response.getStatus() == 200) {

                            int userid = response.getUser().getUserid();
                            String UserName = response.getUser().getUserName();
                            String Email = response.getUser().getEmailId();
                            String Mobile = response.getUser().getMobileNo();
                            String Referral = response.getUser().getReferralcode();

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Emails", Email.toString());
                            editor.putString("UserName", UserName.toString());
                            editor.putString("Mobile", Mobile.toString());
                            editor.putString("Referral", Referral.toString());
                            editor.putInt("UserID", userid);
                            editor.apply();
                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(Login_Page.this);
                            Intent i2 = new Intent(Login_Page.this, MainActivity.class);
                            startActivity(i2, oc2.toBundle());
                            Login_Page.this.finish();
//                            Intent mIntent = new Intent(Login_Page.this, MainActivity.class);
//                            startActivity(mIntent);
//                            finishAffinity();

                        }
                    }
                });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }
}
