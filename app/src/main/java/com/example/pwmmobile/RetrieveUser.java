package com.example.pwmmobile;

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
import android.os.AsyncTask;

/*
 * This class attempts to retrieve a user object from a url.
 * It does this as an asynchronous task in its own thread.
 */
class RetrieveUser extends AsyncTask<String, Void, JSONObject> {

    public interface AsyncResponse {
        void loginResponse(User user);
    }

    public AsyncResponse delegate;

    /*
     * The following three methods are used together to get a JSONObject from the url string
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URL _url = new URL(url);
        // TODO set some timeout
        InputStream is = null;
        try {
            is = _url.openStream();
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

    protected JSONObject doInBackground(String... url) {
        try {
            return readJsonFromUrl(url[0]);
        } catch (IOException e) {

        } catch (JSONException e) {

        }
        return null;
    }

    /*
     * This method parses the JSONObject and calls the loginResponse in the activity that called
     * User.login().
     * If the login was successful, it passes a new User object, else it passes null.
     */
    protected void onPostExecute(JSONObject json) {
        User newUser = null;

        try {
            JSONObject user = json.getJSONObject("user");
            String ID = user.getString("id");
            String displayname = user.getString("name");
            String email = user.getString("email");
            newUser = new User(ID, email, displayname);
            JSONArray parties = user.getJSONArray("mystery");
            for (int i = 0; i < parties.length(); i++) {
                newUser.addPurchasedParty(
                        parties.getJSONObject(i).getString("name"),
                        parties.getJSONObject(i).getString("json_url"));
            }
        } catch (JSONException e) {
            Log.e("exception", e.getMessage());
            delegate.loginResponse(null);
        }

        delegate.loginResponse(newUser);

    }
}