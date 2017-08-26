package dao;

import models.Entry;
import models.PersonalNotes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oPersonalNotesDaoTest {
    private Connection conn;
    private Sql2oEntryDao entryDao;
    private Sql2oPersonalNotesDao personalNotesDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/encyclopaedia.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        personalNotesDao = new Sql2oPersonalNotesDao(sql2o);
        entryDao = new Sql2oEntryDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addPersonalNotesSetsId() throws Exception {
        PersonalNotes testPersonalNotes = setupNewPersonalNotes();
        personalNotesDao.add(testPersonalNotes);
        assertEquals(1, testPersonalNotes.getId());
    }

    @Test
    public void existingPersonalNotesCanBeFoundById() throws Exception {
        PersonalNotes testNotes = setupNewPersonalNotes();
        personalNotesDao.add(testNotes);
        PersonalNotes foundPersonalNotes = personalNotesDao.findById(testNotes.getId());
        assertEquals(testNotes, foundPersonalNotes);
    }

    @Test
    public void returnAllPersonalNotes() throws Exception {
        PersonalNotes testNotes = setupNewPersonalNotes();
        personalNotesDao.add(testNotes);
        assertEquals(1, personalNotesDao.getAll().size());
    }

    @Test
    public void noPersonalNotesReturnsEmptyList() throws Exception {
        assertEquals(0, personalNotesDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectPersonalNotes() throws Exception {
        PersonalNotes testNotes = setupNewPersonalNotes();
        personalNotesDao.add(testNotes);
        personalNotesDao.deletePersonalNotesById(testNotes.getId());
        assertEquals(0, personalNotesDao.getAll().size());
    }

    @Test
    public void clearAllPersonalNotes() throws Exception {
        PersonalNotes personalNotes = setupNewPersonalNotes();
        PersonalNotes personalNotes2 = setupAltPersonalNotes();
        personalNotesDao.add(personalNotes);
        personalNotesDao.add(personalNotes2);
        int daoSize = personalNotesDao.getAll().size();
        personalNotesDao.clearAllPersonalNotes();
        assertTrue(daoSize > 0 && daoSize >personalNotesDao.getAll().size());

    }

    @Test
    public void update() throws Exception {
        PersonalNotes personalNotes = setupNewPersonalNotes();
        personalNotesDao.add(personalNotes);
        personalNotesDao.update("Willie Wonka", "This chicken had feathers", personalNotes.getId());
        PersonalNotes updatedPersonalNotes = personalNotesDao.findById(personalNotes.getId());
        assertEquals("Willie Wonka", updatedPersonalNotes.getWrittenBy());
        assertEquals("This chicken had feathers", updatedPersonalNotes.getContent());
    }



//    @Test
//    public void getAllPersonalNotesForAnEntry() throws Exception {
//        PersonalNotes testPersonalNotes = setupNewPersonalNotes();
//        personalNotesDao.add(testPersonalNotes);
//        PersonalNotes testOtherPersonalNotes = setupAltPersonalNotes();
//        personalNotesDao.add(testOtherPersonalNotes);
//        Entry testEntry = setupEntry();
//        entryDao.add(testEntry);
//        entryDao.addPersonalNotesToEntry(testPersonalNotes, testEntry);
//        entryDao.addPersonalNotesToEntry(testOtherPersonalNotes, testEntry);
//        PersonalNotes[] personalNotes = {testPersonalNotes, testOtherPersonalNotes};
//        assertEquals(entryDao.getAllPersonalNotesForAnEntry(testEntry.getEntryId()), Arrays.asList(personalNotes));
//    }

    //helpers
    public PersonalNotes setupNewPersonalNotes() {
        return new PersonalNotes("Animalia", "Pat Boone", "Note Contents");
    }

    public PersonalNotes setupAltPersonalNotes() {
        return new PersonalNotes("Plantae", "Pat Boone", "Plantae Note Contents");
    }

    public Entry setupEntry() {
        return new Entry("Protozoa", "Amoebae", "Dictyozoa");
//    }
    }
}