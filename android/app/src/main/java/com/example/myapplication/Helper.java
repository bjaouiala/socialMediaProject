package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    static public Bitmap parseImage(String image){
        byte[] decodeBytes = Base64.decode(image,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.length);
        return bitmap;
    }

    static  public String createNewDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }
}
