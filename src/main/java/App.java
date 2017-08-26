import com.google.gson.Gson;
import dao.Sql2oEntryDao;
import dao.Sql2oKingdomDao;
import dao.Sql2oPersonalNotesDao;
import exceptions.ApiException;
import models.Entry;
import models.Kingdom;
import models.PersonalNotes;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oEntryDao entryDao;
        Sql2oPersonalNotesDao personalNotesDao;
        Sql2oKingdomDao kingdomDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/API.db;INIT=RUNSCRIPT from 'classpath:db/encyclopaedia.sql'"; //check me!
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        entryDao = new Sql2oEntryDao(sql2o);
        personalNotesDao = new Sql2oPersonalNotesDao(sql2o);
        kingdomDao = new Sql2oKingdomDao(sql2o);
        conn = sql2o.open();

        // creates a new entry into DB
        post("/kingdom/new", "application/json", (req,res) ->{
            Kingdom kingdom = gson.fromJson(req.body(), Kingdom.class);
            kingdomDao.add(kingdom);
            res.status(201);
            return gson.toJson(kingdom);
        });

        // read all kingdoms
        get("/kingdom", "application/json", (req, res)->{
            return gson.toJson(kingdomDao.getAll());
        });

        // read a specific kingdom
        get("/kingdom/:id", "application/json", (req, res) -> {
            int kingdomId = Integer.parseInt(req.params("id"));
            Kingdom kingdom = kingdomDao.findById(kingdomId);
            if (kingdom == null) {
                throw new ApiException(String.format("No Kingdom with the id '%d' found", kingdomId), 404);
            } return gson.toJson(kingdom);
        });

        // update a kingdom
            post("/kingdom/:id/update", "application/json", (req, res) -> {
            int kingdomId = Integer.parseInt(req.params("id"));
            Kingdom kingdom = gson.fromJson(req.body(), Kingdom.class);
            kingdomDao.update(kingdomId, kingdom.getKingdomName());
            res.status(201);
            return gson.toJson(kingdom);
        });

        // delete a kingdom
        get("/kingdom/:id/delete", "application/json", (req, res) -> {
            int kingdomId = Integer.parseInt(req.params("id"));
            kingdomDao.deleteById(kingdomId);
            return kingdomId;
        });

        // delete all kingdoms
        get("/kingdom/:id/delete/all", "application/json", (req, res) ->{
            kingdomDao.clearAllKingdoms();
            return kingdomDao.getAll().size();
        });

        // create a personal note
        post("/personalNotes/new", "application/json", (req, res) -> {
            PersonalNotes personalNotes = gson.fromJson(req.body(), PersonalNotes.class);
            personalNotesDao.add(personalNotes);
            res.status(201);
            return gson.toJson(personalNotes);
        });

        // read all personal notes
        get("/personalNotes", "application/json", (req, res) -> {
            return gson.toJson(personalNotesDao.getAll());
        });

        // read a specific personal note
        get("/personalNotes/:id", "application/json", (req, res)->{
            int personalNotesId = Integer.parseInt(req.params("id"));
            PersonalNotes personalNotes = personalNotesDao.findById(personalNotesId);
            if (personalNotes == null){
                throw new ApiException(String.format("No personalNotes with the id '%d' found", personalNotesId), 404);
            }
            return gson.toJson(personalNotes);
        });

        // update a personal note
        post("/personalNotes/:id/update", "application/json", (req, res)->{
            int personalNotesId = Integer.parseInt(req.params("id"));
            PersonalNotes personalNotes = gson.fromJson(req.body(), PersonalNotes.class);
            personalNotesDao.update(personalNotes.getWrittenBy(), personalNotes.getContent(), personalNotesId);
            res.status(201);
            return gson.toJson(personalNotes);
        });

        // delete a personal note
        get("/personalNotes/:id/delete", "application/json", (req, res) ->{
            int personalNotesId = Integer.parseInt(req.params("id"));
            personalNotesDao.deletePersonalNotesById(personalNotesId);
            return personalNotesId;
        });

        // delete all personal notes
        get("/personalNotes/:id/delete/all", "application/json", (req, res)->{
            personalNotesDao.clearAllPersonalNotes();
            return personalNotesDao.getAll().size();
        });

        // create an entry
        post("/entry/new", "application/json", (req,res)->{
            Entry entry = gson.fromJson(req.body(), Entry.class);
            entryDao.add(entry);
            res.status(201);
            return gson.toJson(entry);
        });

        //read all entries
        get("/entry", "application/json", (req, res) ->{
            return gson.toJson(entryDao.getAll());
        });

        // read a specific entry
        get("/entry/:id", "application/json", (req, res)->{
            int entryId = Integer.parseInt(req.params("id"));
            Entry entry = entryDao.findById(entryId);
            if (entry == null){
                throw new ApiException(String.format("No entry with the id '%d' found", entryId), 404);
            }
            return gson.toJson(entry);
        });

        // update an entry
        post("/entry/:id/update", "application/json", (req, res) ->{
            int entryId = Integer.parseInt(req.params("id"));
            Entry entry = gson.fromJson(req.body(), Entry.class);
            entryDao.update(entryId, entry.getCommonName(), entry.getPhylum());
            res.status(201);
            return gson.toJson(entry);
        });

        // delete an entry
        get("/entry/:id/delete", "application/json", (req,res)->{
            int entryId = Integer.parseInt(req.params("id"));
            entryDao.deleteById(entryId);
            return entryId;
        });

        // delete all entries
        get("/entry/:id/delete/all", "application/json", (req,res)->{
            entryDao.clearAllEntries();
            return entryDao.getAll().size();
        });

        // add entry to a kingdom
        post("/kingdom/:kingdomId/entry/new", "application/json", (req,res)->{
            int kingdomId = Integer.parseInt(req.params("kingdomId"));
            Entry entry = gson.fromJson(req.body(), Entry.class);
            entry.setOrganismId(kingdomId);
            entryDao.add(entry);
            res.status(201);
            return gson.toJson(entry);
        });

        // read the entries in a kingdom
        get("/kingdom/:id/entry", "application/json", (req, res)->{
            int kingdomId = Integer.parseInt(req.params("id"));
            kingdomDao.getAllEntriesForAKingdom(kingdomId);
            return gson.toJson(kingdomDao.getAllEntriesForAKingdom(kingdomId));
        });

//        // read the kingdoms that are part of an entry
//        get("/entry/:id/kingdom", "application/json", (req, res)->{
//            int entryId = Integer.parseInt(req.params("id"));
//            entryDao.getAllKingdomsForAnEntry(entryId);
//            return gson.toJson(entryDao.getAllKingdomsForAnEntry(entryId));
//        });

        // add personal notes to an entry
        post("/entry/:id/personalNotes/:personalNotesId", "application/json", (req,res)->{
            int entryId = Integer.parseInt(req.params("id"));
            int personalNotesId = Integer.parseInt(req.params("personalNotesId"));
            PersonalNotes personalNotes = personalNotesDao.findById(personalNotesId);
            Entry entry = entryDao.findById(entryId);
            entryDao.addPersonalNotesToEntry(entry, personalNotes);
            res.status(201);
            return gson.toJson(entryDao.getAllPersonalNotesByEntry(entryId));
        });

        // read personal notes in an entry
        get("/entry/:id/personalNotes", "application/json", (req,res)->{
            int entryId = Integer.parseInt(req.params("id"));
            entryDao.getAllPersonalNotesByEntry(entryId);
            return gson.toJson(entryDao.getAllPersonalNotesByEntry(entryId));

        });




        exception(ApiException.class, (exc, req, res) -> {
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });

    }
}
