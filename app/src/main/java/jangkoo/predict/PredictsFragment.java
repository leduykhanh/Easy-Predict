package jangkoo.predict;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import jangkoo.predict.app.MyApplication;
import jangkoo.predict.model.Predict;
import jangkoo.predict.model.Team;
import jangkoo.predict.services.ServiceHandler;
import jangkoo.predict.utils.PredictsListviewAdapter;

import static jangkoo.predict.app.MyApplication.TAG;

public class PredictsFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
    DownloadTask downloadTask ;
    TextView content;
    View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PredictsListviewAdapter adapter;
    private ArrayList<Predict> betsList;
    private TextView totalTv;
    private ListView predictsList;
    Context ctx;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;
    public PredictsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = inflater.getContext();
        rootView = inflater.inflate(R.layout.fragment_predicts, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
//        content = (TextView) rootView.findViewById(R.id.content);
        betsList=new ArrayList<Predict>();
        Predict temp1=new Predict("1","Football",new Team("Arsenal",""),new Team("Barca",""),2);
        Predict temp2=new Predict("1","Football",new Team("Arsenal",""),new Team("Barca",""),2);
//        betsList.add(temp1);
//        betsList.add(temp2);

        predictsList = (ListView) rootView.findViewById(R.id.content_list);
        adapter =  new PredictsListviewAdapter(this.getActivity(), betsList);
        predictsList.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchBets();
                                    }
                                }
        );
//        downloadTask = new DownloadTask(inflater.getContext());
//        downloadTask.execute("http://jangkoo.com/apps/hongbao/getdata.php");

        return rootView;
    }
    @Override
    public void onRefresh() {
        Toast.makeText(ctx, "Refreshed", Toast.LENGTH_SHORT).show();
        fetchBets();
    }
    private void fetchBets(){
        swipeRefreshLayout.setRefreshing(true);
        betsList.clear();
        Toast.makeText(ctx, "fetching bets", Toast.LENGTH_SHORT).show();
        String url = "http://www.jangkoo.com/api/bet/list";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject betObj = response.getJSONObject(i);

                                    String _id = betObj.getString("_id");
                                    String name = betObj.getString("name");
                                    JSONObject firstTeam = betObj.getJSONObject("firstTeam");
                                    JSONObject secondTeam = betObj.getJSONObject("secondTeam");
                                    int firstScore = betObj.getInt("firstScore");
                                    Predict p = new Predict( _id, name, new Team(firstTeam.getString("name"),firstTeam.getString("logo")) ,
                                            new Team(secondTeam.getString("name"),secondTeam.getString("logo")), firstScore);
                                    betsList.add(p);


                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(ctx, "Picture posted", Toast.LENGTH_SHORT).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
        swipeRefreshLayout.setRefreshing(false);
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
    class DownloadTask extends AsyncTask<String, String, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(sUrl[0],1);
            String content = "AAAAAAAA";
            String id="";
            if(jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dashboardJSON = jsonArray.getJSONObject(i);

                        content = dashboardJSON.getString("content");
                        id = dashboardJSON.getString("id");
                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put("id",id);
                        temp.put("content",content);
//                        arrayList.add(temp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            Toast.makeText(context, "Đang tải dữ liệu ...", Toast.LENGTH_LONG).show();
//        mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... result) {
            super.onProgressUpdate(result);
            // if we get here, length is known, now set indeterminate to false
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setMax(100);
//        mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
//        mProgressDialog.dismiss();
//            content.setText(result);
            adapter.notifyDataSetChanged();
            if (result != null)
                Toast.makeText(context, "Chúc mừng năm mới", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }
    }
}
