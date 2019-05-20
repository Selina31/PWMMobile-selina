package com.example.pwmmobile;

import android.app.Activity;
import android.content.Intent;

public class Guest {

    private User user;
    private Character character;
    private int RSVP;
    private String code;
    private String email;

    public static final int CAN_ATTEND = 1;
    public static final int CANNOT_ATTEND = 2;
    public static final int NO_RESPONSE = 3;

    public Guest (String email) {
        RSVP = NO_RESPONSE;
        this.email = email;
    }


    /////////////////
    //getters/setters
    /////////////////

    public String getEmail() { return email; }
    public User getUser() { return user; }
    public Character getCharacter() { return character; }
    public int getRSVP() { return RSVP; }
    public String getCode() { return code; }

    public void setEmail(String email) { this.email = email; }
    public void setUser(User user) { this.user = user; }
    public void setCharacter(Character character) { this.character = character; }
    public void setRSVP(int RSVP) { this.RSVP = RSVP; }
    public void setCode(String code) { this.code = code; }

    /**
     * send an invite to this guest. call after guest is added to guest list
     * @return -1 if failed, 0 if successful
     */
    public int invite(Activity context, String code) {
        Party party = Globals.currParty;
        String text = "You're invited to " + party.getName() + " on " + party.getDate() + "!\n"
                + "RSVP to this email address.\n"
                + "Alternatively, you can download the 'Playing with Murder' app on the Google Play " +
                "Store to and use the code " + code + " to access the party to RSVP. You will then be " +
                "able to access information about the party both leading up to and during the event.\n"
                + "Hope to see you there!";
        String subject = "Party!";
        String[] TO = {email};

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, ""));
        return 0;
    }
}
