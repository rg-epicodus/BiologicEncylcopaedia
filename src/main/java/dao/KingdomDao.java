package dao;


import models.Kingdom;

import java.util.List;

public interface KingdomDao {

    //Create
    void add(Kingdom kingdom);

    //Read
    List<Kingdom> getAll();

    Kingdom findById(int id);

    //Update Kingdom Information
    void update(int id, String newKingdom);

    //Delete by Kingdom
    void deleteById(int id);
    void clearAllKingdoms();
}