package dao;

import models.PersonalNotes;

import java.util.List;

public interface PersonalNotesDao {

    //create
    void add (PersonalNotes personalNotes);

    //read
    PersonalNotes findById(int id);
    List<PersonalNotes> getAll();

    //update

    //destroy
    void deletePersonalNotesById(int id);
}
