package com.example.pwmmobile;
import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.List;
import android.util.Pair;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class Party implements ParseParty.AsyncResponse {


    private String id;
    private User host;
    private List<Guest> guestList;
    private List<Character> characters;
    private List<Clue> clues;
    private List<PartyHTML> hostInfoSections;

    /*
    ACT 0: Story opened, guestlist not published
    ACT 1: Guestlist published, murder not yet occurred
    ACT 2: Murder occurred, voting not yet begun
    ACT 3: Voting begun, voting not yet closed
    ACT 4: Voting closed, recap generated.
     */
    private int act;

    private String name;
    private String menu;
    private List<String> codes;
    private String currGuest;
    private Date date;

    public Party() {
        guestList = new ArrayList<Guest>();
        characters = new ArrayList<Character>();
        clues = new ArrayList<Clue>();
        codes = new ArrayList<String>();
        act = 0;
        currGuest = "0";
    }

    public Party (String url) {
        guestList = new ArrayList<Guest>();
        characters = new ArrayList<Character>();
        clues = new ArrayList<Clue>();
        codes = new ArrayList<String>();
        act = 0;
        currGuest = "0";

        parseJSON(url, this);
        hostInfoSections = new ArrayList<>();

    }

    /////////////////
    //setters/getters
    /////////////////
    public void setId(String id) { this.id = id; }
    public void setHost(User host) {this.host = host; }
    public void setGuestList(List guestList) { this.guestList = guestList;}
    public void setCharacters(List characters) { this.characters = characters;}
    public void setClues(List clues) { this.clues = clues; }
    public void setName(String name) { this.name = name; }
    public void setMenu(String menu) { this.menu = menu; }
    public void setAct(int act) { this.act = act; }
    public void setCodes(List codes) { this.codes = codes; }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() { return id; }
    public User getHost() { return host; }
    public List getGuestList() { return guestList;}
    public List getCharacters() { return characters;}
    public List getClues() { return clues; }
    public String getName() { return name; }
    public String getMenu() { return menu; }
    public int getAct() { return act; }
    public List getCodes() { return codes; }
    public Date getDate() { return date; }

    // Add a character
    public void addCharacter(Character character) {
        characters.add(character);
    }

    // Add a clue
    public void addClue(Clue clue) {
        clues.add(clue);
    }

    /**
     * Populate these fields with information from the story JSON: characters
     * clues, name, menu
     */

    private void parseJSON(String url, Party party) {
        ParseParty.getPartyFromUrl("http://www.playswithmurder.com/mysteries/json.php?mystery_id=3",
                this, party);
    }


    /*
     * This is called when the asynchronous task is finished
     * It sets the global party variable and also prints out
     * info for testing
     */
    public void partyResponse(Party party) {
        if (party == null) {
            Log.e("party", "null");
        } else {
            Globals.currParty = party;

            // For testing
            System.out.println(party);
        }
    }

    /**
     * proceed to the next act
     */
    public void nextAct() {
        act++;
    }

    /**
     * add a new guest to the guest list. send an email invitation to this
     * guest
     *
     * @param email email address of the new guest
     * @return -1 if guest with that address already exists. 0 if successful
     */
    public int addGuest(String email, Activity context) {
        Guest newGuest = new Guest(email);
        for (Guest temp : guestList) {
            if (temp.getEmail().equals(email)) {
                return -1;
            }
        }

        // TODO get code
        String code = currGuest;
        codes.add(code);
        guestList.add(newGuest);
        newGuest.invite(context, code);

        int currGuestI = Integer.parseInt(currGuest);
        currGuestI += 1;
        currGuest = Integer.toString(currGuestI);
        return 0;
    }

    // For testing

    @Override
    public String toString() {
        StringBuilder partyInfo = new StringBuilder("Title: " + name);

        partyInfo.append("\n" + "Host Info:");
        for (int i = 0; i < hostInfoSections.size(); i++) {
            partyInfo.append("\n" + hostInfoSections.get(i).toString());
        }

        partyInfo.append("\n" + "Characters:");
        for (int i = 0; i < characters.size(); i++) {
            partyInfo.append("\n" + characters.get(i).toString());
        }

        partyInfo.append("\n" + "Clues:");
        for (int i = 0; i < clues.size(); i++) {
            partyInfo.append("\n" + clues.get(i).toString());
        }
        return partyInfo.toString();
    }

}


class PartyHTML {
    public PartyHTML() {
        children = new ArrayList<>();
    }

    String title;
    String html;
    String type;
    ArrayList<PartyHTML> children;

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(title + ": " + html);
        for (int i = 0 ; i < children.size(); i++) {
            ret.append("\n" + children.get(i).toString());
        }
        return ret.toString();
    }
}
