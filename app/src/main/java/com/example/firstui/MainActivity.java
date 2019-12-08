package com.example.firstui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //youtube link https://youtu.be/ixS8fQj_1NY
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.searchButton);
        final EditText textView = findViewById(R.id.searchWindow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = textView.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                //the URL's format; the api requires query parameters
                String url = "https://api.yelp.com/v3/businesses/search?location=" + location;
                queue.add(new JsonObjectRequest(Request.Method.GET, url, (JSONObject)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String raw = response.toString();
                        JsonParser parser = new JsonParser();
                        JsonObject root = parser.parse(raw).getAsJsonObject();
                        JsonArray business = root.get("businesses").getAsJsonArray();
                        int count = 0;
                        //using JSON to get the information showed in the second activity
                        for (JsonElement a : business) {
                            String name = a.getAsJsonObject().get("name").getAsString();
                            String rating = a.getAsJsonObject().get("rating").getAsString();
                            String phone = a.getAsJsonObject().get("phone").getAsString();
                            boolean closedOrNot = a.getAsJsonObject().get("is_closed").getAsBoolean();
                            int reviewCount = a.getAsJsonObject().get("review_count").getAsInt();
                            String pictureURLList = a.getAsJsonObject().get("image_url").getAsString();
                            JsonArray categories = a.getAsJsonObject().get("categories").getAsJsonArray();
                            MainActivity2.nameList.add(name);
                            MainActivity2.ratingList.add(rating);
                            MainActivity2.phoneList.add(phone);
                            MainActivity2.isClosedList.add(closedOrNot);
                            MainActivity2.reviewCountList.add(reviewCount);
                            MainActivity2.pictureURLList.add(pictureURLList);
                            String category = "";
                            //category
                            for (JsonElement b:categories) {
                                JsonObject current = b.getAsJsonObject();
                                String title = current.get("title").getAsString() + ",";
                                category = category + title;
                            }
                            category = category.substring(0, category.length() - 1);
                            MainActivity2.titleList.add(category);
                            count++;
                            if (count == 100) {
                                break;
                            }
                        }
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        error.printStackTrace();
                    }
                }){
                    @Override
                    //add authorization to get the api usable
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        try {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("Authorization", "Bearer m3Sfq0JodxB0SUWWLSwBiG-y_W2fBT7zzgavNLXFsfuBfFKVE6CYX6r6qZoTA_4VVumu4JC1brHIDv9EJouOd4Uy8LQsJw-XoPrmTzaXzFzz66hMR0aRXWGohOjmXXYx");
                            return map;
                        } catch (Exception e) {
                            System.out.println("test error");
                            Log.e("error", "Authentication Filure" );
                        }
                        return super.getHeaders();
                    }
                });
            }
        });
    }
}
