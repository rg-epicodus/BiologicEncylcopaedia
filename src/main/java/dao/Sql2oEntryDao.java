package dao;

import org.sql2o.Sql2o;

public class Sql2oEntryDao implements EntryDao {
    private final Sql2o sql2o;

    public Sql2oEntryDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
}
