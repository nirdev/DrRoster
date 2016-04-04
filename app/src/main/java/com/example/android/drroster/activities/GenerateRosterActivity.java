package com.example.android.drroster.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.drroster.R;
import com.example.android.drroster.UI.NavigationView;
import com.example.android.drroster.fragments.ChooseMonthFragment;
import com.example.android.drroster.fragments.FirstCallFragment;

public class GenerateRosterActivity extends AppCompatActivity {

    public static final int GENERATOR_FRAGMENTS_NUMBER = 7;
    public static final int FRAGMENT_CHOOSE_MONTH_INDEX = 0;
    public static final int FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_roster);

        //Set first layout
        // get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // replace
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place_holder_generate_roster, new ChooseMonthFragment());
        ft.commit();

        //Set Listener states to change fragments
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setFragmentChangeListener(new NavigationView.IFragmentChangeListener() {
                @Override
                public void onFragmentChange(int index) {
                    // get fragment manager
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    switch (index){
                        case FRAGMENT_CHOOSE_MONTH_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster, new ChooseMonthFragment());
                            break;
                        case FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster, new FirstCallFragment(),"TAG HERE");
                            break;
                        case 2:
                            ft.replace(R.id.fragment_place_holder_generate_roster, new FirstCallFragment(),"TAG HERE 2");
                            break;
                    }
                    // replace
                    ft.commit();
                }
            });
        }

    }

}
