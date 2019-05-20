package com.example.pwmmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView.*;
import android.os.AsyncTask;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pwmmobile.R;

public class BookActivity extends AppCompatActivity implements OnItemClickListener {

    ListView listView;
    ArrayList<HashMap<String, String>> contentList;
    JSONArray localJsonAry;
    private void loadNewContent(JSONArray jAry) {
        try {
            // looping through All
            for (int i = 0; i < jAry.length(); i++) {
                JSONObject c = jAry.getJSONObject(i);

                HashMap<String, String> content = new HashMap<>();
                if(c.has("title")) {
                    String id = c.getString("id");
                    String title = c.getString("title");
                    content.put("header", title);
                    content.put("subheader", " ");
                    content.put("id", id);
                    contentList.add(content);
                }
                else if (c.has("character_name")){
                    String character_name =  c.getString("character_name");
                    String character_title = "";
                    if (c.getString("character_title")!="null")
                        character_title =  c.getString("character_title");
                    content.put("header", character_name);
                    content.put("position", Integer.toString(i));
                    content.put("subheader", character_title);
                    contentList.add(content);
                }


            }
        } catch (final JSONException e) {
            Log.e(null, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        BookListAdapter adapter = new BookListAdapter(BookActivity.this,contentList);
        listView = findViewById(R.id.lBookList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        contentList = new ArrayList<>();
        loadNewContent(CoverActivity.getCurAry());
        localJsonAry = CoverActivity.getCurAry();
        listView = findViewById(R.id.lBookList);
        listView.setOnItemClickListener(this);
        BookListAdapter adapter = new BookListAdapter(BookActivity.this,contentList);
        listView.setAdapter(adapter);

    }

    protected void onResume() {
        super.onResume();
        CoverActivity.setCurAry(localJsonAry);
    }
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        int charListPos = 0;
        JSONObject defaultAct = null;
        try {
            JSONObject target = CoverActivity.getCurAry().getJSONObject(position);
            if (CoverActivity.getCurAry().getJSONObject(0).has("character_name")) {
                charListPos = Integer.parseInt(contentList.get(position).get("position"));
                target = CoverActivity.getCurAry().getJSONObject(charListPos);
                defaultAct = target.getJSONObject("acts");

            }
            if (target.has("chapterList")) { // sectionList
                CoverActivity.setCurAry(target.getJSONArray("chapterList"));
                startActivity(getIntent());
            } else if (target.has("pageList")) {
                CoverActivity.setCurAry(target.getJSONArray("pageList"));
                startActivity(getIntent());
            } else if (target.has("type") && target.get("type").equals("readme")) {
                Intent text = new Intent(BookActivity.this, TextActivity.class);
                text.putExtra("text", target.getString("HTML"));
                startActivity(text);
            } else if (target.has("character_name")) {
                Intent text = new Intent(BookActivity.this, CharacterActivity.class);
                text.putExtra("index",contentList.get(position).get("position"));
                startActivity(text);
            }
        } catch (JSONException e) {
            Log.e(null, "Json parsing error: " + e.getMessage());
        }
    }

}
