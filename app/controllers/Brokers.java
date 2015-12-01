package controllers;

import com.google.gson.JsonObject;
import play.Logger;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.With;
import util.Constants;

/**
 * Created by Orlando on 24/03/2015.
 */
@With(Secure.class)
public class Brokers extends MasterController {
    public static void list(Integer id) {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String name = params.get("name");
//        String idBroker = params.get("id-value");
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

    /**
     * @author Orlando Flores Arroyo
     * @param brokerId
     * @date 24/03/2015
     * @description Crea o edita un broker
     */
    public static void create(String masterBrokerId, String brokerId) {
        boolean editMode;
        if( brokerId != null) {
            editMode = true;
            Logger.info("Modo editar");
            Logger.info("brokerId: >>>" + brokerId);
        } else {
            editMode = false;
            Logger.info("Modo crear");
        }
        renderArgs.put("editMode", editMode);
        renderArgs.put("brokerId", brokerId);
        renderArgs.put("masterBrokerId", masterBrokerId);
        Logger.info("was here");
        render();
    }

    public static void createBroker() {
        JsonObject jsonObject;
        Logger.info("ENTRANDO A Broker");
        String  id               = params.get("id");
        String  name             = params.get("broker-name");
        String  userb             = params.get("username");
        String  master           = params.get("master");
        String  email            = params.get("broker-email");
        String  passwordb         = params.get("password");
        String  campaign         = params.get("campaigns");
        if(passwordb.isEmpty()) {
            passwordb= params.get("pass_oculto");
        }
        String addId;
        if(id != null && !id.isEmpty()) {
            addId = "/" + id;
        } else {
            addId = "";
        }
        String requestURI = "/certLogin" + addId;
        // Cuerpo de la petición
//        String textpass="";
//        if(!password.isEmpty()){
//            textpass=textpass.concat("  \"password\": \""       + password + "\",\n" );
//        }
        String param=
                "{ \n" +
                        "  \"user\": \""           + userb    + "\",\n"   +
                        "  \"password\": \""           + passwordb    + "\",\n"   +
//                        textpass +
                        "  \"email\": \""          + email + "\",\n" +
                        "  \"name\": \""           + name + "\",\n" +
                        "  \"campana\": \""        + campaign + "\", \n" +
                        "  \"certCustomerId\": \""   + master + "\" \n" +
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
//        renderText(res.getJson());
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
    }
}
