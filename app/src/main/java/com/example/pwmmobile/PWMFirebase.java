package com.example.pwmmobile;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * PWMFirebase will act as the central class for making reads and writes from the Firebase
 * database. Ideally, all actions dealing with the DatabaseReference will be handled here.
 */
public final class PWMFirebase {

    //Reference to the database
    public static DatabaseReference db;
    public static ValueEventListener currPartyListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            HashMap<String, Object> p = (HashMap<String, Object>)dataSnapshot.getValue();
            Party newParty = PWMFirebase.parseFBParty(p);
            Context.currentParty = newParty;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.getMessage());
        }
    };;

    public static void init() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            db = database.getReference();
    }

    //public static Party readParty(String id) { return null; }

    public static void writeParty(Party party) {

        String id = party.getId();
        String gameCode = id.substring(0, 3);
        String partyCode = id.substring(3);

        DatabaseReference pRef = db.child("Parties").child(gameCode).child(partyCode);

        pRef.setValue(party);
    }

    public static void listenToCurrentParty() {
        String id = Context.currentParty.getId();
        String gameCode = id.substring(0, 3);
        String partyCode = id.substring(3);
        db.child("Parties").child(gameCode).child(partyCode).addValueEventListener(currPartyListener);
    }

    public static void stopListeningToCurrentParty() {
        String id = Context.currentParty.getId();
        String gameCode = id.substring(0, 3);
        String partyCode = id.substring(3);
        db.child("Parties").child(gameCode).child(partyCode).removeEventListener(currPartyListener);
    }

    /*public static User readUser(String id) {
        init();
        DatabaseReference userRef = db.child("Users");

        //Having troubles retrieving a single object based off of a query

        return null;
    }

    public static void writeUser(User user) {
        init();
        db.child("Users").child(user.getID()).setValue(user);
    }*/

    public static Party parseFBParty(HashMap<String, Object> map) {
        Iterator it;

        Party newParty = new Party();

        newParty.setId((String)map.get("id"));

        long actL = 0;
        actL = (Long)map.get("act");
        int act = (int) actL;
        newParty.setAct(act);

        User host = parseFBUser((HashMap<String, Object>)map.get("host"));
        newParty.setHost(host);

        ArrayList<Character> characters = new ArrayList<Character>();
        ArrayList<HashMap<String, Object>> fbChar = (ArrayList<HashMap<String, Object>>)map.get("characters");
        it = fbChar.iterator();
        while(it.hasNext()) {
            characters.add(parseFBCharacter((HashMap<String, Object>)it.next()));
        }
        newParty.setCharacters(characters);

        ArrayList<Guest> guests = new ArrayList<Guest>();
        ArrayList<HashMap<String, Object>> fbGuest = (ArrayList<HashMap<String, Object>>)map.get("guestList");
        it = fbGuest.iterator();
        while(it.hasNext()) {
            guests.add(parseFBGuest((HashMap<String, Object>)it.next()));
        }
        newParty.setGuestList(guests);

        ArrayList<Clue> clues = new ArrayList<Clue>();
        ArrayList<HashMap<String, Object>> fbClue = (ArrayList<HashMap<String, Object>>)map.get("clues");
        it = fbClue.iterator();
        while (it.hasNext()) {
            clues.add(parseFBClue((HashMap<String, Object>)it.next()));
        }
        newParty.setClues(clues);
        return newParty;
    }

    public static User parseFBUser(HashMap<String, Object> map) {
        String email = (String)map.get("email");
        String id = (String)map.get("id");
        String displayName = (String)map.get("displayName");
        User newUser = new User(id, email, displayName);
        return newUser;
    }

    public static Character parseFBCharacter(HashMap<String, Object> map) {
        String name = (String)map.get("name");
        String firstActInfo = (String)map.get("firstActInfo");
        String secondActInfo = (String)map.get("secondActInfo");
        String thirdActInfo = (String)map.get("thirdActInfo");
        boolean required = (boolean)map.get("required");
        return new Character(name,
                firstActInfo,
                secondActInfo,
                thirdActInfo,
                null,
                required);
    }

    public static Guest parseFBGuest(HashMap<String, Object> map) {
        String email = (String)map.get("email");
        long rsvpL = (Long)map.get("rsvp");
        int rsvp = (int) rsvpL;
        Guest newGuest = new Guest(email);
        newGuest.setRSVP(rsvp);
        return newGuest;
    }

    public static Clue parseFBClue(HashMap<String, Object> map) {
        String name = (String)map.get("name");
        String information = (String)map.get("information");
        boolean found = (boolean)map.get("found");
        Clue newClue = new Clue(name, information);
        newClue.setFound(found);
        return newClue;
    }
}
