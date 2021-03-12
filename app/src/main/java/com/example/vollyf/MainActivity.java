package com.example.vollyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<info>contacts;
    private static String JSON_URL="https://api.androidhive.info/contacts/";
    RecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        contacts=new ArrayList<>();
        extractcontacts();
    }

    private void extractcontacts() {
        RequestQueue queue = Volley.newRequestQueue(this);

        Response.Listener<JSONObject> jsonResponseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
//                VolleyLog.wtf(res.toString(), "utf-8");
                JSONArray response= null;
                try {
                    response = res.getJSONArray("contacts");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject contactobject= response.getJSONObject(i);
                        info info= new info();
                        info.setName(contactobject.getString("name").toString());
                        info.setEmail(contactobject.getString("email").toString());
                        info.setAddress(contactobject.getString("address").toString());
                        info.setGender(contactobject.getString("gender").toString());
                        contacts.add(info);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new RecAdapter(MainActivity.this,contacts);
                    recyclerView.setAdapter(adapter);
                }
            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL, null, jsonResponseListener, errorListener){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }};;

        queue.add(jsonObjectRequest);
    }
}