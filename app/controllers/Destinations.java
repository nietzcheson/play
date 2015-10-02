package controllers;

import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import models.*;
import util.Constants;

@With(Secure.class)
public class Destinations extends MasterController {
    public static void list() {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String destinationName = params.get("destinationName");
        Logger.info("destinationName: >>>" + destinationName);
        String id = params.get("id-value");
        Logger.info("id: >>>" + id);


        if(mode != null && !mode.isEmpty()) {
            Logger.info("mode existe");

            Logger.info("mode: >>>" + mode);
            if(mode.equals("edit")) {
                successfullyUpdated = true;
                Logger.info("Edition correcta");
            } else if(mode.equals("create")) {
                successfullyCreated = true;
                Logger.info("Creación correcta");
            }
        }

        renderArgs.put("id", id);
        renderArgs.put("destinationName", destinationName);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);
        render();
    }

    public static void create(Integer id) {

        boolean editMode;

        if( id != null) {
            editMode = true;
            Logger.info("Modo editar");
        } else {
            editMode = false;
            Logger.info("Modo crear");
        }
        Logger.info("id: >>>" + id);
        renderArgs.put("editMode", editMode);
        renderArgs.put("id", id);

        render();
    }


    public static void createDestination(String id) {
        Logger.info("Entrando a createDestination");
        JsonObject jsonObject;
        String addId;
        if(id != null && !id.isEmpty()) {
            addId = "/" + id;
        } else {
            addId = "";
        }
        Logger.info("creando...");
        String name = params.get("name");
        String active = params.get("active");
        active = (active.equals("1")) ? "true" : "false";
        String description = params.get("description");
        String state = params.get("state");
        String country = params.get("country");
        String param=
                "{\n" +
                    " \"name\": \"" + name + "\",\n" +
                    " \"active\": " + active + ",\n" +
                    " \"description\": \"" + description + "\", \n" +
                    " \"stateId\": \"" + state + "\", \n" +
                    " \"countryId\": \"" + country + "\" \n" +
                "}";

        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        Logger.info("Constants.API + offers + addId: >>>" + Constants.API + "/destinations" + addId);
        WS.WSRequest request = WS.url(Constants.API + "/destinations" + addId).authenticate(user, password);
//        WS.WSRequest request = WS.url(Constants.API + "/destinations" + addId);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
//        renderText(res.getJson());
    }

}