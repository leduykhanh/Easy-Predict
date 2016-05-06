package jangkoo.predict.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import jangkoo.predict.MainActivity;
import jangkoo.predict.R;

/**
 * Created by User on 26/1/2016.
 */
public class CreateDialogFragment extends DialogFragment {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText time,amount,name;
    SQLiteDatabase db;
    Context ctx;
    int is_added = 1;
    TextView is_added_text;
    ToggleButton toggle;
    public interface CreateDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    CreateDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (CreateDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ctx = inflater.getContext();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        View v = inflater.inflate(R.layout.dialog, null);
        name = (EditText) v.findViewById(R.id.name);
        amount = (EditText) v.findViewById(R.id.amount);
        is_added_text = (TextView)v.findViewById(R.id.addedText);
         toggle = (ToggleButton) v.findViewById(R.id.is_added);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    is_added = 1;
                    is_added_text.setText("Cho");
                } else {
                    is_added = -1;
                    is_added_text.setText("Dc Cho");
                }
            }
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (toggle.isChecked()) {
                    is_added = -1;
                    is_added_text.setText("Cho");
                } else {
                    is_added = 1;
                    is_added_text.setText("Nhận");
                }
            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        db = MainActivity.mDBHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        Calendar cal = Calendar.getInstance();
                        values.put("time", time.getText().toString());
                        try {
                            values.put("amount", is_added * Integer.parseInt(amount.getText().toString()));
                            values.put("name", !name.getText().toString().equals("") ? name.getText().toString() : "Ai đó");
                            long newRowId;
                            newRowId = db.insert("hongbao", null, values);
                            db.close();
                            if (newRowId > -1) {
                                Toast toast = Toast.makeText(ctx, "Chuẩn rồi", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } catch (NumberFormatException e) {
                            Toast toast = Toast.makeText(ctx, "Số bạn điền không hợp lệ", Toast.LENGTH_SHORT);
                            toast.show();

                        }

                        mListener.onDialogPositiveClick(CreateDialogFragment.this);
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Personalizamos

        Resources res = getResources();

        //Buttons
        Button positive_button =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        positive_button.setBackground(res.getDrawable(R.drawable.plus));

        Button negative_button =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
        negative_button.setBackground(res.getDrawable(R.drawable.cancel));
        time = (EditText) getDialog().findViewById(R.id.time);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                time.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        int color = Color.parseColor("#304f5a");

        //Title
        int titleId = res.getIdentifier("alertTitle", "id", "android");
        View title = getDialog().findViewById(titleId);
        if (title != null) {
            ((TextView) title).setTextColor(color);
        }

        //Title divider
        int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(color);
        }
    }
}

