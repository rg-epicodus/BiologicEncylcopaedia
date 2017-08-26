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
            kingdom.setOrganismId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Kingdom findById(int organismId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM kingdom WHERE organismId = :organismId")
                    .addParameter("organismId", organismId)
                    .executeAndFetchFirst(Kingdom.class);
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
    public void update(int organismId, String newKingdomName){
        String sql = "UPDATE kingdom SET (kingdomName) = (:kingdomName) WHERE organismId = :organismId";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("organismId", organismId)
                    .addParameter("kingdomName", newKingdomName)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int organismId) {
        String sql = "DELETE FROM kingdom WHERE organismId = :organismId";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("organismId", organismId)
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
    public void addEntryToKingdom(Kingdom kingdom, Entry entry){
        String query = "INSERT INTO kingdom_personalNotes (kingdomId, entryId) VALUES (:kingdomId, :entryId)";
        try(Connection con = sql2o.open()){
            con.createQuery(query)
                    .addParameter("kingdomId", kingdom.getOrganismId())
                    .addParameter("entryId", entry.getEntryId())
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Entry> getAllEntriesForAKingdom(int kingdomId){
        List<Entry> entry = new ArrayList<>();
        String query = "SELECT entryId FROM kingdom_personalNotes WHERE kingdomId = :kingdomId";
        try(Connection con =sql2o.open()){
            List<Integer> allEntryIds = con.createQuery(query)
                    .addParameter("kingdomId", kingdomId)
                    .executeAndFetch(Integer.class);
            for (Integer entryId : allEntryIds) {
                String query2 = "SELECT * from entry WHERE entryId = :entryId";
                entry.add(
                        con.createQuery(query2)
                                .addParameter("entryId", entryId)
                                .executeAndFetchFirst(Entry.class));
            }
        }catch(Sql2oException e) {
            System.out.println(e);
        }
        return entry;
    }


}
