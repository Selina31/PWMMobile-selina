package com.example.pwmmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class UserTestMainActivity extends AppCompatActivity implements RetrieveUser.AsyncResponse {

    public void loginResponse(User user) {
        if (user != null) {
            // TODO navigate to user page
            Log.e("TODO", "navigate to user page");
        } else {
            // TODO handle failed login
            Log.e("TODO", "handle failed login");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_test);

        User.login("email", "password", this);
    }

}
