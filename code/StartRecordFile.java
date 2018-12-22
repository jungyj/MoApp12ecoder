package com.app.project.recordingapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.project.recordingapp.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class StartRecordFile extends AppCompatActivity implements View.OnClickListener {


    Intent intent;
    TextView FileNameText;
    TextView TotalTime;
    TextView playTime;
    RecordingItem mItem;
    MediaPlayer recordingFile;
    Context c;
    Button btn_play;

    ImageView btn_Back;

    private TimerTask second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_record_file);
        FileNameText = (TextView)findViewById(R.id.sr_fileName);
        TotalTime = (TextView)findViewById(R.id.sr_totalTime);
        playTime = (TextView)findViewById(R.id.sr_Timer);
        btn_play = (Button)findViewById(R.id.btn_play);

        intent = getIntent();
        mItem = (RecordingItem)intent.getSerializableExtra("filedata");
        FileNameText.setText(mItem.getName());
        TotalTime.setText(mItem.getTime());

        btn_Back = (ImageView) findViewById(R.id.bntBack);
        btn_Back.setOnClickListener((View.OnClickListener) this);

    }

    public void playStart(View view)
    {
        c = getApplicationContext();
        int resID;
        try
        {
            resID = getResources().getIdentifier(mItem.getName(), "raw", getPackageName());
            if (recordingFile == null) {
                recordingFile = MediaPlayer.create(c, resID);
                recordingFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        btn_play.setBackgroundResource(R.drawable.btn_play01);
                    }
                });
            }
        }catch (Exception e)
        {
            Log.i("record","fale");
            newRecordFile();
            recordingFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    btn_play.setBackgroundResource(R.drawable.btn_play01);
                }
            });
        }



            recordingFile.setLooping(false);

            second = new TimerTask() {
                @Override
                public void run() {
                    timerStyle();
                }
            };
            Timer timer = new Timer();
            timer.schedule(second,0,1000);

        if(recordingFile.isPlaying())
        {
            recordingFile.pause();
            btn_play.setBackgroundResource(R.drawable.btn_play01);
        }
        else
        {
            recordingFile.start();
            btn_play.setBackgroundResource(R.drawable.btn_ststop01);
        }

    }

    private void timerStyle()
    {
        int sec;
        int min;
        int hour;
        int subTime;

        String TimerText;

        int time = recordingFile.getCurrentPosition() / 1000 +1;

        sec = time%60;
        subTime = time/60;
        min = subTime%60;
        hour = subTime/60;
        TimerText = String.format("%02d:%02d:%02d",hour,min,sec);

        if(mItem.getTimeInt()>=time)
             playTime.setText(TimerText);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bntBack:
                recordingFile.stop();
                onBackPressed();
                break;

        }

    }

    private void newRecordFile()
    {
        File dir = new File(Environment.getExternalStorageDirectory(),"sounds");
        String num = mItem.getName().substring(mItem.getName().length()-1);
        File soundFile = new File(dir+num+".amr");

        if (recordingFile != null) {
            recordingFile.stop();
            recordingFile.release();
            recordingFile = null;
        }

        recordingFile = new MediaPlayer();

        try {
            recordingFile.setDataSource(soundFile.getAbsolutePath());

            recordingFile.prepare();
    }
        catch (Exception e){Log.i("test","load fale");}
    }
}
