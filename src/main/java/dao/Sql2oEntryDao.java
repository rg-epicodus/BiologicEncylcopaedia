package dao;

import models.Entry;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEntryDao implements EntryDao {
    private final Sql2o sql2o;

    public Sql2oEntryDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Entry entry) {
        String sql = "INSERT INTO entry (commonName) VALUES (:commonName)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .bind(entry)
                    .executeUpdate()
                    .getKey();
            entry.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Entry> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM entry")
                    .executeAndFetch(Entry.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from entry WHERE id=:id";
        String joinQuery = "DELETE from kingdom_entry WHERE entryid = :entryId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(joinQuery)
                    .addParameter("entryId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
