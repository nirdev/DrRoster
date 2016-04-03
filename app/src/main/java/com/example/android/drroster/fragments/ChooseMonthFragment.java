package com.example.android.drroster.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.dialogs.ChooseMonthDialog;

import java.text.DateFormatSymbols;

public class ChooseMonthFragment extends Fragment {

    ChooseMonthDialog dialog;
    Button chooseMonthBtn;
    View view;

    //empty constructor
    public ChooseMonthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choos_month,
                container, false);


        //initialize dialog

        dialog = new ChooseMonthDialog(getActivity());
        // Listener for dialog result
        dialog.setDialogResult(new ChooseMonthDialog.OnMyDialogResult() {
            public void finish(int month,int year) {

                TextView choosedMonthTextview = (TextView) view.findViewById(R.id.choosed_month_textview);
                if (choosedMonthTextview != null) {
                    //Set chosen month text view
                    choosedMonthTextview.setText(
                            //convert month number to name
                            new DateFormatSymbols().getMonths()[month-1] + " " + year);
                }
            }
        });

        //Sets onClick listener for choose month button
        chooseMonthBtn = (Button) view.findViewById(R.id.pick_month_dialog_button_fg);
        chooseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        return view;
    }
}
