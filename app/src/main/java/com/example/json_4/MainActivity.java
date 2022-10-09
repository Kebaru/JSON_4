package com.example.json_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> personNames=new ArrayList<>();
    ArrayList<String> emailIds=new ArrayList<>();
    ArrayList<String> mobilenumbers=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        try {
            JSONObject obj=new JSONObject(load());

            JSONArray userArray=obj.getJSONArray("users");

            for (int i=0;i<userArray.length();i++){
                JSONObject userDetail=userArray.getJSONObject(i);
                personNames.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));

                JSONObject contact=userDetail.getJSONObject("contact");
                mobilenumbers.add(contact.getString("mobile"));
            }
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        CustomAdapter customAdapter=new CustomAdapter( personNames,emailIds,mobilenumbers,this);
        recyclerView.setAdapter(customAdapter);
    }

    private String load(){
        String json = null;

        try {
            InputStream is = getAssets().open("new_users.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}