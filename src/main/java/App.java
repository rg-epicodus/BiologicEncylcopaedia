import com.google.gson.Gson;
import dao.Sql2oEntryDao;
import dao.Sql2oKingdomDao;
import dao.Sql2oPersonalNotesDao;
import exceptions.ApiException;
import models.Kingdom;
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

        get("/kingdom", "application/json", (req, res)->{
            return gson.toJson(kingdomDao.getAll());
        });

        get("/kingdom/:id", "application/json", (req, res) -> {
            int kingdomId = Integer.parseInt(req.params("id"));
            Kingdom kingdom = kingdomDao.findById(kingdomId);
            if (kingdom == null) {
                throw new ApiException(String.format("No location with the id '%d' found", kingdomId), 404);
            } return gson.toJson(kingdom);
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
