package dao;

import models.PersonalNotes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oPersonalNotesDaoTest {
    private Connection conn;
    private Sql2oPersonalNotesDao personalNotesDao;
    private Sql2oEntryDao entryDao;

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
        int originalEntryId = testPersonalNotes.getId();
        personalNotesDao.add(testPersonalNotes);
        assertEquals(originalEntryId, testPersonalNotes.getId());
    }


    //helpers
    public PersonalNotes setupNewPersonalNotes(){
        return new PersonalNotes("Written By", 5, "Note Contents");
    }

}