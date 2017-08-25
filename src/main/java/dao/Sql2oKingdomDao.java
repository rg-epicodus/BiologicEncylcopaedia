package dao;

import models.Kingdom;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class Sql2oKingdomDao implements KingdomDao {
    private final Sql2o sql2o;

    public Sql2oKingdomDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Kingdom kingdom){
        String sql = "INSERT INTO kingdom (kingdomName) VALUES (:kingdomName)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(kingdom)
                    .executeUpdate()
                    .getKey();
            kingdom.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
