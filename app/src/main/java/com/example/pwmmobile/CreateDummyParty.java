package com.example.pwmmobile;

import java.util.ArrayList;

public class CreateDummyParty {

    private String[] chrNames = { "Francis Fisk",
            "Richard den Waldern",
            "Chester Fisk",
            "Jonathon Fisk",
            "Zachary Wellington",
            "Sam Lily",
            "David Kreig",
            "Grayson Kreig",
            "Jesse Corrington",
            "Isaac Corrington",
            "Alexei Siranov",
            "Irene den Waldern",
            "Rose den Waldern",
            "Jo Chase",
            "Candice Wellington",
            "Ashley Lebois"};

    private String[] clueNames = { "Fisk's Safe",
            "Fisk's Phone",
            "A Bloody Knife",
            "Fisk's Dead Body"};

    public Party createDummyParty() {
        User host = new User("1", "testuser1@test.com", "TestUser1");

        ArrayList<Character> characters = new ArrayList<>();
        for (int j = 0; j < chrNames.length; j++) {
            boolean required;

            if (j < 10) {
                required = true;
            } else {
                required = false;
            }

            ArrayList<String> gossip = new ArrayList<>();
            gossip.add("This is gossip on character 1");
            gossip.add("This is gossip on character 2");

            Character character = new Character(
                    chrNames[j],
                    "Character " + j + " role",
                    "Character " + j + " who you are information.",
                    "Character " + j + " getting into character information.",
                    gossip,
                    required
            );

            characters.add(character);
        }

        ArrayList<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Guest guest = new Guest("guest" + i + "@test.com");
            guest.setCharacter(characters.get(i));
            guest.setUser(new User(""+i, guest.getEmail(),"guest" + i));
            guests.add(guest);
        }

        ArrayList<Clue> clues = new ArrayList<>();
        for (int k = 0; k < clueNames.length; k++) {
            boolean found;

            if (k < 3) {
                found = true;
            } else {
                found = false;
            }

            Clue clue = new Clue(clueNames[k],"Clue " + k + " information.");
            clue.setFound(found);

            clues.add(clue);
        }

        Party party = new Party("url");

        party.setCharacters(characters);
        party.setClues(clues);
        party.setGuestList(guests);
        party.setAct(2);
        party.setHost(host);

        return party;
    }
}
