package dao;


import models.Kingdom;

import java.util.List;

public interface KingdomDao {

    //Create
    void add(Kingdom kingdom);

    //Read
    List<Kingdom> getAll();
    Kingdom findById(int id);

    //Update
    void update(int id, String newKingdom);

    //Destroy
    void deleteById(int id);
//    void clearAllKingdoms();
}