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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import jangkoo.predict.R;



public class PredictsListviewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView team1;
    TextView team2;
    TextView txtThird;
    Button bet1,bet2,bet0;
    ImageView txtFourth;
    Typeface font,fontNum;
    public PredictsListviewAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
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

            convertView = inflater.inflate(R.layout.predict, null);

            team1 = (TextView) convertView.findViewById(R.id.team1);
            team2 = (TextView) convertView.findViewById(R.id.team2);
//            txtThird=(TextView) convertView.findViewById(R.id.name);
//            txtFourth=(ImageView) convertView.findViewById(R.id.image);
            bet1 = (Button) convertView.findViewById(R.id.bet1);
            bet2 = (Button) convertView.findViewById(R.id.bet2);
            bet0 = (Button) convertView.findViewById(R.id.bet0);
            team1.setTypeface(font);
            team2.setTypeface(font);
            final View finalConvertView = convertView;
            bet1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity.getApplicationContext(), "You predicted", Toast.LENGTH_LONG).show();
                    view.setBackgroundResource(R.drawable.bet_button);
                    bet0.setBackgroundResource(R.drawable.money_inactive);
                    bet2.setBackgroundResource(R.drawable.money_inactive);
                    notifyDataSetChanged();
                    notifyDataSetInvalidated();
                    view.invalidate();
                    bet0.invalidate();
                    bet2.invalidate();

                }

                ;
            });
            bet2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity.getApplicationContext(), "You predicted", Toast.LENGTH_LONG).show();
                    view.setBackgroundResource(R.drawable.bet_button);
                    bet0.setBackgroundResource(R.drawable.money_inactive);
                    bet1.setBackgroundResource(R.drawable.money_inactive);
                    notifyDataSetChanged();
                    notifyDataSetInvalidated();
                    view.invalidate();
                    bet0.invalidate();
                    bet1.invalidate();

                }

                ;
            });
            bet0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity.getApplicationContext(), "You predicted", Toast.LENGTH_LONG).show();
                    view.setBackgroundResource(R.drawable.bet_button);
                    bet1.setBackgroundResource(R.drawable.money_inactive);
                    bet2.setBackgroundResource(R.drawable.money_inactive);
                    notifyDataSetChanged();
                    notifyDataSetInvalidated();
                    view.invalidate();
                    bet1.invalidate();
                    bet2.invalidate();

                }

                ;
            });
        }
        HashMap<String, String> map=list.get(position);
        team1.setText(map.get("team1"));
        team2.setText(map.get("team2"));
//        txtThird.setText(map.get("name"));
//        txtFourth.setText(map.get(FOURTH_COLUMN));
        if(map.get("id")!=null) {
            convertView.setBackgroundColor(Integer.parseInt(map.get("id")) > 0 ? Color.parseColor("#e82d2d") : Color.WHITE);
            team2.setTextColor(Integer.parseInt(map.get("id")) > 0 ? Color.GREEN : Color.RED);
        }
        return convertView;
    }

}

