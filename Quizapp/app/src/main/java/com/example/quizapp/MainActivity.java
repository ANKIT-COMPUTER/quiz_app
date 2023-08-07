package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog_1 = new Dialog(MainActivity.this);
        dialog_1.setContentView(R.layout.layout);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        Button wifi =(Button) dialog_1.findViewById(R.id.btn_wifi_on);
        Button data =(Button) dialog_1.findViewById(R.id.btn_mobile_data_on);


        if(!connected){
            dialog_1.show();
        }
        else{
            try {
                Thread.sleep(6000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        wifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

            }

        });

        data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);

            }

        });
        Intent i = new Intent(getApplicationContext(),CategoryActivity.class);
        startActivity(i);
    }
}