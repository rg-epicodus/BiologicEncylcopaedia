package dao;

import models.Entry;
import models.Kingdom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oKingdomDaoTest {
    private Connection conn;
    private Sql2oKingdomDao kingdomDao;
    private Sql2oEntryDao entryDao;
    private Sql2oPersonalNotesDao personalNotesDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/encyclopaedia.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        kingdomDao = new Sql2oKingdomDao(sql2o);
        entryDao = new Sql2oEntryDao(sql2o);
        personalNotesDao = new Sql2oPersonalNotesDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingKingdomSetsId() throws Exception {
        Kingdom testKingdom = setupKingdom();
        int originalKingdomId = testKingdom.getOrganismId();
        kingdomDao.add(testKingdom);
        assertNotEquals(originalKingdomId,testKingdom.getOrganismId());
    }

    @Test
    public void addedKingdomsAreReturnedFromGetAll() throws Exception {
        Kingdom testKingdom = setupKingdom();
        kingdomDao.add(testKingdom);
        assertEquals(1, kingdomDao.getAll().size());
    }

    @Test
    public void noKingdomReturnsEmptyList() throws Exception {
        assertEquals(0, kingdomDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectKingdom() throws Exception {
        Kingdom testKingdom = setupKingdom();
        kingdomDao.add(testKingdom);
        kingdomDao.deleteById(testKingdom.getOrganismId());
        assertEquals(0, kingdomDao.getAll().size());
    }

    @Test
    public void findById_findCorrectKindom() throws Exception {
        Kingdom kingdom = setupKingdom();
        kingdomDao.add(kingdom);
        Kingdom foundKingdom = kingdomDao.findById(kingdom.getOrganismId());
        assertEquals(kingdom, foundKingdom);
    }

    @Test
    public void updateKingdomInformation() throws Exception {
        Kingdom kingdom = setupKingdom();
        kingdomDao.add(kingdom);
        kingdomDao.update(kingdom.getOrganismId(), "Plantae");
        Kingdom updateLocation = kingdomDao.findById(kingdom.getOrganismId());
        assertNotEquals(kingdom, updateLocation.getKingdomName());
    }

    @Test
    public void clearAllKingdomsClearsAllKingdoms() throws Exception {
        Kingdom kingdom = setupKingdom();
        Kingdom kingdom2 = setupKingdom2();
        kingdomDao.add(kingdom);
        kingdomDao.add(kingdom2);
        int daoSize = kingdomDao.getAll().size();
        kingdomDao.clearAllKingdoms();
        assertTrue(daoSize > 0 && daoSize >kingdomDao.getAll().size());
    }

//    @Test
//    public void getAllEntriesForAKingdomReturnsEntriesCorrectly() throws Exception {
//        Entry testEntry  = new Entry("entryOne");
//        entryDao.add(testEntry);
//        Entry otherEntry  = new Entry("entryTwo");
//        entryDao.add(otherEntry);
//        Kingdom testKingdom = setupKingdom();
//        kingdomDao.add(testKingdom);
//        kingdomDao.addKingdomToEntry(testKingdom,testEntry);
//        kingdomDao.addKingdomToEntry(testKingdom,otherEntry);
//        Entry[] entry = {testEntry, otherEntry};
//        assertEquals(kingdomDao.getAllEntriesForAKingdom(testKingdom.getOrganismId()), Arrays.asList(entry));
//    }

        // helpers

    public Kingdom setupKingdom (){
        return new Kingdom("Animalia");
    }

    public Kingdom setupKingdom2 (){
        return new Kingdom("Plantae");
    }


}