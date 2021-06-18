package com.example.calorieculator;

import android.content.Context;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] iteamName;
    String[] quantity;
    String[] calories;
    public static int multiply;
    public ProgramAdapter(@NonNull Context context, String[] iteamName,String[] quantity,int[] images,String[] calories) {
        super(context, R.layout.example_item,R.id.textView,iteamName);
        this.context=context;
        this.iteamName=iteamName;
        this.quantity=quantity;
        this.images=images;
        this.calories=calories;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View singleItem=convertView;
        ProgramViewHolder holder=null;
        if(singleItem==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem=layoutInflater.inflate(R.layout.example_item,parent,false);
            holder=new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else {
            holder= (ProgramViewHolder) singleItem.getTag();
        }
        holder.itemName.setText(iteamName[position]);
        holder.quantity.setText(quantity[position]);
        holder.itemImage.setImageResource(images[position]);
        holder.calories.setText(calories[position]);



        return singleItem;
    }
}
