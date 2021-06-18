package com.example.calorieculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
 //   private ImageButton button;


    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // button=(ImageButton) findViewById(R.id.calorie);
        //button.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
            //    openCalorieCalculator();
           // }
       // });

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,calorieCalculator.class);
                startActivity(intent);
                finish();
            }
        },2500);

    }
  //  public void openCalorieCalculator()
   // {
     //   Intent intent=new Intent(this,calorieCalculator.class);
       // startActivity(intent);
    //}
}