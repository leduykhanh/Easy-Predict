package jangkoo.predict;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import jangkoo.predict.youtube.VideoListDemoActivity;

public class NgheNhacXuanFragment extends Fragment {
	
	public NgheNhacXuanFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        final ImageView imgv = (ImageView) rootView.findViewById(R.id.txtImage);
        imgv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), VideoListDemoActivity.class);
                                        startActivity(intent);

                                    }
                                }

        );
        return rootView;
    }
}
