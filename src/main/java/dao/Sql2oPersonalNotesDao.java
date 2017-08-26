package dao;

import models.Entry;
import models.PersonalNotes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oPersonalNotesDao implements PersonalNotesDao {
    private final Sql2o sql2o;

    public Sql2oPersonalNotesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(PersonalNotes personalNotes){
        String sql = "INSERT INTO personalNotes (writtenBy, content) VALUES (:writtenBy, :content)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .bind(personalNotes)
                    .executeUpdate()
                    .getKey();
            personalNotes.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public PersonalNotes findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM personalNotes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(PersonalNotes.class);
        }
    }

    @Override
    public List<PersonalNotes> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM personalNotes")
                    .executeAndFetch(PersonalNotes.class);
        }
    }

    @Override
    public void deletePersonalNotesById(int id) {
        String sql = "DELETE from personalNotes WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllPersonalNotes() {
        String sql = "DELETE from personalNotes";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(String writtenBy, String content, int id){
        String query = "UPDATE personalNotes SET (writtenBy, content) = (:writtenBy, :content) WHERE id =:id";
        try(Connection con =sql2o.open()){
            con.createQuery(query)
                    .addParameter("writtenBy", writtenBy)
                    .addParameter("content", content)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e);
        }
    }







//    @Override
//    public void addPersonalNotesToEntry(PersonalNotes personalNotes, Entry entry){
//        String sql = "INSERT INTO entry_personalNotes (entryId, personalNotesId) VALUES (:entryId, :personalNotesId)";
//        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("entryId", entry.getEntryId())
//                    .addParameter("personalNotesId", personalNotes.getId())
//                    .executeUpdate();
//        } catch (Sql2oException ex){
//            System.out.println(ex);
//        }
//    }
//
//    @Override
//    public List<Entry> getAllEntriesForAPersonalNote(int personalNotesId) {
//
//        ArrayList<Entry> entry = new ArrayList<>();
//
//        String joinQuery = "SELECT entryId FROM entry_personalNotes WHERE personalNotesId = :personalNotesId";
//
//        try (Connection con = sql2o.open()) {
//            List<Integer> allEntryIds = con.createQuery(joinQuery)
//                    .addParameter("personalNotesId", personalNotesId)
//                    .executeAndFetch(Integer.class);
//            for (Integer entryId : allEntryIds){
//                String entryQuery = "SELECT * FROM entry WHERE id = :entryId";
//                entry.add(
//                        con.createQuery(entryQuery)
//                                .addParameter("entryId", entryId)
//                                .executeAndFetchFirst(Entry.class));
//            }
//        } catch (Sql2oException ex){
//            System.out.println(ex);
//        }
//        return entry;
//    }


//    @Override
//    public List<PersonalNotes> getAllPersonalNotesForAnEntry(int entryId) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * FROM personalNotes WHERE entryId = :entryId")
//                    .addParameter("entryId", entryId)
//                    .executeAndFetch(PersonalNotes.class);
//        }
//
//    }


}
