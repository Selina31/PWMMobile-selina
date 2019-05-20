package com.example.pwmmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pwmmobile.R;

public class HostPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_page);
        final Button bGuidebook = findViewById(R.id.bGuidebook);
        final Button bGuestlist = findViewById(R.id.bGuestlist);
        final Button bClue = findViewById(R.id.bClue);
        final Button bProceed = findViewById(R.id.bProceed);

        bGuidebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gCover = new Intent(HostPageActivity.this, CoverActivity.class);
                startActivity(gCover);
            }
        });

        bGuestlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gList = new Intent(HostPageActivity.this, GuestListActivity.class);
                HostPageActivity.this.startActivity(gList);
            }
        });

        bClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clue = new Intent(HostPageActivity.this, ClueListActivity.class);
                HostPageActivity.this.startActivity(clue);
            }
        });

        bProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }
}
