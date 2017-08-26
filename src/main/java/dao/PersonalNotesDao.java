package dao;

import models.Entry;
import models.PersonalNotes;

import java.util.List;

public interface PersonalNotesDao {

    //create
    void add (PersonalNotes personalNotes);

    //read
    PersonalNotes findById(int id);
    List<PersonalNotes> getAll();

    //update
    void update(String writtenBy, String Content, int id);

    //destroy
    void deletePersonalNotesById(int id);
    void clearAllPersonalNotes();
}
