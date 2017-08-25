package dao;

import models.Kingdom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

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
    public void addingEntrySetsId() throws Exception {
        Kingdom testKingdom = setupKingdom();
        int originalKingdomId = testKingdom.getId();
        kingdomDao.add(testKingdom);
        assertNotEquals(originalKingdomId,testKingdom.getId());
    }

    // helpers

    public Kingdom setupKingdom (){
        return new Kingdom("Animalia");
    }

}