package com.example.android.drroster.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.android.drroster.R;

/**
 * Created by Nir on 4/1/2016.
 */
public class NavigationView extends RelativeLayout {

    public NavigationView(Context context) {
        super(context);
        initializeViews(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.navigation_view, this);
    }
}
