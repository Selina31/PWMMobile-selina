package com.example.pwmmobile;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testUser() {
        new RetrieveUser().execute("test");
        new User(0, "", "");
        new UserTestMainActivity();
    }

    @Test
    public void testBookActivity() {
        BookActivity b = new BookActivity();
        new BookListAdapter(b, null);
    }

    @Test
    public void testCharacter() {
        Character c = new Character("", "", "", "", null, false);
        c.setName("name");
        c.setRole("role");
        c.setWhoYouAre("whoYouAre");
        c.setGettingIntoCharacter("gettingIntoCharacter");
        c.setGossipAndObjectives(new String[]{"gossip"});
        c.setRequired(true);
        assertEquals("name", c.getName());
        assertEquals("role", c.getRole());
        assertEquals("whoYouAre", c.getWhoYouAre());
        assertEquals("gettingIntoCharacter", c.getGettingIntoCharacter());
        assertEquals("gossip", c.getGossipAndObjectives()[0]);
        assertEquals(true, c.isRequired());
        new CharacterActivity();
    }

    @Test
    public void testClue() {
        Clue c = new Clue("","");
        new ClueFoundActivity();
        ClueListActivity clA = new ClueListActivity();
        new ClueListAdapter(clA, null);
    }

    @Test
    public void testDummy() {
        Party p = new CreateDummyParty().createDummyParty();
        List<Character> characters = p.getCharacters();
        List<Clue> clues = p.getClues();
        assertEquals(16, characters.size());
        assertEquals(4, clues.size());
        assertEquals(2, p.getAct());

    }

    @Test
    public void testGossip() {
        GossipActivity g = new GossipActivity();
        new GossipListAdapter(g, null);
    }

    @Test
    public void testGuest() {
        Guest g = new Guest("");
        GuestListActivity glA = new GuestListActivity();
        new GuestListAdapter(glA, null, null);
        new GuestPageActivity();
        new HostPageActivity();
    }

    @Test
    public void testMain() {
        new MainActivity();
    }

    @Test
    public void testParty() {
        Party p = new Party();
        PartyDate pd = new PartyDate("","","");
        PartyListActivity plA = new PartyListActivity();
        new PartyListAdapter(plA, null, null, null);
    }

    @Test
    public void testPWM() {
        new PWMFirebase();
    }
}