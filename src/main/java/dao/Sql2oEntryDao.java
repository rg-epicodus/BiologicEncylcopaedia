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
        String sql = "INSERT INTO entry (commonName, phylum, kingdomId) VALUES (:commonName, :phylum, :kingdomId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(entry)
                    .executeUpdate()
                    .getKey();
            entry.setEntryId(id);
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
    public void deleteById(int entryId) {
        String sql = "DELETE from entry WHERE entryId=:entryId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("entryId", entryId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Entry findById(int entryId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry where entryId = :entryId")
                    .addParameter("entryId", entryId)
                    .executeAndFetchFirst(Entry.class);
        }
    }

    @Override
    public void addPersonalNotesToEntry(PersonalNotes personalNotes, Entry entry) {
        String sql = "INSERT INTO entry_personalNotes (personalNotesId, entryId) VALUES (:personalNotesId, :entryId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("personalNotesId", personalNotes.getId())
                    .addParameter("entryId", entry.getEntryId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<PersonalNotes> getAllPersonalNotesForAnEntry(int personalNotesId){
        ArrayList<PersonalNotes> allPersonalNotes = new ArrayList<>();
        String getIdsSQL = "SELECT entryId FROM entry_personalNotes WHERE personalNotesId = :personalNotesId";
        String sql = "SELECT * FROM entry WHERE entryId = :entryId";
        try (Connection con = sql2o.open()){
            List<Integer> entryIds = con.createQuery(getIdsSQL)
                    .addParameter("personalNotesId", personalNotesId)
                    .executeAndFetch(Integer.class);
            for(Integer entryId : entryIds){
                allPersonalNotes.add(
                        con.createQuery(sql)
                                .addParameter("entryId", entryId)
                                .executeAndFetchFirst(PersonalNotes.class)
                );
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allPersonalNotes;
    }



}

