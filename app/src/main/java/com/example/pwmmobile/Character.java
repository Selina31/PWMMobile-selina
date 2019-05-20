package com.example.pwmmobile;

import java.io.Serializable;
import java.util.List;

public class Character implements Serializable {

    private String name;
    private String role;
    private String whoYouAre;
    private String gettingIntoCharacter;
    private List<String> gossipAndObjectives;
    private boolean required;

    public Character() {}

    public Character(String name,
                     String role,
                     String whoYouAre,
                     String gettingIntoCharacter,
                     List<String> gossipAndObjectives,
                     boolean required) {
        this.name = name;
        this.role = role;
        this.whoYouAre = whoYouAre;
        this.gettingIntoCharacter = gettingIntoCharacter;
        this.gossipAndObjectives = gossipAndObjectives;
        this.required = required;
    }

    /////////////////
    //getters/setters
    /////////////////

    public String getName() {return name;}
    public String getRole() { return role; }
    public String getWhoYouAre() { return whoYouAre; }
    public String getGettingIntoCharacter() { return gettingIntoCharacter; }
    public List<String> getGossipAndObjectives() { return gossipAndObjectives; }
    public boolean isRequired() { return required; }

    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setWhoYouAre(String whoYouAre) { this.whoYouAre = whoYouAre; }
    public void setGettingIntoCharacter(String gettingIntoCharacter) {
        this.gettingIntoCharacter = gettingIntoCharacter;
    }
    public void setGossipAndObjectives(List<String> gossipAndObjectives) {
        this.gossipAndObjectives = gossipAndObjectives;
    }
    public void setRequired(boolean required) { this.required = required; }

    //For testing
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Name: " + name);
        ret.append("\nRequired: " + ""+required);
        ret.append("\nRole: " + role);
        return ret.toString();
    }
}
