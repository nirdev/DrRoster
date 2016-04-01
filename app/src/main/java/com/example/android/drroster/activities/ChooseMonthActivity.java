package com.example.android.drroster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.dialogs.ChooseMonthDialog;

import java.text.DateFormatSymbols;

public class ChooseMonthActivity extends AppCompatActivity {


    Boolean isMonthPicked = false;
    ChooseMonthDialog dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_month);

        TextView barLayoutTitle = new TextView(this);
        barLayoutTitle = (TextView) findViewById(R.id.toolbar_title_rostergen);
        barLayoutTitle.setText(R.string.ChooseMonthActivityBarTitle);

        dialog = new ChooseMonthDialog(this);
        //  initialization stuff, blah blah
        dialog.setDialogResult(new ChooseMonthDialog.OnMyDialogResult() {
            public void finish(int month,int year) {

                TextView choosedMonthTextview = (TextView) findViewById(R.id.choosed_month_textview);
                if (choosedMonthTextview != null) {
                    //                                 convert month number to name
                    choosedMonthTextview.setText(new DateFormatSymbols().getMonths()[month-1] + " " + year);
                    isMonthPicked = true;
                }
            }
        });

    }

    public void chooseMonthOnclick(View view) {
        //Show dialog
        dialog.show();
    }


    public void goToFirstCallActivity(View view) {
        if (isMonthPicked){
            Intent intent = new Intent(this,GenerateRosterActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"please choose month",Toast.LENGTH_SHORT).show();
        }
    }
}
