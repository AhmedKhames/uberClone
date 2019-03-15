package com.example.ahmedkhames.uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {


    public void redirectActivity(){

        if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){

            Intent intent = new Intent(getApplicationContext(),RiderActivity.class);
            startActivity(intent);

        }else {

            Intent intent = new Intent(getApplicationContext(),ViewRequestActivity.class);
            startActivity(intent);
        }

    }


    public void getStarted(View view){

        Switch userTypeSwitch = (Switch)findViewById(R.id.userType);

        Log.i("Switch value: ",String.valueOf(userTypeSwitch.isChecked()));

        String userType = "rider";

        if (userTypeSwitch.isChecked()){

            userType = "driver";
        }
        ParseUser.getCurrentUser().put("riderOrDriver",userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();

            }
        });

        Log.i("Type is: ",userType);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ParseUser.getCurrentUser() == null){

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null){
                        Log.i("Info","successful");
                    }else {
                        Log.i("Info","failed");
                    }
                }
            });
        }else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null){

                Log.i("Info", String.valueOf(ParseUser.getCurrentUser().get("riderOrDriver")));
                redirectActivity();

            }
        }

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
