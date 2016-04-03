package com.example.android.drroster.Signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;


public class SigninActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

    }

    public void setDepartmentNameOnClick(View view) {


        //inflate string from activity_signin xml
        editText = (EditText) findViewById(R.id.department_name_edit_text);

        if ( editText.getText() != null && !editText.getText().toString().isEmpty()) {
            //Save String in sharedpref
            SharedPreferences mSetSettings = getSharedPreferences(SigninConstants.SHAREDPREF_FILE_KEY, SigninConstants.SHAREDPREF_MODE_KEY);
            SharedPreferences.Editor editor = mSetSettings.edit();
            editor.putString(SigninConstants.SHAREDPREF_DEPARTMENT_KEY, editText.getText() + "");
            editor.apply();

            //Show user what name has been saved
            Toast.makeText(getApplicationContext(), editText.getText() + " is saved", Toast.LENGTH_SHORT).show();

            //Go to choose month activity
            Intent i = new Intent(this, GenerateRosterActivity.class);
            startActivity(i);
        }
        else {
            //Tell user choose name
            Toast.makeText(getApplicationContext(), "Please choose name", Toast.LENGTH_SHORT).show();

        }


    }
}
