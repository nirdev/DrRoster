package com.example.android.drroster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.drroster.Signin.SigninActivity;
import com.example.android.drroster.Signin.SigninConstants;
import com.example.android.drroster.activities.GenerateRosterActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Go to choose month activity //TODO:Delete after finish build sign in
        Intent i1 = new Intent(this,GenerateRosterActivity.class );
        startActivity(i1);

        //Retrieve string from sharedPreference
        SharedPreferences mSettings = getSharedPreferences(SigninConstants.SHAREDPREF_FILE_KEY, SigninConstants.SHAREDPREF_MODE_KEY);
        String departmentName = mSettings.getString(SigninConstants.SHAREDPREF_DEPARTMENT_KEY, null);

        //check if this is first signin
        if ( departmentName == null || departmentName.isEmpty() ){

            //if true Got to signinActivity
            Intent i = new Intent(this,SigninActivity.class);
            startActivity(i);
        }
        // if false start app
    }
}

