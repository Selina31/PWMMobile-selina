package com.example.pwmmobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.pwmmobile.R;


public class BookListAdapter extends ArrayAdapter {

    // Activity
    private final Activity context;

    // title
    private ArrayList<HashMap<String,String>> contentList = null;

    public BookListAdapter(Activity context, ArrayList<HashMap<String,String>> contentList){

        super(context, R.layout.booklist_row , contentList);
        this.context=context;
        this.contentList = contentList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.booklist_row, null,true);

        //this code gets references to objects in the booklist_row.xml file
        TextView header  = rowView.findViewById(R.id.tHeader);
        TextView subheader  = rowView.findViewById(R.id.tSubheader);

        //this code sets the values of the objects to values from the arrays
        header.setText(contentList.get(position).get("header"));
        subheader.setText(contentList.get(position).get("subheader"));

        return rowView;

    }

}
