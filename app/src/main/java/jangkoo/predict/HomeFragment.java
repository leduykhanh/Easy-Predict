package jangkoo.predict;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.HashMap;

import jangkoo.predict.utils.RecordListViewAdapter;

public class HomeFragment extends Fragment {
    private RecordListViewAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList;
    private TextView totalTv;
    private ListView recordList;
    private ShareButton shareButton;
    private Button shareButton1;
    Context ctx;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;
    View rootView;
    public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ctx = inflater.getContext();


        arrayList=new ArrayList<HashMap<String,String>>();
        recordList = (ListView) rootView.findViewById(R.id.listView);
        adapter =  new RecordListViewAdapter(this.getActivity(), arrayList);
        totalTv = (TextView) rootView.findViewById(R.id.total);
        getItem();
        recordList.setAdapter(adapter);
//        shareButton = (ShareButton) rootView.findViewById(R.id.share_btn);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                postPicture();
//            }
//        });
        shareButton1 = (Button) rootView.findViewById(R.id.share_btn1);
        shareButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPicture();
            }
        });
        return rootView;
    }
    private  void getItem(){
        arrayList.clear();
        SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
        int total = 0;
        Cursor cursor = db.rawQuery("SELECT time,amount,team1,team2 FROM bet_history WHERE 1=1 ORDER BY time DESC LIMIT 100",null);
        cursor.moveToFirst();
        int i=0;
        while (!cursor.isAfterLast()) {
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put("time",cursor.getString(0));
            temp.put("amount",cursor.getString(1));
            total += (cursor.getString(1)!=null &&!cursor.getString(1).equals(""))?Integer.parseInt(cursor.getString(1)):0;
            temp.put("team1", cursor.getString(2));
            temp.put("team2", cursor.getString(3));
            arrayList.add(temp);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        totalTv.setText(total + "");
    }
    @Override
    public void onResume() {
        super.onResume();
        getItem();
        adapter.notifyDataSetChanged();
    }
    public void refresh(){
        getItem();
        adapter.notifyDataSetChanged();
    }
    public void postPicture() {
        Toast toast = Toast.makeText(ctx, "Picture posted", Toast.LENGTH_SHORT);
        toast.show();
        //check counter
        if(counter == 0) {
            //save the screenshot
            //View rv = rootView.findViewById(android.R.id.content).getRootView();
            rootView.setDrawingCacheEnabled(true);
            // creates immutable clone of image
            image = Bitmap.createBitmap(rootView.getDrawingCache());
            // destroy
            rootView.destroyDrawingCache();

            //share dialog
//            AlertDialog.Builder shareDialog = new AlertDialog.Builder(getActivity());
//            shareDialog.setTitle("Share Screen Shot");
//            shareDialog.setMessage("Share image to Facebook?");
//            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
                    //share the image to Facebook
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
//                    shareButton.setShareContent(content);
                    counter = 1;
//                    shareButton.performClick();
                    ShareDialog shareDialog = new ShareDialog(getActivity());
                    shareDialog.show(getActivity(), content);
//                }
//            });
//            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            shareDialog.show();
        }
        else {
            counter = 0;
//            shareButton.setShareContent(null);
        }
    }
}
