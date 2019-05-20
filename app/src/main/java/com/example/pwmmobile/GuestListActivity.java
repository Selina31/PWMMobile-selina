package com.example.pwmmobile;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.view.View;
import com.example.pwmmobile.R;

public class GuestListActivity extends AppCompatActivity {

    ListView listView;
    String guestEmail;
    Activity context;
    View currView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);
        context = this;

        /* Code to retrieve name and character list from database */
        //CreateDummyParty create = new CreateDummyParty();
        //final Party dParty = create.createDummyParty();
        final Party dParty = Globals.currParty;
        final Guest[] guestArray = (Guest[])dParty.getGuestList().toArray(new Guest[dParty.getGuestList().size()]);
        //System.out.println(guestArray[0].getUser().getDisplayName());
        final Character[] characterArray = (Character[])dParty.getCharacters().toArray(new Character[dParty.getGuestList().size()]);
        /* ****************************************************** */

        GuestListAdapter adapter = new GuestListAdapter(this,guestArray,characterArray);
        listView = findViewById(R.id.lGuestList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (currView != null && view != currView) {
                    currView.findViewById(R.id.bRemove).setVisibility(View.GONE);
                }
                currView = view;
                Button button = view.findViewById(R.id.bRemove);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dParty.getGuestList().remove(i);
                        listView.setAdapter(new GuestListAdapter(context,
                                (Guest[])dParty.getGuestList().toArray(new Guest[dParty.getGuestList().size()]),
                                characterArray));

                    }
                });
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currView != null && view != currView) {
                    currView.findViewById(R.id.bRemove).setVisibility(View.GONE);
                }
            }
        });

        EditText email = findViewById(R.id.etGuestEmail);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                guestEmail = editable.toString();
            }
        });

        Button invite = findViewById(R.id.bInvite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dParty.addGuest(guestEmail, context);
                listView.setAdapter(new GuestListAdapter(context,
                        (Guest[])dParty.getGuestList().toArray(new Guest[dParty.getGuestList().size()]),
                        characterArray));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EditText) findViewById(R.id.etGuestEmail)).setText("");
    }

}
