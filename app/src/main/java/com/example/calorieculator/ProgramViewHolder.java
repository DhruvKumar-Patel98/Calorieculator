package com.example.calorieculator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    ImageView itemImage;
    TextView itemName;
    TextView quantity;
    TextView calories;
    ProgramViewHolder(View v){
        itemImage=v.findViewById(R.id.imageView);
        itemName=v.findViewById(R.id.textView);
        quantity=v.findViewById(R.id.textView2);
        calories=v.findViewById(R.id.textcalories);


    }
}
