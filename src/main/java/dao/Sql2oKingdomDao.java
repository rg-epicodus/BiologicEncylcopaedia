package dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oKingdomDao implements KingdomDao {
    private final Sql2o sql2o;

    public Sql2oKingdomDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
}
