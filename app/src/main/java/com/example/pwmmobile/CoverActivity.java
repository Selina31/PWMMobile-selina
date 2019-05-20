package com.example.pwmmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CoverActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> contentList;
    String title;
    String welcome;
    String credits;
    JSONObject jsonObj;
    private static JSONArray curAry;
    public static JSONArray getCurAry() {
        return curAry;
    }
    public static void setCurAry(JSONArray newAry) {
        curAry = newAry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        new GetContent().execute();

    }

    private class GetContent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(CoverActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }
        private String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getAssets().open("sample.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr = loadJSONFromAsset();
            contentList = new ArrayList<>();
            Log.e(null, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    jsonObj = new JSONObject(jsonStr);
                    // adding each child node to HashMap key => value
                    title = jsonObj.getString("title");
                    welcome = jsonObj.getString("welcomeHTML");
                    credits = jsonObj.getString("creditHTML");
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
            } else {
                Log.e(null, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TextView tTitle  = findViewById(R.id.tHeader);
            TextView tWelcome = findViewById(R.id.tWelcome);

            tTitle.setText(title);
            tWelcome.setText(welcome);final Button bSections = findViewById(R.id.bSections);
            final Button bCharacters = findViewById(R.id.bCharacters);
            final Button bCredits = findViewById(R.id.bCredits);

            bSections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        curAry = jsonObj.getJSONArray("sectionList");
                    } catch (final JSONException e) {
//                    Log.e(null, "Json parsing error: " + e.getMessage());
                    }
                    Intent book = new Intent(CoverActivity.this, BookActivity.class);
                    CoverActivity.this.startActivity(book);
                }
            });
            bCharacters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        curAry = jsonObj.getJSONArray("characterList").getJSONArray(0);
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
                    Intent book = new Intent(CoverActivity.this, BookActivity.class);
                    CoverActivity.this.startActivity(book);
                }
            });
            bCredits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent text = new Intent(CoverActivity.this, TextActivity.class);
                    text.putExtra("text",credits);
                    startActivity(text);
                }
            });

        }
    }
}
