package com.app.project.recordingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FileListAdapter extends BaseAdapter {

    private ArrayList<RecordingItem> itemList = new ArrayList<>();

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public RecordingItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recording_file,parent,false);
        }

        TextView tv_name = (TextView)convertView.findViewById(R.id.fileName);
        TextView tv_day = (TextView)convertView.findViewById(R.id.fileData);
        TextView tv_time = (TextView)convertView.findViewById(R.id.fileTime);
        TextView tv_size = (TextView)convertView.findViewById(R.id.fileSize);

        RecordingItem rItem = getItem(position);

        tv_name.setText(rItem.getName());
        tv_day.setText(rItem.getDay());
        tv_time.setText(rItem.getTime());
        tv_size.setText(rItem.getSize());

        return convertView;
    }

    public void addItem(String n,String d,int t,float s)
    {
        RecordingItem mItem = new RecordingItem();

        mItem.setName(n);
        mItem.setDay(d);
        mItem.setTime(t);
        mItem.setSize(s);

        itemList.add(mItem);

    }

}
