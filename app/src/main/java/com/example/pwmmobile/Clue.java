package com.example.pwmmobile;

public class Clue {

    private String name;
    private boolean found;
    private String information;

    public Clue() { }

    public Clue(String name, String information) {
        this.name = name;
        this.information = information;
        found = false;
    }

    /////////////////
    //getters/setters
    /////////////////


    public String getName() { return name; }
    public String getInformation() { return information; }
    public boolean isFound() { return found; }

    public void setName(String name) { this.name = name; }
    public void setFound(boolean found) { this.found = found; }
    public void setInformation(String information) { this.information = information; }

    // For testing
    @Override
    public String toString() {
        return name + ": " + information;
    }

}
