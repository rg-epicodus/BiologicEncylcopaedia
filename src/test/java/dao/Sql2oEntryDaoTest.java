package dao;

import models.Entry;
import models.Kingdom;
import models.PersonalNotes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oEntryDaoTest {
    private Sql2oPersonalNotesDao personalNotesDao;
    private Sql2oEntryDao entryDao;
    private Connection conn;

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
    public void addingEntrySetsId() throws Exception {
        Entry testEntry = setupNewEntry();
        entryDao.add(testEntry);
        assertEquals(1, testEntry.getEntryId());
        assertEquals("Animalia", testEntry.getKingdomName());
        assertEquals("Chicken", testEntry.getCommonName());
        assertEquals("Chordata", testEntry.getPhylum());

    }

    @Test
    public void addedEntriesAreReturnedFromGetAll() throws Exception {
        Entry testEntry = setupNewEntry();
        entryDao.add(testEntry);
        Entry testAltEntry = setupAltNewEntry();
        entryDao.add(testAltEntry);
        assertEquals(2, entryDao.getAll().size());
    }

    @Test
    public void noEntryReturnsEmptyList() throws Exception {
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectEntry() throws Exception {
        Entry testEntry = setupNewEntry();
        entryDao.add(testEntry);
        entryDao.deleteById(testEntry.getEntryId());
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void findById_findCorrectEntry() throws Exception {
        Entry entry = setupNewEntry();
        entryDao.add(entry);
        Entry foundEntry = entryDao.findById(entry.getEntryId());
        assertEquals(entry, foundEntry);
    }

    @Test
    public void kingdomIdIsReturnedCorrectly() throws Exception {
        Entry entry = setupNewEntry();
        int originalId = entry.getKingdomId();
        entryDao.add(entry);
        assertEquals(originalId, entryDao.findById(entry.getEntryId()).getKingdomId());
    }

    @Test
    public void clearAllEntries() throws Exception {
        Entry entry = setupNewEntry();
        Entry entry2 = setupAltNewEntry();
        entryDao.add(entry);
        entryDao.add(entry2);
        int daoSize = entryDao.getAll().size();
        entryDao.clearAllEntries();
        assertTrue(daoSize > 0 && daoSize >entryDao.getAll().size());

    }

    @Test
    public void updateEntryUpdatesCorrectly() throws Exception {
        Entry entry = setupNewEntry();
        entryDao.add(entry);
        entryDao.update(entry.getEntryId(), "Chicken", "Chordata");
        Entry updatedEntry = entryDao.findById(entry.getEntryId());
        assertEquals("Chicken", updatedEntry.getCommonName());
        assertEquals("Chordata", updatedEntry.getPhylum());
    }

    @Test
    public void getAllPersonalNotesForAnEntryReturnsCorrectly() throws Exception {
        PersonalNotes newPersonalNotes = newPersonalNotes();
        personalNotesDao.add(newPersonalNotes);

        PersonalNotes otherPersonalNotes  = newPersonalNotes();
        personalNotesDao.add(otherPersonalNotes);

        Entry testEntry = setupNewEntry();
        entryDao.add(testEntry);
        entryDao.addPersonalNotesToEntry(testEntry, newPersonalNotes);
        entryDao.addPersonalNotesToEntry(testEntry, otherPersonalNotes);

        PersonalNotes[] personalNotes = {newPersonalNotes, otherPersonalNotes};

        assertEquals(entryDao.getAllPersonalNotesByEntry(testEntry.getEntryId()), Arrays.asList(personalNotes));
    }


    // helpers
    public Entry setupNewEntry(){
        return new Entry("Animalia","Chicken", "Chordata" );
    }
    public Entry setupAltNewEntry(){
        return new Entry("Plantae","Cattle", "Chordata" );
    }
    public PersonalNotes newPersonalNotes() {
        return new PersonalNotes("Animalia", "Bob the Builder", "Looks harmless");
    }

}