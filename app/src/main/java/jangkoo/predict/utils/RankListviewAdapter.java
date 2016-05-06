package jangkoo.predict.utils;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import jangkoo.predict.R;



public class RankListviewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView rank;
    TextView name;
    TextView amount;
    Button bet1,bet2,bet0;
    ImageView txtFourth;
    Typeface font,fontNum;
    public RankListviewAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
        font = Typeface.createFromAsset(activity.getAssets(), "fonts/DorBlue.ttf");
        fontNum = Typeface.createFromAsset(activity.getAssets(), "fonts/HARLOWSI.TTF");

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

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.rank, null);

            rank = (TextView) convertView.findViewById(R.id.rank);
            name = (TextView) convertView.findViewById(R.id.name);
            amount=(TextView) convertView.findViewById(R.id.amount);
            rank.setTypeface(font);
            name.setTypeface(font);
            amount.setTypeface(fontNum);

        }
        HashMap<String, String> map=list.get(position);
        rank.setText(map.get("rank"));
        name.setText(map.get("name"));
//        txtThird.setText(map.get("name"));
//        txtFourth.setText(map.get(FOURTH_COLUMN));
        if(map.get("id")!=null) {
            convertView.setBackgroundColor(Integer.parseInt(map.get("id")) > 0 ? Color.parseColor("#e82d2d") : Color.WHITE);
            name.setTextColor(Integer.parseInt(map.get("id")) > 0 ? Color.GREEN : Color.RED);
        }
        return convertView;
    }

}
