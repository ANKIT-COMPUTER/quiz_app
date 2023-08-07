package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Spinner category = findViewById(R.id.Category);
        String[] items = new String[]{"Any Category", "General Knowledge", "Entertainment: Books","Entertainment:  Film","Entertainment: Music","Entertainment: Musicals &amp; Theatres","Entertainment: Television","Entertainment: Video Games","Entertainment: Board Games","Science &amp; Nature","Science: Computers","Science: Mathematics","Mythology","Sports","Geography","History","Politics","Art","Celebrities","Animals","Vehicles","Entertainment: Comics","Science: Gadgets","Entertainment: Japanese Anime &amp; Manga","Entertainment: Cartoon &amp; Animations"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        Spinner dflevel = findViewById(R.id.dflevel);
        String[] levelItem = new String[]{"Any Difficulty","Easy","Medium","Hard"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levelItem);
        dflevel.setAdapter(adapter1);

        Spinner nofQ = findViewById(R.id.numberOfQuetions);
        String[] nOfQlItem = new String[]{"5","10","15","20","25","30","35","40","45","50"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nOfQlItem);
        nofQ .setAdapter(adapter2);

        Spinner qType = findViewById(R.id.quetionType);
        String[] qtypelItem = new String[]{"Multiple Choice","True/False"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtypelItem );
        qType.setAdapter(adapter3);
        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(qType.getSelectedItem().toString()=="True/False"){
                    Intent i = new Intent(getApplicationContext(),TfActivity.class);
                    i.putExtra("object_category", category.getSelectedItem().toString());
                    i.putExtra("object_nuberOfQuestion",  nofQ.getSelectedItem().toString());
                    i.putExtra("object_Difficulty", dflevel.getSelectedItem().toString());
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(getApplicationContext(),QaActivity.class);
                    i.putExtra("object_category", category.getSelectedItem().toString());
                    i.putExtra("object_nuberOfQuestion", nofQ.getSelectedItem().toString());
                    i.putExtra("object_Difficulty", dflevel.getSelectedItem().toString());
                    startActivity(i);
                }

            }

        });
    }

}