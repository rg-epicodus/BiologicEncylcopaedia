package dao;

import models.Entry;
import models.Kingdom;
import models.PersonalNotes;

import java.util.List;

public interface EntryDao {

//    //create
    void add(Entry entry);
    void addPersonalNotesToEntry(PersonalNotes personalNotes, Entry entry);
//
//    //read
    Entry findById(int id);
    List<Entry> getAll();
//    List<Entry> getAllKingdomsForAnEntry(int kingdomId);
    List<PersonalNotes> getAllPersonalNotesForAnEntry(int personalNotesId);
//
//    //update
//    void update(int entryId, String commonName, String phylum);
//    //destroy
    void deleteById(int entryId);
}
