package dao;

import models.Kingdom;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

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

    @Override
    public List<Kingdom> getAll(){
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM kingdom")
                    .executeAndFetch(Kingdom.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from kingdom WHERE id = :id";
//        String deletejoin = "DELETE from kingdom_entry WHERE kingdomid = :kingdomId";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
//            con.createQuery(deletejoin)
//                    .addParameter("kigndomId", id)
//                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
