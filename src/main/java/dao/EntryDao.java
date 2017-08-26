package dao;

import models.Entry;
import models.Kingdom;
import models.PersonalNotes;

import java.util.List;

public interface EntryDao {

    //create
    void add(Entry entry);
    void addPersonalNotesToEntry(Entry entry, PersonalNotes personalNotes);

    //read
    Entry findById(int id);
//    List<Entry> getAllKingdomsForAnEntry(int kingdomId);
    List<PersonalNotes> getAllPersonalNotesByEntry(int personalNotesId);
    List<Entry> getAll();

    //update
    void update(int entryId, String commonName, String phylum);

    //destroy
    void deleteById(int entryId);
    void clearAllEntries();
}
