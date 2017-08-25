package dao;

import models.Entry;
import models.Kingdom;
import models.PersonalNotes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oEntryDao implements EntryDao {
    private final Sql2o sql2o;

    public Sql2oEntryDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Entry entry) {
        String sql = "INSERT INTO entry (commonName) VALUES (:commonName)";
        try (Connection con = sql2o.open()) {
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
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry")
                    .executeAndFetch(Entry.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from entry WHERE id=:id";
        String joinQuery = "DELETE from kingdom_entry WHERE entryId = :entryId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(joinQuery)
                    .addParameter("entryId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Entry findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry where id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Entry.class);
        }
    }

    @Override
    public void addEntryToKingdom(Kingdom kingdom, Entry entry) {
        String sql = "INSERT INTO kingdom_entry (kingdomId, entryId) VALUES (:kingdomId, :entryId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("kingdomId", kingdom.getId())
                    .addParameter("entryId", entry.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addEntryToPersonalNotes(PersonalNotes personalNotes, Entry entry) {
        String sql = "INSERT INTO personalNotes_entry (personalNotesId, entryId) VALUES (:personalNotesId, :entryId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("personalNotesId", personalNotes.getId())
                    .addParameter("entryId", entry.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Kingdom> getAllKingdomsForAnEntry(int entryId) {

        ArrayList<Kingdom> kingdoms = new ArrayList<>();

        String joinQuery = "SELECT kingdomId FROM kingdom_entry WHERE entryId = :entryId";

        try (Connection con = sql2o.open()) {
            List<Integer> allKingdomIds = con.createQuery(joinQuery)
                    .addParameter("entryId", entryId)
                    .executeAndFetch(Integer.class);
            for (Integer kingdomId : allKingdomIds) {
                String kingdomQuery = "SELECT * FROM kingdom WHERE id = :kingdomId";
                kingdoms.add(
                        con.createQuery(kingdomQuery)
                                .addParameter("kingdomId", kingdomId)
                                .executeAndFetchFirst(Kingdom.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return kingdoms;
    }

    public List<PersonalNotes> getAllPersonalNotesForAnEntry(int personalNotesId){
        ArrayList<PersonalNotes> allPersonalNotes = new ArrayList<>();
        String getIdsSQL = "SELECT entryId FROM entry_personalNotes WHERE personalNotesId = :personalNotesId";
        String sql = "SELECT * FROM entry WHERE id = :id";
        try (Connection con = sql2o.open()){
            List<Integer> entryIds = con.createQuery(getIdsSQL)
                    .addParameter("personalNotesId", personalNotesId)
                    .executeAndFetch(Integer.class);
            for(Integer entryId : entryIds){
                allPersonalNotes.add(
                        con.createQuery(sql)
                                .addParameter("id", entryId)
                                .executeAndFetchFirst(PersonalNotes.class)
                );
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allPersonalNotes;
    }



}

