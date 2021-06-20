package com.example.calorieculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class calorieCalculator extends AppCompatActivity {
    private Button btnsnacklist;
    private Button btndinnerlist;
    private Button btnBreakfastList;
    private Button btnLunchList;

    private AdView mAdView;
    String bfResult,lResult,sResult,dResult,finalresult;
    double totalResult;


    //int ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });



        TextView breakfastTextview = findViewById(R.id.breakfastCalories);
        bfResult= String.valueOf(clickBreakfastlist.breakfastCalories);
        breakfastTextview.setText(bfResult);

        TextView lunchTextview = findViewById(R.id.lunchCalories);
        lResult= String.valueOf(clickLunchList.lunchCalories);
        lunchTextview.setText(lResult);
        System.out.println("TEXTBOXOFLUNCH=================="+lResult);

        TextView snackTextview = findViewById(R.id.snackCalories);
        sResult= String.valueOf(clickSnackList.snackCalories);
        snackTextview.setText(sResult);

        TextView dinnerTextview = findViewById(R.id.dinnerCalories);
        dResult= String.valueOf(clickDinnerList.dinnerCalories);
        dinnerTextview.setText(dResult);

        TextView total = findViewById(R.id.resiveResult);
        totalResult=Double.parseDouble(bfResult)+Double.parseDouble(lResult)+Double.parseDouble(sResult)+Double.parseDouble(dResult);
        finalresult=Double.toString(totalResult);
        total.setText(finalresult);



            btnBreakfastList=(Button)findViewById(R.id.btnBreackfast);
            btnBreakfastList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(calorieCalculator.this,BreakfastListview.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });

        btnsnacklist = (Button) findViewById(R.id.clickBtnSnack);
        btnsnacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(calorieCalculator.this,snackList.class);
               startActivity(intent);

            }
        });
        btnLunchList=(Button)findViewById(R.id.btnLunch);
        btnLunchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(calorieCalculator.this,LunchList.class);
                startActivity(intent);
            }
        });
        btndinnerlist = (Button) findViewById(R.id.btnDinner);
        btndinnerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(calorieCalculator.this, dinnerList.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clickBreakfastlist.breakfastCalories=0;
        clickLunchList.lunchCalories=0;
    }

}