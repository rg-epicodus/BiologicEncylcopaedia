package dao;

import models.PersonalNotes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oPersonalNotesDao implements PersonalNotesDao {
    private final Sql2o sql2o;

    public Sql2oPersonalNotesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(PersonalNotes personalNotes){
        String sql = "INSERT INTO personalNotes (writtenBy, entryId, personalNotesId, content) VALUES (:writtenBy, :entryId, :personalNotesId, :content)";
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

//    @Override
//    public List<PersonalNotes> getAllPersonalNotesForAnEntry(int entryId) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * FROM personalNotes WHERE entryId = :entryId")
//                    .addParameter("entryId", entryId)
//                    .executeAndFetch(PersonalNotes.class);
//        }
//
//    }

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

}
