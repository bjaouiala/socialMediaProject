package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Helper {
    static public Bitmap parseImage(String image){
      if (image != null){
          byte[] decodeBytes = Base64.decode(image,Base64.DEFAULT);
          Bitmap bitmap = BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.length);
          return bitmap;
      }else {
          return null;
      }

    }

    static  public String createNewDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public static String convertDate(String dat){
        String outputDate=" ";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy MMMM dd", Locale.ENGLISH);

        try {
            Date date = inputFormat.parse(dat);
            outputDate = outputFormat.format(date);
            System.out.println(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }
}
