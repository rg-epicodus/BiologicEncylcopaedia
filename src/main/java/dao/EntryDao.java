package dao;

import models.Entry;
import models.Kingdom;

import java.util.List;

public interface EntryDao {

    //create
    void add(Entry entry);

    //read
    List<Entry> getAll();
    Entry findById(int id);
//    List<Kingdom> getAllKingdomsForAnEntry(int id);

    //update

    //destroy
    void deleteById(int id);
}
