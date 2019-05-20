package com.example.pwmmobile;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class GossipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gossip);

        Character character = (Character) getIntent().getSerializableExtra("character");

        final ListView lGossipList = findViewById(R.id.lGossipList);

        List<String> gossipObj = character.getGossipAndObjectives();
        String[] gossipObjArray = gossipObj.toArray(new String[gossipObj.size()]);

        GossipListAdapter adapter = new GossipListAdapter(this, gossipObjArray);
        lGossipList.setAdapter(adapter);
    }

}
