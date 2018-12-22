package com.app.project.recordingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.progresviews.ProgressWheel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int lcdWidth1;
    private int lcdHeight1;

    private int lcdWidth2;
    private int lcdHeight2;

    private View Mymenu1;

    ProgressWheel pWheel;

    private Context mContext;
    private View view_viewpager_select_bar1;
    private View view_viewpager_select_bar2;
    private CustomViewPager viewPager1;
    private ViewPager viewPager2;
    private Spinner spinner;
    //private ArrayAdapter sAdapter;

    private int fileNum = 0;

    FileListAdapter mFileAdapter = new FileListAdapter();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);

    TextView recadingTimer;
    TimerTask second;
    int time_sec=0;
    boolean flag=true;
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display display = getWindowManager().getDefaultDisplay();
        Point size1 = new Point();
        display.getSize(size1);
        lcdWidth1 = size1.x;
        lcdHeight1 = size1.y;

        mContext = this;

        view_viewpager_select_bar1 = findViewById(R.id.view_viewpager_select_bar);


        viewPager1 = findViewById(R.id.viewPager1);
        viewPager1.setAdapter(mPagerAdapter1);

        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    view_viewpager_select_bar1.setTranslationX(0);
                } else {
                    view_viewpager_select_bar1.setTranslationX(lcdWidth1 / 2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        Mymenu1 = findViewById(R.id.menu1);
        Mymenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                Toast.makeText(getApplication(), "메뉴1", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.m2:
                                Toast.makeText(getApplication(), "메뉴2", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.m3:
                                Toast.makeText(getApplication(), "메뉴3", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();//Popup Menu 보이기
            }


        });
    }

    public void GoHelp(View v)
    {
        Intent intent = new Intent(MainActivity.this,Help.class);
        startActivity(intent);
    }
    public void GoCate(View v){
        Intent intent = new Intent(MainActivity.this,cate.class);
        startActivity(intent);
    }
    public void GoCate2(View v){
        Intent intent = new Intent(MainActivity.this,cate2.class);
        startActivity(intent);
    }

    public void GoAllList() {
        Intent intent = new Intent(MainActivity.this,DateList.class);
        intent.putExtra("testAllList", "testValue");
        startActivity(intent);
    }

    private void GoCategoryList() {
        Intent intent = new Intent(MainActivity.this, CategoryList.class);
        intent.putExtra("testCategory", "testValue1");
        startActivity(intent);
    }

    private void GoDateList() {
        Intent intent = new Intent(MainActivity.this, DateList.class);
        startActivity(intent);
    }

    public void onRecodingAction(View view) {
        // 뷰페이저 레코딩 화면 보이도록 처리
        viewPager1.setCurrentItem(0, true);
    }

    public void onHistoryAction(View view) {
        // 뷰페이저 히스토리 화면 보이도록 처리
        if(viewPager1.isPagingEnabled()){
            viewPager1.setCurrentItem(1, true);
        }
    }

    public void onAllHistoryAction(View view) {
        // 뷰페이저 전체 히스토리 화면 보이도록 처리
        viewPager2.setCurrentItem(0, true);
    }

    public void onDateHistoryAction(View view) {
        // 뷰페이저 Date 히스토리 화면 보이도록 처리
        viewPager2.setCurrentItem(1, true);
    }

    public void onAllCategoryHistoryAction(View view) {
        // 뷰페이저 category 히스토리 화면 보이도록 처리
        viewPager2.setCurrentItem(2, true);
    }


    MediaRecorder mp=null;

    private PagerAdapter mPagerAdapter1 = new PagerAdapter() {



        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            View view = null;

            try {


                if (position == 0) { //tab1
                      view = View.inflate(mContext, R.layout.activity_main_recoding, null);

                      final View start_record = view.findViewById(R.id.lay_startrecoder);//before record
                      final View stop_record = view.findViewById(R.id.lay_recoding); //after record


                      //녹음시작
                      view.findViewById(R.id.start_record).setOnClickListener(new View.OnClickListener(){

                          @Override
                          public void onClick(View v) {
                              flag=true;

                              start_record.setVisibility(View.GONE); //off
                              stop_record.setVisibility(View.VISIBLE);//on

                              spinner = (Spinner)findViewById(R.id.spin1);


                              pWheel = (ProgressWheel) findViewById(R.id.wheelprogress);
                                  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wheel);
                                  pWheel.setAnimation(animation);

                              viewPager1.setPagingEnabled(false);
                              startRecord();
                              time_sec = 0;
                              second = new TimerTask() {
                                  @Override
                                  public void run() {
                                      if(flag==true) {
                                          time_sec++;

                                      }
                                      timerStyle();
                                  }
                              };
                              Timer timer = new Timer();
                              timer.schedule(second,0,1000);

                          }
                      });
                      view.findViewById(R.id.pause_record).setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              cnt++;

                              if (cnt%2==0){
                                  flag=true;
                                  ((Button)findViewById(R.id.pause_record)).setBackgroundResource(R.drawable.btn_ststop01);
                                  ((TextView)findViewById(R.id.textview1)).setText("일시정지");
                              }else{
                                  flag=false;
                                  ((Button)findViewById(R.id.pause_record)).setBackgroundResource(R.drawable.btn_play01);
                                  ((TextView)findViewById(R.id.textview1)).setText("재생");
                              }


                          }
                      });

                      //녹음완료
                    view.findViewById(R.id.stop_record).setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            stopRecord();

                            start_record.setVisibility(View.VISIBLE);
                            stop_record.setVisibility(View.GONE);

                            viewPager1.setPagingEnabled(true);


                        }
                    });

                    // view = View.inflate(mContext,R.layout.start_record,null);


                } else { //tab2
                    view = View.inflate(mContext, R.layout.activity_main_histroy, null);

                    final Display display = getWindowManager().getDefaultDisplay();
                    Point size2 = new Point();
                    display.getSize(size2);
                    lcdWidth2 = size2.x;
                    lcdHeight2 = size2.y;

                    view_viewpager_select_bar2 = view.findViewById(R.id.view_viewpager_select_bar2);

                    viewPager2 = view.findViewById(R.id.viewPager2);
                    viewPager2.setAdapter(mPagerAdapter2);

                    viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {

                            String onColor = "#FF212121";
                            String offColor = "#878787";

                            if (position == 0) {
                                view_viewpager_select_bar2.setTranslationX(0);

                                Button button1 = (Button) findViewById(R.id.allTap);
                                Button button2 = (Button) findViewById(R.id.DayTap);
                                Button button3 = (Button) findViewById(R.id.catTap);

                                button1.setTextColor(Color.parseColor(onColor));
                                button2.setTextColor(Color.parseColor(offColor));
                                button3.setTextColor(Color.parseColor(offColor));

                            }

                            else if (position == 1){
                                view_viewpager_select_bar2.setTranslationX(lcdWidth2 / 3);

                                Button button1 = (Button) findViewById(R.id.allTap);
                                Button button2 = (Button) findViewById(R.id.DayTap);
                                Button button3 = (Button) findViewById(R.id.catTap);

                                button1.setTextColor(Color.parseColor(offColor));
                                button2.setTextColor(Color.parseColor(onColor));
                                button3.setTextColor(Color.parseColor(offColor));

                            }

                            else {
                                view_viewpager_select_bar2.setTranslationX(lcdWidth2 * 2 / 3);

                                Button button1 = (Button) findViewById(R.id.allTap);
                                Button button2 = (Button) findViewById(R.id.DayTap);
                                Button button3 = (Button) findViewById(R.id.catTap);

                                button1.setTextColor(Color.parseColor(offColor));
                                button2.setTextColor(Color.parseColor(offColor));
                                button3.setTextColor(Color.parseColor(onColor));

                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }

                container.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));



                return view;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

    private void timerStyle()
    {
        int sec;
        int min;
        int hour;
        int subTime;

        String TimerText;
        recadingTimer = (TextView) findViewById(R.id.recordingTimer);

        sec = time_sec%60;
        subTime = time_sec/60;
        min = subTime%60;
        hour = subTime/60;
        TimerText = String.format("%02d:%02d:%02d",hour,min,sec);

        recadingTimer.setText(TimerText);

    }

    private void stopRecord(){
        if(mp!=null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        second.cancel();
        String todayData = dateFormat.format(new Date());
        mFileAdapter.addItem("file"+fileNum,todayData,time_sec,soundFile.length()/100);

        fileListView.setAdapter(mFileAdapter);
    }

    MediaPlayer player;
    File dir = new File(Environment.getExternalStorageDirectory(),"sounds");
    File soundFile;

    private void startRecord(){

        fileNum++;

        if(mp==null){

            if(!dir.exists()){
                dir.mkdirs();
            }

            soundFile  = new File(dir+Integer.toString(fileNum)+".amr");
            if(!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    Log.i("fileCreate","Fale");
                    e.printStackTrace();
                }
            }
            mp = new MediaRecorder();
            mp.setAudioSource(MediaRecorder.AudioSource.MIC);
            mp.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mp.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);


            mp.setOutputFile(soundFile.getAbsolutePath());

            try {
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Log.i("fileRecording","Fale");
                e.printStackTrace();
            }


        }
    }





    CalendarAdapter adt;
    GridView grid;
    Calendar cal;
    TextView date;
    TextView nextM;
    TextView prevM;
    ListView fileListView;

    private PagerAdapter mPagerAdapter2 = new PagerAdapter() {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            try {
                View view = null;

                if (position == 0) {
                    view = View.inflate(mContext, R.layout.activity_all_list, null);
                    fileListView = (ListView)view.findViewById(R.id.fileListView);

                    dataSetting();

                }
                else if (position == 1) {
                    view = View.inflate(mContext, R.layout.activity_date_list, null);

                    adt = new CalendarAdapter(mContext);
                    grid = (GridView)view.findViewById(R.id.grid);
                    date = (TextView)view.findViewById(R.id.nowMonth);
                    prevM = (TextView)view.findViewById(R.id.prevMonth);
                    nextM = (TextView)view.findViewById(R.id.nextMonth);

                    cal = Calendar.getInstance();
                    int y = cal.get(Calendar.YEAR);
                    int m = cal.get(Calendar.MONTH)+1;
                    cal.set(y,m-1,1);

                    view.findViewById(R.id.prevMonth).setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            doPrev(v);
                        }
                    });
                    view.findViewById(R.id.nextMonth).setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            doNext(v);
                        }
                    });

                    doshow();
                }

                else {
                    view = View.inflate(mContext, R.layout.activity_category_list, null);

                }


                container.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));


                return view;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };


    private void dataSetting()
    {

        fileListView.setAdapter(mFileAdapter);

      fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView parent, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,StartRecordFile.class);
              RecordingItem item = (RecordingItem)parent.getItemAtPosition(i);
                intent.putExtra("filedata",item);
              startActivity(intent);
          }
      });
    }

    public void doPrev(View v)
    {
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH)-1;

        cal.set(y,m,1);

        doshow();
    }

    public void doNext(View v)
    {
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH)+1;

        cal.set(y,m,1);

        doshow();
    }

    private void doshow()
    {
        adt.clear();

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH)+1;
        date.setText(y+"년 "+m+"월");
        prevM.setText((m-1)+"월");
        nextM.setText((m+1)+"월");

        int sYoil = cal.get(Calendar.DAY_OF_WEEK);

        for(int i=1;i<sYoil;i++)
        {
            boolean isTake = false;
            CalendarItem item = new CalendarItem(Integer.toString(y),Integer.toString(m),isTake);
            adt.add(item);
        }

        int IDay = getLastDay(y,m);

        for(int i=1;i<=IDay;i++)
        {
            boolean isTake = false;
            if(i%2==0)
                isTake=true;

            cal.set(y,cal.get(Calendar.MONTH),i);
            int yoil = cal.get(Calendar.DAY_OF_WEEK);

            CalendarItem item = new CalendarItem(Integer.toString(y),Integer.toString(m),Integer.toString(i),yoil,isTake);
            adt.add(item);
        }
        grid.setAdapter(adt);
    }

    private int getLastDay(int year,int month)
    {
        Date d = new Date(year,month,1);
        d.setHours(d.getDay()-1*24);
        SimpleDateFormat f = new SimpleDateFormat("dd");
        return Integer.parseInt(f.format(d));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
