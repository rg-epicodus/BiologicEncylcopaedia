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
    public void addPersonalNotesToEntry(Entry entry, PersonalNotes personalNotes) {
        String sql = "INSERT INTO kingdom_personalNotes (kingdomId, personalNotesId) VALUES (:kingdomId, :personalNotesId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("kingdomId", entry.getEntryId())
                    .addParameter("personalNotesId", personalNotes.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<PersonalNotes> getAllPersonalNotesByEntry(int kingdomId){
        ArrayList<PersonalNotes> allPersonalNotes = new ArrayList<>();
        String getIdsSQL = "SELECT personalNotesId FROM kingdom_personalNotes WHERE kingdomId = :kingdomId";
        String sql = "SELECT * FROM personalNotes WHERE id = :personalNotesId";
        try (Connection con = sql2o.open()){
            List<Integer> personalNotesIds = con.createQuery(getIdsSQL)
                    .addParameter("kingdomId", kingdomId)
                    .executeAndFetch(Integer.class);
            for(Integer personalNotesId : personalNotesIds){
                allPersonalNotes.add(
                        con.createQuery(sql)
                                .addParameter("personalNotesId", personalNotesId)
                                .executeAndFetchFirst(PersonalNotes.class)
                );
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allPersonalNotes;
    }

    @Override
    public void clearAllEntries() {
        String sql = "DELETE from entry";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(int entryId, String newCommonName, String newPhylum) {
        String query = "UPDATE entry SET (commonName, phylum) = (:commonName, :phylum) WHERE entryId = :entryId";
        try(Connection con = sql2o.open()){
            con.createQuery(query)
                    .addParameter("entryId", entryId)
                    .addParameter("commonName", newCommonName)
                    .addParameter("phylum", newPhylum)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println(e);
        }
    }



}

