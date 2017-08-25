package dao;

import models.Entry;
import models.Kingdom;
import models.PersonalNotes;

import java.util.List;

public interface EntryDao {

    //create
    void add(Entry entry);
    void addEntryToKingdom(Kingdom kingdom, Entry entry);
    void addEntryToPersonalNotes(PersonalNotes personalNotes, Entry entry);


    //read
    List<Entry> getAll();
    Entry findById(int id);
    List<Kingdom> getAllKingdomsForAnEntry(int id);
//    List<PersonalNotes> getAllPersonalNotesForAnEntry(int id);

    //update

    //destroy
    void deleteById(int id);
}
