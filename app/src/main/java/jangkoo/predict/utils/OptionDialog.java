//package jangkoo.hongbao.utils;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import jangkoo.hongbao.R;
//
//
///**
// * Created by User on 22/9/2015.
// */
//public class OptionDialog extends DialogFragment {
//
//    Context ctx;
//    Typeface font;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Get the layout inflater
//        final LayoutInflater inflater = getActivity().getLayoutInflater();
//        this.ctx = inflater.getContext();
//        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HARLOWSI.TTF");
//        final String[] items = getResources().getStringArray(R.array.choices);
//
//// Instead of String[] items, Here you can also use ArrayList for your custom object..
//
//        ListAdapter adapter = new ArrayAdapter<String>(
//                ctx, R.layout.dialog_row, items) {
//
//            ViewHolder holder;
//            Drawable icon;
//
//            class ViewHolder {
//                ImageView icon;
//                TextView title;
//            }
//
//            public View getView(int position, View convertView,
//                                ViewGroup parent) {
////                final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
////                        .getSystemService(
////                                Context.LAYOUT_INFLATER_SERVICE);
//
//                if (convertView == null) {
//                    convertView = inflater.inflate(
//                            R.layout.dialog_row, null);
//
//                    holder = new ViewHolder();
//                    holder.icon = (ImageView) convertView
//                            .findViewById(R.id.icon);
//                    holder.title = (TextView) convertView
//                            .findViewById(R.id.title);
//                    holder.title.setTypeface(font);
//                    convertView.setTag(holder);
//                } else {
//                    // view already defined, retrieve view holder
//                    holder = (ViewHolder) convertView.getTag();
//                }
//
//                Drawable drawable = getResources().getDrawable(R.drawable.youtube); //this is an image from the drawables folder
//
//                holder.title.setText(items[position]);
//                holder.icon.setImageDrawable(drawable);
//
//                return convertView;
//            }
//        };
//
//        builder.setView(inflater.inflate(R.layout.dialog, null))
//                .setTitle("RELAX FEW MINS").setIcon(R.drawable.youtube)
//                .setAdapter(adapter,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int item) {
//                                switch (item) {
//                                    case 1:
///
//                                        break;
//                                    case 0:
//                                        try {
//
//                                        }
//                                        catch (Exception e){
//                                            Toast.makeText(ctx, "Downloading is in progress" + e, Toast.LENGTH_LONG).show();
//                                        }
//                                        break;
//                                }
//                            }
//                        });
//
//        // Add action buttons
//
//        return builder.create();
//    }
//
//
//}
