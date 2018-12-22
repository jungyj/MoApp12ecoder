package com.app.project.recordingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Help extends AppCompatActivity implements View.OnClickListener {

    ImageView bnt_Back;
    private Button bnt_Agreement;
    private  Button bnt_Version;
    private Button bnt_developers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        bnt_Back = (ImageView)findViewById(R.id.bntBack);
        bnt_Back.setOnClickListener(this);
        bnt_Agreement = (Button)findViewById(R.id.agreement);
        bnt_Agreement.setOnClickListener(this);
        bnt_Version = (Button)findViewById(R.id.version);
        bnt_Version.setOnClickListener(this);
        bnt_developers = (Button)findViewById(R.id.developers);
        bnt_developers.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bntBack:
                onBackPressed();
                break;
            case R.id.agreement:
                Toast.makeText(getApplication(), "이용약관", Toast.LENGTH_SHORT).show();
                break;
            case R.id.version:
                Toast.makeText(getApplication(), "1.0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.developers:
                Toast.makeText(getApplication(), "김우일, 김정환, 이주형, 정영진", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}
