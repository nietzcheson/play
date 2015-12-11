package controllers;

import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;

import services.OfferServiceConnector;
import util.Constants;

@With(Secure.class)
public class Users extends MasterController {
    public static void list() {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String name = params.get("name");
        String id = params.get("id");
        Logger.info("id: >>>" + id);
        Logger.info("id: >>>" + id);
        Logger.info("name: >>>" + name);

        if(mode != null && !mode.isEmpty()) {
            Logger.info("mode: >>>" + mode);
            if(mode.equals("edit")) {
                successfullyUpdated = true;
                Logger.info("Edition correcta");
            } else if(mode.equals("create")) {
                successfullyCreated = true;
                Logger.info("Creación correcta");
            }
        }

        renderArgs.put("name", name);
        renderArgs.put("id", id);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);
        render();
    }

    public static void create(Integer id) {
        Logger.info("entrando a Ussers.create()");
        boolean editMode;
        if( id != null) {
            editMode = true;
            Logger.info("Modo editar");
            Logger.info("id: >>>" + id);
        } else {
            editMode = false;
            Logger.info("Modo crear");
        }
        renderArgs.put("editMode", editMode);
        renderArgs.put("id", id);
        renderArgs.put("ecertlink", Constants.CertLink);
        Logger.info("was here");

        render();
    }

    /**
     * Crea o edita un usuario. Es llamado con un post hecho en ajax
     *
     * @author Orlando Flores Arroyo
     * @date 13/03/2015
     * @return Renderea la respuesta a la petición que se hace desde el formulario de usuarios en ajax
     */
    public static void createUser() {
        JsonObject jsonObject;
        Logger.info("ENTRANDO A CREATE USER");
        String  id               = params.get("id");
        String  companyName      = params.get("companyName");
        String  responsableName  = params.get("responsableName");
        String  address          = params.get("address");
        String  phone            = params.get("phone");
        String  countryCode      = params.get("countryCode");
        String  stateId          = params.get("stateId");
        String  city             = params.get("city");
        String  userm             = params.get("user");
        String  email            = params.get("email");
        String  passwordm         = params.get("password");
//        String  pass             = params.get("pass");
        String addId;
        if(id != null && !id.isEmpty()) {
            addId = "/" + id;
        } else {
            addId = "";
        }
        String requestURI = "/certCustomer" + addId;
        // Cuerpo de la petición
        String param=
            "{ \n" +
                "  \"companyName\": \""           + companyName    + "\",\n"   +
                "  \"responsableName\": \""     + responsableName + "\",\n" +
                "  \"address\": \""             + address + "\",\n" +
                "  \"phone\": \""               + phone + "\",\n" +
                "  \"countryCode\": \""         + countryCode + "\",\n" +
                "  \"stateId\": \""             + stateId + "\",\n" +
                "  \"city\": \""                + city + "\",\n" +
                "  \"user\": \""                + userm + "\",\n" +
                "  \"password\": \""            + passwordm + "\",\n" +
                "  \"email\": \""               + email + "\" \n"   +
            "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res ;
        Logger.info("Constants.API + requestURI: >>>" + Constants.API + requestURI);
        WS.WSRequest request = WS.url(Constants.API + requestURI).authenticate(user, password);
//        WS.WSRequest request = WS.url(Constants.API + requestURI);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        Logger.info("res.getJson(): >>>" + res.getJson());
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
//        renderText(res.getJson());
    }

}