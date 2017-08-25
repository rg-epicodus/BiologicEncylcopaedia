package dao;

import models.Entry;
import models.Kingdom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oEntryDaoTest {
    private Sql2oKingdomDao kingdomDao;
    private Sql2oEntryDao entryDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/encyclopaedia.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        kingdomDao = new Sql2oKingdomDao(sql2o);
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
        int originalEntryId = testEntry.getId();
        entryDao.add(testEntry);
        assertNotEquals(originalEntryId,testEntry.getId());
    }

    @Test
    public void addedEntriesAreReturnedFromGetAll() throws Exception {
        Entry testEntry = setupNewEntry();
        entryDao.add(testEntry);
        assertEquals(1, entryDao.getAll().size());
    }

    @Test
    public void noEntryReturnsEmptyList() throws Exception {
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectEntry() throws Exception {
        Entry foodtype = setupNewEntry();
        entryDao.add(foodtype);
        entryDao.deleteById(foodtype.getId());
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void findById_findCorrectEntry() throws Exception {
        Entry entry = setupNewEntry();
        entryDao.add(entry);
        Entry foundEntry = entryDao.findById(entry.getId());
        assertEquals(entry, foundEntry);
    }

    @Test
    public void addEntryToKingdomAddsCorrectly() throws Exception {

        Kingdom testKingdom = setupKingdom();
        Kingdom altKingdom = setupAltKingdom();

        kingdomDao.add(testKingdom);
        kingdomDao.add(altKingdom);

        Entry testEntry = setupNewEntry();

        entryDao.add(testEntry);

        entryDao.addEntryToKingdom(testKingdom, testEntry);
        entryDao.addEntryToKingdom(altKingdom, testEntry);

        assertEquals(2, entryDao.getAllKingdomsForAnEntry(testEntry.getId()).size());
    }


    // helpers
    public Entry setupNewEntry(){
        return new Entry("chicken");
    }

    public Kingdom setupKingdom (){
        return new Kingdom("Animalia");
    }

    public Kingdom setupAltKingdom (){
        return new Kingdom("Plantae");
    }
}