package controllers;

import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import models.*;
import util.Constants;
@With(Secure.class)
public class Serials extends MasterController {

    public static void list(Integer id) {
        Boolean successfullyCreated = false;
        String QTYofSerials = params.get("QTYofSerials");
//        String offerName = params.get("offerName");

        if(QTYofSerials != null && !QTYofSerials.isEmpty()) {
            successfullyCreated = true;
        }


        renderArgs.put("QTYofSerials", QTYofSerials);
        renderArgs.put("success", successfullyCreated);
        renderArgs.put("idCampaign", id);
        render();
    }
    public static void create(Integer idCampaign, boolean inventory) {
        boolean editMode;
        if( idCampaign != null) {
            editMode = true;
            Logger.info("Modo editar");
        } else {
            editMode = false;
            Logger.info("Modo crear");
        }
        renderArgs.put("editMode", editMode);
        renderArgs.put("inventory", inventory);
        renderArgs.put("idCampaign", idCampaign);

        render();
    }

    public static void view(Integer id) {

        Logger.info("Entrando a view");

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
        render();
    }

    public static void createSerials(String id) {
        Logger.info("creando folios..");
        JsonObject jsonObject;
        String quantityFolios = params.get("quantityFolios");
        String campaignId = params.get("campaignId");
        String param="";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        WS.WSRequest request;
        System.out.println("Campaign"+campaignId);
        if(campaignId!=null && !campaignId.isEmpty()){
            param="" +
                    "{\n" +
                    "  \"quantityFolios\": " + quantityFolios + ",\n" +
                    "  \"campaignId\": " + campaignId + "\n" +
                    "}";
            request = WS.url(Constants.API + "/campaign/certificate/create").authenticate(user, password);
        }else{
            param="" +
                    "{\n" +
                    "  \"quantityFolios\": " + quantityFolios + "\n" +
                    "}";
            request = WS.url(Constants.API + "/campaign/batchcertificate/create").authenticate(user, password);
        }
//        WS.WSRequest request = WS.url(Constants.API + "/campaign/certificate/create");
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        Logger.info("res.getJson(): >>>" + res.getJson());
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus());
        System.out.println(res.getJson());
        renderText(jsonObject);
    }
    public static void createBatch(String id) {
        JsonObject jsonObject;
        Logger.info("Creando lotes..");
        String idCampaign = params.get("idCampaign");
        String start = params.get("start");
        String end = params.get("end");
        String param="" +
                "{\n" +
                "  \"idCampaign\": " + idCampaign + ",\n" +
                "  \"start\": " + start + ",\n" +
                "  \"end\": " + end + "\n" +
                "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        System.out.println(Constants.API + "/campaign/"+id+"/batch/create");
        WS.WSRequest request = WS.url(Constants.API + "/campaign/"+id+"/batch/create").authenticate(user, password);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus());
        System.out.println(res.getJson());
        renderText(jsonObject);
    }

}