package com.example.firstui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.searchButton);
        final String key = "NYC";
        System.out.println("at the top");
        Log.e("top", "at the top");
        TextView textView = findViewById(R.id.searchWindow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                final String key = "NYC";
                String url = "https://api.yelp.com/v3/businesses/search/" + key;
                JSONObject otherKey = new JSONObject();
                try {
                    otherKey.put("Authorization", "m3Sfq0JodxB0SUWWLSwBiG-y_W2fBT7zzgavNLXFsfuBfFKVE6CYX6r6qZoTA_4VVumu4JC1brHIDv9EJouOd4Uy8LQsJw-XoPrmTzaXzFzz66hMR0aRXWGohOjmXXYx");
                } catch(JSONException e) {
                    Log.e("exception",e.getMessage());

                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, otherKey, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("just begin");
                        String raw = response.toString();
                        JsonParser parser = new JsonParser();
                        //root
                        JsonObject root = parser.parse(raw).getAsJsonObject();
                        JsonArray business = root.get("businesses").getAsJsonArray();
                        int count = 0;
                        System.out.println("after getting");
                        for (JsonElement a : business) {
                            Boolean i = a.getAsJsonObject().get("is_closed").getAsBoolean();
                            if (!i) {
                                String id = a.getAsJsonObject().get("id").getAsString();
                                MainActivity2.responseFromApi.add(id);
                                count++;
                                if (count == 4) {
                                    break;
                                }
                            }
                        }
                        System.out.println("after arraylistadding");
                        //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        //startActivity(intent);
                        System.out.println("have I start the activity?");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        error.printStackTrace();
                        Log.e("bottom", "at the bottom");
                    }
                });
                queue.add(jsonObjectRequest);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}
