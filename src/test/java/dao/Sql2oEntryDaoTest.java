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