
package com.example.pwmmobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Pair;

import com.example.pwmmobile.R;

import java.util.ArrayList;
import java.util.Date;

public class PartyListActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);

        User user = Globals.user;

        ListView currPartyList = findViewById(R.id.currPartyList);

        ListView purchasedPartiesList = findViewById(R.id.purchasedPartiesList);
        final ArrayList<Pair<String, String>> purchasedParties = user.getPurchasedParties();
        ArrayList<String> _purchasedParties = new ArrayList<String>();
        for (int i = 0; i < purchasedParties.size(); i++) {
            _purchasedParties.add(purchasedParties.get(i).first);
        }
        purchasedPartiesList.setAdapter(new PartyListAdapter(this, _purchasedParties,
                null, null));
        purchasedPartiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO create new party
                //Globals.currParty = new Party(purchasedParties.get(i).second);
                CreateDummyParty create = new CreateDummyParty();
                Globals.currParty = create.createDummyParty();
                Intent intent = new Intent(context, HostPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
