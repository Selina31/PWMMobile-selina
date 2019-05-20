package com.example.pwmmobile;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton;

public class ClueListAdapter extends ArrayAdapter {

    // Activity
    private final Activity context;

    // title
    private final Clue[] clueArray;

    // from guest list?
    private final boolean guest;

    public ClueListAdapter(Activity context, Clue[] clueArray, boolean guest){

        super(context, R.layout.cluelist_row , clueArray);

        this.context=context;
        this.clueArray = clueArray;
        this.guest = guest;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cluelist_row, null,true);

        //this code gets references to objects in the cluelist_row.xml file
        TextView clueTitle  = rowView.findViewById(R.id.tClueTitle);
        TextView clueDescription = rowView.findViewById(R.id.tClueDescription);
        Switch sClueSwitch = rowView.findViewById(R.id.sClueSwitch);

        if (guest) {
            sClueSwitch.setVisibility(View.GONE);
        } else {
            sClueSwitch.setVisibility(View.VISIBLE);
        }

        //this code sets the values of the objects to values from the arrays
        clueTitle.setText(Html.fromHtml(clueArray[position].getName()));
        clueDescription.setText(Html.fromHtml(clueArray[position].getInformation()));
        sClueSwitch.setChecked(clueArray[position].isFound());
        sClueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clueArray[position].setFound(isChecked);
            }
        });

        return rowView;

    }

}
