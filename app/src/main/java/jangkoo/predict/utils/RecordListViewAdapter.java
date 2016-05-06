package jangkoo.predict.utils;

/**
 * Created by User on 28/10/2015.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import jangkoo.predict.R;

public class RecordListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    ImageView txtFourth;
    Typeface font,fontNum;
    public RecordListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
        font = Typeface.createFromAsset(activity.getAssets(), "fonts/HARLOWSI.TTF");
        fontNum = Typeface.createFromAsset(activity.getAssets(), "fonts/DorBlue.ttf");

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.record_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.team1);
            txtSecond=(TextView) convertView.findViewById(R.id.team2);
            txtThird=(TextView) convertView.findViewById(R.id.amount);
            txtFourth=(ImageView) convertView.findViewById(R.id.image);

            txtFirst.setTypeface(font);
            txtSecond.setTypeface(font);
            txtThird.setTypeface(fontNum);
        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get("team1"));
        txtSecond.setText(map.get("team2"));
        txtThird.setText(map.get("amount"));
//        txtFourth.setText(map.get(FOURTH_COLUMN));
        if(map.get("amount")!=null) {
            convertView.setBackgroundColor(Integer.parseInt(map.get("amount")) > 0 ? Color.parseColor("#e82d2d") : Color.WHITE);
            txtSecond.setTextColor(Integer.parseInt(map.get("amount")) > 0 ? Color.GREEN : Color.RED);
        }
        return convertView;
    }

}
