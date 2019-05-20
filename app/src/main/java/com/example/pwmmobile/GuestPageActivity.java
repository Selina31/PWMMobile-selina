package com.example.pwmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GuestPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);

        CreateDummyParty create = new CreateDummyParty();
        Globals.currParty = create.createDummyParty();

        final Button bClue = findViewById(R.id.bClues);
        final Button bGossip = findViewById(R.id.bGossip);
        final Button bCharacter = findViewById(R.id.bCharacter);

        //Once we have login functionality, we can replace this with the logged in Guest
        List<String> gossip = new ArrayList<String>();
        gossip.add("This is for gossip about character 1");
        gossip.add("This is for gossip about character 2");
        gossip.add("This is for gossip about character 3");

        final Character character = new Character(
                "ThisIsCharName",
                "ThisIsCharRole",
                "This is who you are for the character you are playing",
                "This is getting into the character",
                gossip,
                true
        );
        final Guest guest = new Guest("test@guest.com");
        guest.setCharacter(character);

        bCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent character = new Intent(
                        GuestPageActivity.this,
                        com.example.pwmmobile.CharacterActivity.class
                ).putExtra("character", guest.getCharacter());

                GuestPageActivity.this.startActivity(character);
            }
        });

        bGossip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gossip = new Intent(
                        GuestPageActivity.this,
                        com.example.pwmmobile.GossipActivity.class
                ).putExtra("character", guest.getCharacter());

                GuestPageActivity.this.startActivity(gossip);
            }
        });

        bClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gossip = new Intent(
                        GuestPageActivity.this,
                        com.example.pwmmobile.ClueListActivity.class
                ).putExtra("guest", true);

                GuestPageActivity.this.startActivity(gossip);
            }
        });
    }
}
