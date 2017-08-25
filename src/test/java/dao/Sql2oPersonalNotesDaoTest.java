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
        PersonalNotes review = setupNewPersonalNotes();
        personalNotesDao.add(review);
        PersonalNotes foundPersonalNotes = personalNotesDao.findById(review.getId());
        assertEquals(review, foundPersonalNotes);
    }

    @Test
    public void returnAllPersonalNotes() throws Exception {
        PersonalNotes review = setupNewPersonalNotes();
        personalNotesDao.add(review);
        assertEquals(1, personalNotesDao.getAll().size());
    }

    @Test
    public void noPersonalNotesReturnsEmptyList() throws Exception {
        assertEquals(0, personalNotesDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectPersonalNotes() throws Exception {
        PersonalNotes review = setupNewPersonalNotes();
        personalNotesDao.add(review);
        personalNotesDao.deletePersonalNotesById(review.getId());
        assertEquals(0, personalNotesDao.getAll().size());
    }



    //helpers
    public PersonalNotes setupNewPersonalNotes(){
        return new PersonalNotes("Written By", 5 , 4, "Note Contents");
    }

    public PersonalNotes setupAltPersonalNotes(){
        return new PersonalNotes("Not Written By", 4, 5,"Not Note Contents");
    }

    public Entry setupEntry() {
        return new Entry ("chicken");
    }
}