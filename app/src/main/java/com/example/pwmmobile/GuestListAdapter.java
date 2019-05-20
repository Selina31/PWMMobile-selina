package com.example.pwmmobile;

import android.app.Activity;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pwmmobile.R;


public class GuestListAdapter extends ArrayAdapter {

    // Activity
    private final Activity context;

    // title
    private final Guest[] guestArray;
    private final Character[] characterArray;

    public GuestListAdapter(Activity context, Guest[] guestArray, Character[] characterArray){

        super(context, R.layout.guestlist_row , guestArray);

        this.context=context;
        this.guestArray = guestArray;
        this.characterArray = characterArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.guestlist_row, null,true);

        //this code gets references to objects in the booklist_row.xml file
        TextView name  = rowView.findViewById(R.id.tName);
        TextView character = rowView.findViewById(R.id.tCharacterID);
        TextView emailText = rowView.findViewById(R.id.tEmail);

        //this code sets the values of the objects to values from the arrays
        String displayName = null;
        String guestName = null;
        String email = null;
        if (guestArray[position] != null && guestArray[position].getUser() != null) {
            displayName = guestArray[position].getUser().getDisplayName();
        }

        if (guestArray[position].getCharacter() != null) {
            guestName = guestArray[position].getCharacter().getName();
        }

        if (guestArray[position].getEmail() != null) {
            email = guestArray[position].getEmail();
        }

        if (displayName != null) {
            name.setText(displayName);
        } else {
            name.setText("null");
        }

        if (guestName != null) {
            character.setText(guestName);
        } else {
            character.setText("null");
        }

        if (email != null) {
            emailText.setText(email);
        } else {
            character.setText("null");
        }

        return rowView;

    }

}
