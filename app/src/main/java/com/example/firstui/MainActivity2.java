package com.example.firstui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<String> ratingList = new ArrayList<>();
    public static ArrayList<Boolean> isClosedList = new ArrayList<>();
    public static ArrayList<String> phoneList = new ArrayList<>();
    public static ArrayList<Integer> reviewCountList = new ArrayList<>();
    public static ArrayList<String> pictureURLList = new ArrayList<>();
    public static ArrayList<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayout information = findViewById(R.id.information);
        for (int i = 0; i < nameList.size(); i++) {
            String current = "name :" + nameList.get(i) + "\n";
            String current1 = "phone :" + phoneList.get(i) + "\n";
            String current2 = "rate :" + ratingList.get(i) + "\n";
            String current3 = "categories :" + titleList.get(i) + "\n";
            String current4 = pictureURLList.get(i);
            //SHOW(chunk part)
            View chunk = getLayoutInflater().inflate(R.layout.chunk_restaurtant, information, false);
            ImageView picture = chunk.findViewById(R.id.picture);
            //SHOW(transfer string to url; display images using Picasso library)
            //Picasso.with(Context).load(Uri).into(imageView)
            //parse the String into Uri
            Uri uri = Uri.parse(current4);
            Picasso.with(this.getApplicationContext()).load(uri).into(picture);
            picture.setVisibility(View.VISIBLE);
            TextView name = chunk.findViewById(R.id.name);
            name.setText(current);
            //SHOW(if the restaurant is closed, the name's color will be turned into grey)
            if (isClosedList.get(i)) {
                name.setTextColor(Color.parseColor("#A89594"));
            }
            TextView phone = chunk.findViewById(R.id.phone);
            phone.setText(current1);
            TextView rate = chunk.findViewById(R.id.rate);
            rate.setText(current2);
            TextView title = chunk.findViewById(R.id.title);
            title.setText(current3);
            chunk.setVisibility(View.VISIBLE);
            information.addView(chunk);
        }
        //clear all the list for next location
        nameList.clear();
        phoneList.clear();
        ratingList.clear();
        titleList.clear();
        pictureURLList.clear();
        Button button = findViewById(R.id.returnButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /*private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("tag", "error");
        }
        return bm;
    }*/
}

