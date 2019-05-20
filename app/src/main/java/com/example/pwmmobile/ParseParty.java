package com.example.pwmmobile;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class ParseParty extends AsyncTask<String, Void, JSONObject> {

    public interface AsyncResponse {
        void partyResponse(Party party);
    }

    static Party newParty;

    public ParseParty.AsyncResponse delegate;

    public static void getPartyFromUrl(String url, ParseParty.AsyncResponse delegate, Party party) {
        ParseParty parseParty = new ParseParty();
        parseParty.delegate = delegate;
        newParty = party;

        parseParty.execute(url);
    }

    protected JSONObject doInBackground(String... url) {
        try {
            return readJsonFromUrl(url[0]);
        } catch (IOException e) {

        } catch (JSONException e) {

        }
        return null;
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URL _url = new URL(url);
        // TODO set some timeout
        InputStream is = _url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        } finally {
            is.close();
        }
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    protected void onPostExecute(JSONObject party) {

        String title = null;
        String creditHTML = null;
        String welcomeHTML = null;

        JSONArray sectionList = null;

        try {
            title = party.getString("title");
            creditHTML = party.getString("creditHTML");
            welcomeHTML = party.getString("welcomeHTML");
            sectionList = party.getJSONArray("sectionList");

            PartyHTML credit = new PartyHTML();
            credit.title = "Credit";
            credit.html = creditHTML;
            credit.type = "credit";
            //newParty.hostInfoSections.add(credit);

            PartyHTML welcome = new PartyHTML();
            welcome.title = "Welcome";
            welcome.html = welcomeHTML;
            welcome.type = "welcome";
            //newParty.hostInfoSections.add(welcome);

            for (int i = 0; i < sectionList.length(); i++) {
                //newParty.hostInfoSections.add(dfsSearch(sectionList.getJSONObject(i), "section"));
            }

            JSONArray characters = party.getJSONArray("characterList").getJSONArray(0);

            for (int i = 0; i < characters.length(); i++) {
                JSONObject character = (JSONObject) characters.get(i);
                String name = character.getString("character_name");
                String characterTitle = character.getString("character_title");
                String imageURL = character.getString("character_image");
                boolean required = character.getString("character_status").equals("Required");

                JSONObject acts = character.getJSONObject("acts");
                StringBuilder firstActInfo = new StringBuilder();
                StringBuilder secondActInfo = new StringBuilder();

                if (acts.has("act_1")) {
                    JSONArray act1 = acts.getJSONObject("act_1").optJSONArray("pages");
                    if (act1 != null) {
                        for (int j = 0; j < act1.length(); j++) {
                            firstActInfo.append("\n<h1>" + act1.getJSONObject(j).getString("title") + "</h1>\n");
                            firstActInfo.append(act1.getJSONObject(j).getString("HTML"));
                        }
                    }
                    JSONArray act2 = ((JSONObject) characters.get(++i)).getJSONObject("acts").getJSONObject("act_2").optJSONArray("pages");
                    if (act2 != null) {
                        for (int j = 0; j < act2.length(); j++) {
                            secondActInfo.append("\n<h1>" + act2.getJSONObject(j).getString("title") + "</h1>\n");
                            secondActInfo.append(act2.getJSONObject(j).getString("HTML"));
                        }
                    }
                } else {
                    JSONArray act2 = acts.getJSONObject("act_2").optJSONArray("pages");
                    if (act2 != null) {
                        for (int j = 0; j < act2.length(); j++) {
                            secondActInfo.append("\n<h1>" + act2.getJSONObject(j).getString("title") + "</h1>\n");
                            secondActInfo.append(act2.getJSONObject(j).getString("HTML"));
                        }
                    }
                }

                Character newCharacter = new Character(name, characterTitle, "",
                        firstActInfo.toString() + secondActInfo.toString(),
                        null, required);
                newParty.addCharacter(newCharacter);

            }


        } catch (Exception e) {
            Log.e("exception", e.getMessage());
            delegate.partyResponse(null);
        }

        newParty.setHost(Globals.user);
        newParty.setName(title);
        delegate.partyResponse(newParty);
    }

    private PartyHTML dfsSearch(JSONObject jsonObject, String type) {
        PartyHTML partyHTML = new PartyHTML();

        try {
            // Add partyHTML to party
            if (jsonObject.has("title")) {
                partyHTML.title = jsonObject.getString("title");
            }
            if (jsonObject.has("HTML")) {
                partyHTML.html = jsonObject.getString("HTML");
            }
            partyHTML.type = type;

            // Get Clues
            if (jsonObject.getString("title").equals("Solving the Mystery")) {
                String html = jsonObject.getString("HTML");
                String[] clues = html.split("<strong>");
                for (int i = 1; i < clues.length; i++) {
                    String[] clue = clues[i].split("</strong>: ");
                    if (clue.length == 2) {
                        newParty.addClue(new Clue(clue[0], "<ul><li>" + clue[1]));
                    }
                }
            }

            // Get task successors
            JSONArray successors;
            if (jsonObject.has("taskList")) {
                successors = jsonObject.getJSONArray("taskList");
                for (int i = 0; i < successors.length(); i++) {
                    partyHTML.children.add(dfsSearch(successors.getJSONObject(i), "task"));
                }

            }

            // Get chapter successors
            if (jsonObject.has("chapterList")) {
                successors = jsonObject.getJSONArray("chapterList");
                for (int i = 0; i < successors.length(); i++) {
                    partyHTML.children.add(dfsSearch(successors.getJSONObject(i), "chapter"));
                }

            }

            // Get page successors
            if (jsonObject.has("pageList")) {
                successors = jsonObject.getJSONArray("pageList");
                for (int i = 0; i < successors.length(); i++) {
                    partyHTML.children.add(dfsSearch(successors.getJSONObject(i), "page"));
                }

            }

            // Get section successors
            if (jsonObject.has("sectionList")) {
                successors = jsonObject.getJSONArray("sectionList");
                for (int i = 0; i < successors.length(); i++) {
                    partyHTML.children.add(dfsSearch(successors.getJSONObject(i), "section"));
                }

            }

        } catch (JSONException e) {
            Log.e("exception", e.getMessage());
        }

        return partyHTML;
    }

}
