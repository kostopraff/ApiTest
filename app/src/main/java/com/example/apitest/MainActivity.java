package com.example.apitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        tv = findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDownload md = new MyDownload();
                md.execute();
            }
        });
    }
    private class MyDownload extends AsyncTask<Void,Void,String>{
        HttpURLConnection httpurl;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://api.weatherstack.com/current?access_key=9b4f673dd175c3d52e7af34c288f4e92&query=Dubai");
                httpurl = (HttpURLConnection) url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();

                InputStream inputStream = httpurl.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()){
                    builder.append(scanner.nextLine());
                }
                return builder.toString();

            } catch (IOException e) {
                Log.e("errrrr",e.toString());
                //e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson g = new Gson();
            Weather weather = g.fromJson(s,Weather.class);
            int temp = weather.getCurrent().getTemperature();
            tv.setText(Integer.toString(temp));
        }
    }
}
