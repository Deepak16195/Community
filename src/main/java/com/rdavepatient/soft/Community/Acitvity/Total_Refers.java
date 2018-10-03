package com.rdavepatient.soft.Community.Acitvity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rdavepatient.soft.Community.Adapter.All_Refers_Adapter;
import com.rdavepatient.soft.Community.Models.Refrral;
import com.rdavepatient.soft.Community.R;
import com.rdavepatient.soft.Community.Utile.Utiles;
import com.rdavepatient.soft.Community.remote.APIService;
import com.rdavepatient.soft.Community.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Total_Refers extends AppCompatActivity {


    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    AlertDialog dialog;
    All_Refers_Adapter RecyclerViewHorizontalAdapter;
    public static final String MY_PREFS_NAME = "Community";
    APIService mApiService;
    int UserID;
    RecyclerView RVrefreel;
    List<Refrral.UserEntity> mPostList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total__refers);
        RVrefreel = (RecyclerView) findViewById(R.id.RVrefreel);
        mApiService = ApiUtils.getAPIService();
        SharedPreferences preferences = getApplication().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        UserID = preferences.getInt("UserID", 0);
        GetRefferList(UserID);



    }

    public void initRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplication());
        RVrefreel.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.
        RecyclerViewHorizontalAdapter = new All_Refers_Adapter(getApplication(), mPostList);
        HorizontalLayout = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        RVrefreel.setLayoutManager(HorizontalLayout);
        RVrefreel.setAdapter(RecyclerViewHorizontalAdapter);
    }


    public void back(View view){
        super.onBackPressed();
    }



    private void GetRefferList(int Userid) {   // -> LoginANDCheckUser
        //Utiles.showProgressDialog(getApplicationContext());
        ApiUtils.getAPIService().GetRefrral(Userid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Refrral>() {
                    @Override
                    public void onCompleted() {

                        Utiles.hideProgressDialog();
                        initRecyclerView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utiles.hideProgressDialog();
                        Log.e("retro_error", e.toString());
                    }

                    @Override
                    public void onNext(Refrral response) {


                        if (response.getStatus() == 101) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.RVrefreel), "", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }
                        if (response.getStatus() == 200) {

                            if (response.getReffral().getUser().size() > 0) {
                                mPostList = response.getReffral().getUser();
                            }




                        }
                    }
                });
    }
}
