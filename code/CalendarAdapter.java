package com.app.project.recordingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {

    ArrayList<CalendarItem>mItem = new ArrayList<CalendarItem>();
    LayoutInflater minflater;
    Context mContext;

    CalendarAdapter(Context context)
    {
        minflater = LayoutInflater.from(context);
        mContext = context;
    }

    public  void add(CalendarItem item){mItem.add(item);}
    public void clear(){mItem.clear();}

    @Override
    public int getCount(){return mItem.size();}

    @Override
    public Object getItem(int position){return null;}

    @Override
    public long getItemId(int position)
    {
        Long id = Long.parseLong(mItem.get(position).getDay());
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v;
        if(convertView == null)
        {
            v = minflater.inflate(R.layout.activity_calendar_gridview,null);
        }
        else
        {
            v = convertView;
        }

        TextView day = (TextView)v.findViewById(R.id.day);
        day.setText(mItem.get(position).getDay());

        int yoil = mItem.get(position).getYoil();

        if(yoil == 1)
            day.setTextColor(mContext.getResources().getColor(R.color.color_red));
        else
            day.setTextColor(mContext.getResources().getColor(R.color.color_black));


        day.setVisibility(View.VISIBLE);

        TextView take = (TextView)v.findViewById(R.id.take);
        if(mItem.get(position).getIsTake())
            take.setVisibility(View.VISIBLE);

        return v;
    }

}
