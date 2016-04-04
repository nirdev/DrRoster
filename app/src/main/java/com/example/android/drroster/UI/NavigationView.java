package com.example.android.drroster.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;

/**
 * Created by Nir on 4/1/2016.bububu
 */
public class NavigationView extends RelativeLayout {

    //Navigation buttons
    private Button mPreviousButton;
    private Button mNextButton;

    //stepBar
    private StepBarView mStepBarView;
    //Index of stepBar & fragment
    private int mSelectedIndex = -1;

    //Constructor @pram attrs is android default attrs
    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    //Interface to set call back to the activity on fragment change
    public interface IFragmentChangeListener {
        public void onFragmentChange(int index);
    }

    private IFragmentChangeListener fragmentChangeListener;

    public void setFragmentChangeListener(IFragmentChangeListener listener){
        this.fragmentChangeListener = listener;
    }

    private void initializeViews(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigation, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //find step bar in view
        mStepBarView  = (StepBarView) findViewById(R.id.step_bar_view);

        mPreviousButton = (Button) this.findViewById(R.id.navigationView_previous_button);
        mPreviousButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mSelectedIndex > 0) {
                    int newSelectedIndex = mSelectedIndex - 1;
                    setSelectedIndex(newSelectedIndex);
                }
            }
        });

        // When the next button is pressed, select the next item in the list.
        mNextButton = (Button) this.findViewById(R.id.navigationView_next_button);
        mNextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ( mSelectedIndex < GenerateRosterActivity.GENERATOR_FRAGMENTS_NUMBER - 1) {
                    int newSelectedIndex = mSelectedIndex + 1;
                    setSelectedIndex(newSelectedIndex);
                }
            }
        });

        // Select the first value by default.
        setSelectedIndex(0);
    }

    /**
     * Sets the selected index of the spinner.
     * @param index the index of the value to select.
     */
    public void setSelectedIndex(int index) {


        if (fragmentChangeListener != null){
            fragmentChangeListener.onFragmentChange(index);
        }

        // Set the current index and display the value.
        mSelectedIndex = index;

        //Customize stepBar
        mStepBarView.setBoldDot (index);

        // If the first value is shown, hide the previous button.
        if (mSelectedIndex == 0) {
            mPreviousButton.setVisibility(INVISIBLE);
        }
        else {
            mPreviousButton.setVisibility(VISIBLE);
        }
        // If the last value is shown, hide the next button.
        if (mSelectedIndex ==  GenerateRosterActivity.GENERATOR_FRAGMENTS_NUMBER - 1 ) {
            mNextButton.setVisibility(INVISIBLE);
        }
        else {
            mNextButton.setVisibility(VISIBLE);
        }
    }
}

