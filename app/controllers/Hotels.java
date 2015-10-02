package controllers;

import com.google.gson.JsonObject;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import models.*;
import util.Constants;
@With(Secure.class)
public class Hotels extends MasterController {

    public static void list() {
        Logger.info("Entrando a Hotels.list()");

        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;

        String mode = params.get("mode");
        Logger.info("mode: >>>" + mode);
        String id = params.get("id-value");
        Logger.info("id: >>>" + id);

        String hotelName = params.get("hotelName");
        Logger.info("hotelName: >>>" + hotelName);
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
        renderArgs.put("hotelName", hotelName);
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

        renderArgs.put("editMode", editMode);
        renderArgs.put("id", id);
        renderArgs.put("url", Constants.API);

        render();
    }
    public static void createHotel(String id) {
        JsonObject jsonObject;
        String addId;
        if(id != null && !id.isEmpty()) {
            addId = "/" + id;
        } else {
            addId = "";
        }

        Logger.info("creando..");

        String name = params.get("name");
        Logger.info("String name: >>>" + name);
        String destinationId = params.get("destinationId");
        Logger.info("destinationId: >>>" + destinationId);
        String city = params.get("city");
        Logger.info("city: >>>" + city);
        String zipCode = params.get("zipCode");
        Logger.info("zipCode: >>>" + zipCode);
        String address = params.get("address");
        Logger.info("Address: >>>" + address);
        String website = params.get("website");
        Logger.info("Website: >>>" + website);

        String englishDescription = params.get("englishDescription");
        Logger.info("englishDescription: >>>" + englishDescription);

        String spanishDescription = params.get("spanishDescription");
        Logger.info("spanishDescription: >>>" + spanishDescription);

        String portugueseDescription = params.get("portugueseDescription");
        Logger.info("portugueseDescription: >>>" + portugueseDescription);

        String param="{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"destinationId\": " + destinationId + ",\n" +
                "  \"countryCode\": \"MEX\", \n"+
                "  \"stateId\": 222, \n"+
                "  \"city\": \"" + city + "\",\n" +
                "  \"zipCode\": \"" + zipCode + "\",\n" +
                "  \"address\": \"" + address + "\" \n" + ",\n" +
                "  \"website\": \"" + website + "\" \n" + ",\n" +
                "  \"translations\" : [" +
                        "{\n" +
                        "  \"languageCode\": \"en\",\n" +
                        "  \"websiteDescription\": \"" + englishDescription + "\" \n" +
                        "},\n" +
                        "{\n" +
                        "  \"languageCode\": \"es\",\n" +
                        "  \"websiteDescription\": \"" + spanishDescription + "\" \n" +
                        "},\n" +
                        "{\n" +
                        "  \"languageCode\": \"pt-br\", \n" +
                        "  \"websiteDescription\": \"" + portugueseDescription + "\" \n" +
                        "}" +
                    "]" +
                "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        Logger.info("Constants.API + hotels + addId: >>>" + Constants.API + "/hotels" + addId);
        WS.WSRequest request = WS.url(Constants.API + "/hotels" + addId).authenticate(user, password);
//        WS.WSRequest request = WS.url(Constants.API + "/hotels" + addId);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        Logger.info(res.getString());
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
//        renderText(res.getJson());
    }

}