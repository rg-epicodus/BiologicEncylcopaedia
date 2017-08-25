package dao;

import models.Entry;
import models.Kingdom;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
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

    @Override
    public Kingdom findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM kingdom WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Kingdom.class);
        }
    }

    @Override
    public void update(int id, String kingdomName){
        String sql = "UPDATE kingdom SET (kingdomName) = (:kingdomName) WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("kingdomName", kingdomName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllKingdoms() {
        String sql = "DELETE from kingdom";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Entry> getAllEntriesForAKingdom(int kingdomId) {
        ArrayList<Entry> entries = new ArrayList<>();
        String joinQuery = "SELECT entryid FROM kingdom_entry WHERE kingdomid = :kingdomId";
        try (Connection con = sql2o.open()) {
            List<Integer> allEntrysIds = con.createQuery(joinQuery)
                    .addParameter("kingdomId", kingdomId)
                    .executeAndFetch(Integer.class);
            for (Integer entryId : allEntrysIds) {
                String entryQuery = "SELECT * FROM entry WHERE id = :entryId";
                entries.add(
                        con.createQuery(entryQuery)
                                .addParameter("entryId", entryId)
                                .executeAndFetchFirst(Entry.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return entries;
    }

    @Override
    public void addKingdomToEntry(Kingdom kingdom, Entry entry) {
        String sql = "INSERT INTO kingdom_entry (kingdomid, entryid) VALUES (:kingdomId, :entryId)";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("kingdomId", kingdom.getId())
                    .addParameter("entryId", entry.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


}
