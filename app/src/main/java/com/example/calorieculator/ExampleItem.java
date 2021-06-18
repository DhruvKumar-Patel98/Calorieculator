package com.example.calorieculator;

import android.os.Parcel;
import android.os.Parcelable;

public class  ExampleItem implements Parcelable {
    private int imageResource;
    private String text1;
    private String text2;
    private String text3;

    public ExampleItem(int imageResource, String text1, String text2, String text3) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2=text2;
        this.text3=text3;

    }

    protected ExampleItem(Parcel in) {
        imageResource = in.readInt();
        text1 = in.readString();
        text2=in.readString();
        text3=in.readString();

    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    public int getImageResource() {
        return imageResource;
    }
    public String getText1() {
        return text1;
    }
    public String getText2() {
        return text2;
    }
    public String getText3() {
        return text3;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imageResource);
        parcel.writeString(text1);
        parcel.writeString(text2);
        parcel.writeString(text3);

    }
}