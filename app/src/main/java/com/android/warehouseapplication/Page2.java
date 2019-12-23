package com.android.warehouseapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Page2 extends AppCompatActivity {
    ArrayList<String> ID = new ArrayList<>();
    ArrayList<String> Description = new ArrayList<>();
    ArrayList<String> Cost = new ArrayList<>();
    ArrayList<String> Manufacturer = new ArrayList<>();
    ArrayList<String> Brand = new ArrayList<>();

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        new fetchData1().execute();

        backButton = findViewById(R.id.Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(Page2.this, Page1.class);
                startActivity(BackIntent);
            }
        });
    }

    public class fetchData1 extends AsyncTask<Void,Void,Void> {
        String data1 = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                URL url1 = new URL(globalClass.getProduct());
                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                InputStream inputStream1 = httpURLConnection1.getInputStream();

                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                String line1 = "";
                while (line1 != null) {
                    line1 = bufferedReader1.readLine();
                    data1 = data1 + line1;
                }
                Log.d("DATA_p2", "data:" + data1);

                JSONArray JA1 = new JSONArray(data1);
                JSONObject JO1 = null;
                for (int i = 0; i < JA1.length(); i++) {
                    JO1 = (JSONObject) JA1.get(i);

                    ID.add(JO1.getString("product_id"));
                    Description.add(JO1.getString("description"));
                    Brand.add(JO1.getString("brand"));
                    Cost.add(JO1.getString("cost"));
                    Manufacturer.add(JO1.getString("manufacturer"));
                    Log.d("DATA_p2", "data1:" + ID.get(i));
                }
                httpURLConnection1.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView pro1_id = findViewById(R.id.textView3);
            TextView description = findViewById(R.id.textView4);
            TextView brand = findViewById(R.id.textView5);
            TextView cost = findViewById(R.id.textView6);
            TextView manufacturer = findViewById(R.id.textView7);
            ImageView image = findViewById(R.id.imageView3);

            image.setImageResource(R.drawable.samsungphone);

            pro1_id.setText("Product ID: "+ID.get(0));
            description.setText("Product Description: "+Description.get(0));
            brand.setText("Product Brand: "+Brand.get(0));
            cost.setText("Product Cost per Unit (RM): "+Cost.get(0));
            manufacturer.setText("Product Manufacturer: "+Manufacturer.get(0));

        }
    }
}