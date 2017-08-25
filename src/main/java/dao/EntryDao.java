package dao;

import models.Entry;
import models.Kingdom;

import java.util.List;

public interface EntryDao {

    //create
    void add(Entry entry);
    //void addEntryToKingdom(Kingdom kingdom, Entry entry);

    //read
    List<Entry> getAll();
    Entry findById(int id);

    //update

    //destroy
    void deleteById(int id);
}
