
package com.app.project.recordingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);

        Intent getIntent = getIntent();
        if(getIntent != null){
            String testDataList = getIntent.getStringExtra("testDataList");
            Log.e("test", "testDataList = " + testDataList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        return true;
    }

    public void GoAllList(View view)
    {
        Intent intent = new Intent(DateList.this,MainActivity.class);
        startActivity(intent);
    }

    public void GoCategoryList(View view)
    {
        Intent intent = new Intent(DateList.this,CategoryList.class);
        startActivity(intent);
    }


}
