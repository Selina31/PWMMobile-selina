package com.example.pwmmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.ListView;

import com.example.pwmmobile.R;

public class ClueListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_list);

        //CreateDummyParty create = new CreateDummyParty();
        //Party dParty = create.createDummyParty();
        /* Code to retrieve clue list from database */
        Party dParty = Globals.currParty;
        /* ****************************************************** */

        Clue[] clueArray;
        ClueListAdapter adapter;
        if (getIntent().getBooleanExtra("guest", false)) {
            ArrayList<Clue> foundClues = (ArrayList<Clue>) ((ArrayList<Clue>) dParty.getClues()).clone();
            int i = 0;
            while (i < foundClues.size()) {
                if (!foundClues.get(i).isFound()) {
                    foundClues.remove((i));
                } else {
                    i++;
                }
            }
            clueArray = (Clue[]) (foundClues.toArray(new Clue[foundClues.size()]));
            adapter = new ClueListAdapter(this,clueArray,true);
        } else {
            clueArray = (Clue[])(dParty.getClues().toArray(new Clue[dParty.getClues().size()]));
            adapter = new ClueListAdapter(this,clueArray,false);
        }

        listView = findViewById(R.id.lClueList);
        listView.setAdapter(adapter);
    }
}
