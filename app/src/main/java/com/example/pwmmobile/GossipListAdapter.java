package com.example.pwmmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GossipListAdapter extends ArrayAdapter {
    // Activity
    private final Activity context;

    // title
    private final String[] titleArray;

    public GossipListAdapter(Activity context, String[] titleArray){

        super(context, R.layout.gossiplist_row , titleArray);

        this.context=context;
        this.titleArray = titleArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.gossiplist_row, null,true);

        // this code gets references to objects in the gossiplist_row.xml file
        TextView title  = rowView.findViewById(R.id.tTitle);

        // this code sets the values of the objects to values from the arrays
        title.setText(titleArray[position]);

        return rowView;

    }
}
