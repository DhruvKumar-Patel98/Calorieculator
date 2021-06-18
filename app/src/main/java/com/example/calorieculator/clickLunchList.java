package com.example.calorieculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class clickLunchList extends AppCompatActivity {

    private Button btninfo;
    private Button btnDone;
    public static double lunchCalories=0;
    TextView iteamName;
    ImageView imageView;
    TextView quantity;
    TextView calories;
    EditText editText;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_lunch_list);
        iteamName = findViewById(R.id.iteamName);
        imageView = findViewById(R.id.lunchListimage);
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
                if(value!=null){
                    lunchCalories= lunchCalories+(Integer.parseInt(value)*Double.parseDouble(receiveCalories));
                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("value", String.valueOf(lunchCalories));
                    editor.apply();
                    System.out.println("LUNCHCALORIES===================="+lunchCalories);
                }

                Intent intent = new Intent(clickLunchList.this, calorieCalculator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });

        btnInfo=findViewById(R.id.btn_quantityinfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(clickLunchList.this,info.class);
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