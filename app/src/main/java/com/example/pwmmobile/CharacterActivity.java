package com.example.pwmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        JSONArray CharAry = CoverActivity.getCurAry();
        Character character = (Character) getIntent().getSerializableExtra("character");
        Intent i = getIntent();
        int position;
        JSONObject data = null;

        JSONObject defaultAct = null;
        final TextView tCharacterName = findViewById(R.id.tCharacterName);
//        tCharacterName.setText(character.getName());
        final TextView tCharacterRole = findViewById(R.id.tCharacterRole);
//        tCharacterRole.setText(character.getRole());
        final TextView tWhoYouAre = findViewById(R.id.tWhoYouAre);
//        tWhoYouAre.setText(character.getWhoYouAre());
        final TextView tGettingIntoCharacter = findViewById(R.id.tGettingIntoCharacter);
//        tGettingIntoCharacter.setText(character.getGettingIntoCharacter());

        if (i.hasExtra("index")) {
            position = Integer.parseInt(i.getStringExtra("index"));
            try {
                data = CharAry.getJSONObject(position);
                if (data.getJSONObject("acts").has("act_1"))
                    defaultAct = data.getJSONObject("acts").getJSONObject("act_1");
                else
                    defaultAct = data.getJSONObject("acts").getJSONObject("act_2");
                tCharacterName.setText(data.getString("character_name"));
                tCharacterRole.setText(data.getString("character_title"));
                tWhoYouAre.setText(defaultAct.getJSONArray("pages").getJSONObject(0).getString("HTML"));
                tGettingIntoCharacter.setText(defaultAct.getJSONArray("pages").getJSONObject(1).getString("HTML"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            tCharacterName.setText(character.getName());
            tCharacterRole.setText(character.getRole());
            tWhoYouAre.setText(character.getWhoYouAre());
            tGettingIntoCharacter.setText(character.getGettingIntoCharacter());
        }

        //JSONObject defaultAct = null;
        /* final TextView tCharacterName = findViewById(R.id.tCharacterName);
        tCharacterName.setText(character.getName());
        final TextView tCharacterRole = findViewById(R.id.tCharacterRole);
        tCharacterRole.setText(character.getRole());
        final TextView tWhoYouAre = findViewById(R.id.tWhoYouAre);
        tWhoYouAre.setText(character.getWhoYouAre());
        final TextView tGettingIntoCharacter = findViewById(R.id.tGettingIntoCharacter);
        tGettingIntoCharacter.setText(character.getGettingIntoCharacter()); */


    }
}

