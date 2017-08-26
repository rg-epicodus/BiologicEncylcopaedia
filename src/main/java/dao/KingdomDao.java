package dao;


import models.Entry;
import models.Kingdom;

import java.util.List;

public interface KingdomDao {

    //Create
    void add(Kingdom kingdom);
////    void addKingdomToEntry(Kingdom kingdom, Entry entry);
//    void addEntryToKingdom(Kingdom kingdom, Entry entry);
//
//    //Read
//    List<Entry> getAllEntriesForAKingdom(int entryId);
    List<Kingdom> getAll();
    Kingdom findById(int organismId);
//
//    //Update
    void update(int organismId, String newKingdomName);
//
//    //Destroy
    void deleteById(int oraganismId);
    void clearAllKingdoms();
}