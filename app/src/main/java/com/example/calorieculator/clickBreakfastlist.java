package com.example.calorieculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class clickBreakfastlist extends AppCompatActivity {
    TextView iteamName;
    ImageView imageView;
    TextView quantity;
    TextView calories;
    private Button btnDone;
    private InterstitialAd mInterstitialAd;
    EditText editText;
    public static double breakfastCalories=0;
    Button btnInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_breakfastlist);
        iteamName = findViewById(R.id.iteamName);
        imageView = findViewById(R.id.breakfastListimage);
        quantity=findViewById(R.id.iteamDetail);
        calories=findViewById(R.id.textcalories);
        editText=findViewById(R.id.enterQuantity);
        Intent intent = getIntent();
        String receivedName =  intent.getStringExtra("name");
        int receivedImage = intent.getIntExtra("image",0);
        String receiveQuantity=intent.getStringExtra("quantity");
        String receiveCalories=intent.getStringExtra("calories");
        iteamName.setText(receivedName);
        imageView.setImageResource(receivedImage);
        quantity.setText(receiveQuantity);
        calories.setText(receiveCalories);
        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        btnDone=findViewById(R.id.select_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = editText.getText().toString().trim();
                if(value.matches("")){
                   Toast.makeText(clickBreakfastlist.this,"Please Enter Quantity",Toast.LENGTH_LONG).show();
                }
                else {
                    breakfastCalories= breakfastCalories+(Integer.parseInt(value)*Double.parseDouble(receiveCalories));
                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("value", String.valueOf(breakfastCalories));
                    editor.apply();

                    Intent intent = new Intent(clickBreakfastlist.this, calorieCalculator.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(clickBreakfastlist.this);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }



            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

        btnInfo=findViewById(R.id.btn_quantityinfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(clickBreakfastlist.this,info.class);
                startActivity(intent1);
            }
        });

    }
    //getting back to listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}