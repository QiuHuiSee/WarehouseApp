package com.android.warehouseapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Page1 extends AppCompatActivity {
    Button P_1;
    Button P_2;
    Button P_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        P_1 = findViewById(R.id.P1);
        P_2 = findViewById(R.id.P2);
        P_3 = findViewById(R.id.P3);

        new fetchData().execute();
        P_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Page1.this, Page2.class);
                startActivity(intent1);
            }
        });

        P_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Page1.this, Product2.class);
                startActivity(intent2);
            }
        });

        P_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Page1.this, Product3.class);
                startActivity(intent3);
            }
        });
    }

    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data = "";
        JSONObject dataPass = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                URL url = new URL(globalClass.getStock());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                Log.d("DATA_p1", "data:" + data);

                JSONArray JA = new JSONArray(data);
                JSONObject JO = null;
                for (int i = 0; i < JA.length(); i++) {
                    JO = (JSONObject) JA.get(i);
                    Log.d("DATA_p2", "data:" + JO.getString("product1"));
                }
                httpURLConnection.disconnect();
                dataPass = JO;
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
            TextView pro1_id = findViewById(R.id.pro1_id);
            TextView pro1_q = findViewById(R.id.pro1_quan);
            TextView pro2_id = findViewById(R.id.pro2_id);
            TextView pro2_q = findViewById(R.id.pro2_quan);
            TextView pro3_id = findViewById(R.id.pro3_id);
            TextView pro3_q = findViewById(R.id.pro3_quan);

            try {
                pro1_id.setText(String.valueOf(dataPass.get("product1")));
                pro1_q.setText(String.valueOf(dataPass.get("quantity1")));
                pro2_id.setText(String.valueOf(dataPass.get("product2")));
                pro2_q.setText(String.valueOf(dataPass.get("quantity2")));
                pro3_id.setText(String.valueOf(dataPass.get("product3")));
                pro3_q.setText(String.valueOf(dataPass.get("quantity3")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
