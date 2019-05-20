package com.example.pwmmobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Pair;

import com.example.pwmmobile.R;

import java.util.ArrayList;
import java.util.Date;

public class PartyListAdapter extends ArrayAdapter {

    // Activity
    private final Activity context;

    // gameNames
    private final ArrayList<String> gameIDArray;

    // dates
    private final Date[] datesArray;

    // host or not
    private final Boolean[] hostArray;

    public PartyListAdapter(Activity context, ArrayList<String> gameIDArray, Date[] datesArray, Boolean[] hostArray){

        super(context, R.layout.partylist_row , gameIDArray);

        this.context=context;
        this.gameIDArray = gameIDArray;
        this.datesArray = datesArray;
        this.hostArray = hostArray;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.partylist_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView gameNameID = (TextView) rowView.findViewById(R.id.gameNameID);
        TextView partyDate = (TextView) rowView.findViewById(R.id.partyDate);
        TextView hOrG = (TextView) rowView.findViewById(R.id.hOrG);

        //this code sets the values of the objects to values from the arrays
        gameNameID.setText(gameIDArray.get(position));
        if (datesArray != null)
            partyDate.setText(datesArray[position].toString());
        if (hostArray != null) {
            if (hostArray[position])
                hOrG.setText("Host");
            else
                hOrG.setText("Guest");
        }

        return rowView;

    }

}
