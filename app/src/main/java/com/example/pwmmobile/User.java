package com.example.pwmmobile;

import java.util.ArrayList;
import android.util.Pair;

/*
 * This class contains the information for a user account with PWM
 */
class User {

    private String ID;
    private ArrayList<Party> currParties;
    private ArrayList<Pair<String, String>> purchasedParties;
    private String email;
    private String displayname;

    public User(String ID, String email, String displayname) {
        this.ID = ID;
        this.email = email;
        this.displayname = displayname;
        currParties = new ArrayList<>();
        purchasedParties = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }


    public void setID(String id) { this.ID = id; }

    public ArrayList<Party> getCurrParties() {
        return currParties;
    }

    public void addCurrParty(Party party) {
        currParties.add(party);
    }

    public ArrayList<Pair<String, String>> getPurchasedParties() {
        return purchasedParties;
    }

    public void addPurchasedParty(String name, String url) {
        purchasedParties.add(new Pair<String, String>(name, url));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayname;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
    }

    public static void login(String email, String password, RetrieveUser.AsyncResponse delegate) {
        RetrieveUser retrieveUser = new RetrieveUser();
        retrieveUser.delegate = delegate;

        String url = "http://www.playswithmurder.com/mysteries/authenticate.php?email=" + email;
        retrieveUser.execute(url);
        //retrieveUser.execute("http://www.playswithmurder.com/mysteries/authenticate.php?email=name@email.com");
    }

}
