package com.android.warehouseapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText textInputEmail;
    EditText textInputUsername;
    EditText textInputPassword;
    Button mButtonLogin;
    ArrayList<String> user = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputEmail = findViewById(R.id.email);
        textInputUsername = findViewById(R.id.username);
        textInputPassword = findViewById(R.id.password);
        mButtonLogin = findViewById(R.id.button_login);

        new fetchData2().execute();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String I_user = textInputUsername.getText().toString();
                String I_email = textInputEmail.getText().toString();
                String I_password = textInputPassword.getText().toString();
                String ValidPassword = "ABC123bnm";
                Boolean flag_u = Boolean.FALSE;
                Boolean flag_e = Boolean.FALSE;
                Boolean flag_p = Boolean.FALSE;

                Log.d("DATAm8", "size:" + user.size());

                for (int i=0;i<user.size();i++){
                    if(I_user.equals(user.get(i))){
                        flag_u = Boolean.TRUE;
                        if(I_email.equals(email.get(i))){
                            flag_e = Boolean.TRUE;
                            if(I_password.equals(ValidPassword)){
                                flag_p = Boolean.TRUE;

                            }else{
                                Log.d("DATAm7", "passwrong:" + I_password);
                            }
                        }else{
                            Log.d("DATAm6", "email:" + email.get(i));
                        }
                    }else{
                        Log.d("DATAm5", "user:" + user.get(i));
                    }
                }
                if(flag_u && flag_e && flag_p){
                    Intent LoginIntent = new Intent(MainActivity.this, Page1.class);
                    startActivity(LoginIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid username/email/password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class fetchData2 extends AsyncTask<Void,Void,Void> {
        String data = "";
        String data1 = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                URL url = new URL("https://c84a053f.ngrok.io/api/v1/");
//                URL url = new URL("https://api.myjson.com/bins/slqmw");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                Log.d("DATA_m1", "data:" + data);

                JSONObject JO = new JSONObject(data);
                for (int i = 0; i < JO.length(); i++) {
                    globalClass.setUsers(JO.getString("users"));
                    globalClass.setProduct(JO.getString("product"));
                    globalClass.setStock(JO.getString("stock"));
                    Log.d("DATA_m2", "data:" + JO.get("users"));
                }

                URL url1 = new URL(globalClass.getUsers());
                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                InputStream inputStream1 = httpURLConnection1.getInputStream();

                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                String line1 = "";
                while (line1 != null) {
                    line1 = bufferedReader1.readLine();
                    data1 = data1 + line1;
                }
                Log.d("DATA_m3", "data:" + data1);

                JSONArray JA1 = new JSONArray(data1);
                JSONObject JO1 = null;

                Log.d("DATAm9", "length:" + JA1.length());
                for (int i = 0; i < JA1.length(); i++) {
                    JO1 = (JSONObject) JA1.get(i);
                    user.add(JO1.getString("username"));
                    email.add(JO1.getString("email"));
                    Log.d("DATAm4", "data:" + JO1.getString("username"));
                }
                httpURLConnection.disconnect();
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
        }
    }

}

