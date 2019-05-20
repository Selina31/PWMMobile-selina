package com.example.pwmmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pwmmobile.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RetrieveUser.AsyncResponse {

    RetrieveUser.AsyncResponse context = this;
    String email;
    String password;
    String guestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PWMFirebase.init();

        Button blogin = findViewById(R.id.bLogin);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.login(email, password, context);
            }
        });

        Button bCheckin = findViewById(R.id.bCheckin);
        bCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GuestPageIntent = new Intent(MainActivity.this, GuestPageActivity.class);
                MainActivity.this.startActivity(GuestPageIntent);
            }
        });

        EditText etEmail = findViewById(R.id.etEmail);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                email = editable.toString();
            }
        });

        EditText etPassword = findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });

        EditText etGcode = findViewById(R.id.etGcode);
        etGcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                guestCode = editable.toString();
            }
        });

    }

    public void loginResponse(User user) {
        if (user != null) {
            Globals.user = user;
            Intent intent = new Intent(this, PartyListActivity.class);
            startActivity(intent);
            //Intent HostPageIntent = new Intent(MainActivity.this, HostPageActivity.class);
            //MainActivity.this.startActivity(HostPageIntent);
        } else {
            Toast.makeText(this, "Invalid email/password combo", Toast.LENGTH_SHORT).show();
        }
    }

        /* final Button bHost = findViewById(R.id.bHost);
        final Button bGuest = findViewById(R.id.bGuest);

        bHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HostPageIntent = new Intent(MainActivity.this, HostPageActivity.class);
                MainActivity.this.startActivity(HostPageIntent);
            }
        });

        bGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GuestPageIntent = new Intent(MainActivity.this, GuestPageActivity.class);
                MainActivity.this.startActivity(GuestPageIntent);
            }
        }); */


}
