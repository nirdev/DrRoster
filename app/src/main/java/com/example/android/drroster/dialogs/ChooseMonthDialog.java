package com.example.android.drroster.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.android.drroster.R;

import java.lang.reflect.Field;

/**
 * Created by Nir on 3/31/2016.
 * Dialog guide - http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
 * Hide Day in datepciker guide - http://stackoverflow.com/questions/30789907/hide-day-month-or-year-from-datepicker-in-android-5-0-lollipop
 *Interface between dialog and activity -  http://stackoverflow.com/questions/4279787/how-can-i-pass-values-between-a-dialog-and-an-activity
 */
public class ChooseMonthDialog extends Dialog {

    public Activity c;
    private DatePicker datePicker;
    int mYear = 0;
    int mMonth = 0;
    OnMyDialogResult mDialogResult;



    public ChooseMonthDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_month_layout);


        //Inflate datePicker spinner from vew and sets current month and year
        datePicker = (DatePicker) findViewById(R.id.datepicker_sinner);
        //Set onClickListener on OK button inside dialog - callback in ChooseMonthActivity
        Button setMonthButton = (Button) findViewById(R.id.choose_moth_dialog_button);
        setMonthButton.setOnClickListener(new OKListener());

        //Check which SDK is used in order to hide day spinner
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
            if (daySpinnerId != 0) {
                View daySpinner = datePicker.findViewById(daySpinnerId);
                if (daySpinner != null) {
                    //Actually set visibility
                    daySpinner.setVisibility(View.GONE);
                }
            }


            int monthSpinnerId = Resources.getSystem().getIdentifier("mMonth", "id", "android");
            if (monthSpinnerId != 0) {
                View monthSpinner = datePicker.findViewById(monthSpinnerId);
                if (monthSpinner != null) {
                    //Actually set visibility
                    monthSpinner.setVisibility(View.VISIBLE);
                }
            }

            int yearSpinnerId = Resources.getSystem().getIdentifier("mYear", "id", "android");
            if (yearSpinnerId != 0) {
                View yearSpinner = datePicker.findViewById(yearSpinnerId);
                if (yearSpinner != null) {
                    //Actually set visibility
                    yearSpinner.setVisibility(View.VISIBLE);
                }

            }

        } else { //Older SDK versions
            Field f[] = datePicker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mDayPicker") || field.getName().equals("mDaySpinner")) {
                    field.setAccessible(true);
                    Object dayPicker = null;
                    try {
                        dayPicker = field.get(datePicker);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (((View) dayPicker) != null) {
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }

                if (field.getName().equals("mMonthPicker") || field.getName().equals("mMonthSpinner")) {
                    field.setAccessible(true);
                    Object monthPicker = null;
                    try {
                        monthPicker = field.get(datePicker);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (((View) monthPicker) != null) {
                        ((View) monthPicker).setVisibility(View.VISIBLE);
                    }
                }

                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = null;
                    try {
                        yearPicker = field.get(datePicker);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (((View) yearPicker) != null) {
                        ((View) yearPicker).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    //Pass Date and Year back to activity via interface class below
    private class OKListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {

            //Inflate date picker again to check for changes in the month that was being chose
            datePicker = (DatePicker) findViewById(R.id.datepicker_sinner);
            mYear = datePicker.getYear();
            mMonth = datePicker.getMonth();


            if( mDialogResult != null ){
                //mMonth + 1 because the built-in moth array starts with 0 - not good for the jews
                mDialogResult.finish(mMonth + 1, mYear);
            }
            ChooseMonthDialog.this.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(int month,int year);
    }
}
