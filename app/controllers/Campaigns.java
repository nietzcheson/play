package controllers;

import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;

import services.CatalogConnector;
import util.Constants;


@With(Secure.class)
public class Campaigns extends MasterController {
    public static void list() {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String id = params.get("id-value");
        String campaignName = params.get("campaignName");
        Logger.info("campaignName: >>>" + campaignName);
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
        renderArgs.put("campaignName", campaignName);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);
        render();
    }

    public static void create(Integer id, Integer id_master) {
        boolean editMode;
        if( id != null) {
            editMode = true;
            Logger.info("Modo editar");
            Logger.info("id: >>>" + id);
//            id--;
        }else {
            editMode = false;
            Logger.info("Modo crear");
        }
        CatalogConnector catalogConnector=new CatalogConnector();

        renderArgs.put("countries", catalogConnector.findAllCountries());
        renderArgs.put("callcenters", catalogConnector.findAllCallCenter());
        renderArgs.put("offers", catalogConnector.findAllOffers());
        Logger.info("catalogConnector.findAllUsers(): >>>" + catalogConnector.findAllUsers());
        Logger.info("catalogConnector.findAllUsers(): >>>" + catalogConnector.findAllUsers());
        renderArgs.put("users", catalogConnector.findAllUsers());
        renderArgs.put("segments", catalogConnector.findAllSegments());
        renderArgs.put("reservationGroup", catalogConnector.findAllReservationGroups());
        renderArgs.put("merchants", catalogConnector.findAllMerchants());
        renderArgs.put("editMode", editMode);
        renderArgs.put("id", id);
        renderArgs.put("id_master", id_master);

        render();
    }

    public static void createCampaign() {
        JsonObject jsonObject;
        String id = params.get("id");
//        String cerCustomer = params.get("id");
        Logger.info("empty: >>>" + id.isEmpty());
        Logger.info("lenght: >>>" + id.length());
        Logger.info("lenght: >>>" + id.length());

        String idParamNode;
        String cerCustomerParamNode = "";
        String requestURI;
        Logger.info("id: >>>" + id);
        Logger.info("id: >>>" + id);
        Logger.info("id: >>>" + id);
        Logger.info("id: >>>" + id);
        if(id != null && !id.isEmpty()) {
            Logger.info("creación");
            Logger.info("creación");
            Logger.info("creación");
            Logger.info("creación");

            idParamNode = "  \"id\": " + params.get("id") + ",\n";
            requestURI = "/campaign/update";
        } else {
            idParamNode = "";
            requestURI = "/campaign/create";
        }

        Logger.info("ENTRANDO A CREATECAMPAIGN");
        String[] nbrokers;
        nbrokers = new String[10];
        nbrokers[0]="oflores";
        nbrokers[1]="losorio";
        String name = params.get("name");
        Logger.info("String name: >>>" + name);
        String code = params.get("code");
        Logger.info("String code: >>>" + code);
        String expiration = params.get("expiration");
        Logger.info("String expiration: >>>" + expiration);
        String merchant = params.get("merchant");
        Logger.info("String merchant: >>>" + merchant);
        String offerId = params.get("offerId");
        Logger.info("String offerId: >>>" + offerId);
        String country = params.get("country");
        Logger.info("String country: >>>" + country);
        String segment = params.get("segment");
        Logger.info("String segment: >>>" + segment);
        String reservationGroup = params.get("reservationGroup");
        Logger.info("String reservationGroup: >>>" + reservationGroup);
        //String userToken = params.get("userToken");
        //Logger.info("String userToken: >>>" + userToken);
        String brokerToken = params.get("brokerToken");
        Logger.info("String brokerToken: >>>" + brokerToken);
        String typeFolio = params.get("typeFolio");
        Logger.info("String typeFolio: >>>" + typeFolio);
        String typeCert = params.get("typeCert");
        Logger.info("String typeCert: >>>" + typeCert);
        String masterBroker = params.get("masterBroker");
        Logger.info("String masterBroker: >>>" + masterBroker);
        String brokers = params.get("brokers");
        Logger.info("String brokers: >>>" + brokers);
        String callcenter = params.get("callcenter");
        Logger.info("String callcenter: >>>" + callcenter);
        String description = params.get("description");
        Logger.info("String description: >>>" + description);
        String slug = params.get("slug");
        Logger.info("String slug: >>>" + slug);

        if(masterBroker != null && !masterBroker.equals("")) {
            cerCustomerParamNode = "  \"cerCustomerId\": " + masterBroker + ",\n";
        }

        Logger.info("params.get('name'): >>>" + params.get("name"));

        //String offerNumber = (id == null) ? "" : "/" + id;
        code="dhsjshskjd";
        String param="{\n" +
                idParamNode +
                "  \"name\": \"" + name + "\",\n" +
                "  \"expiration\": " + expiration + ",\n" +
                "  \"merchant\": \"" + merchant + "\",\n" +
                "  \"offerId\": " + offerId + ",\n" +
                "  \"country\": \"" + country + "\",\n" +
                "  \"segment\":" + segment + ",\n" +
                "  \"callcenter\":" + callcenter + ",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"reservationGroup\":" + reservationGroup + ",\n" +
                "  \"slug\": \"" + slug + "\",\n" +
//                "  \"userToken\": \"" + userToken + "\",\n" +
//                "  \"brokerToken\": \"" + userToken + "\",\n" +
//                "  \"userToken\": \"FM4J309N\",\n" +
                "  \"typeFolio\":" + typeFolio + ",\n" +
                "  \"typeCert\":" + typeCert + ",\n" +
                cerCustomerParamNode +
                "  \"brokerId\": [" + brokers + "]\n" +
                "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res ;
        WS.WSRequest request = WS.url(Constants.API + requestURI).authenticate(user, password);
//        WS.WSRequest request = WS.url(Constants.API + requestURI);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        if(res.getStatus()==201 || res.getStatus()==200){
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            renderText(jsonObject);
        }
        renderText(res.getJson());


    }
    public static void createConfigImage(String id) {
        Logger.info("Entrando a createDestination");
        JsonObject jsonObject;
        String urlconfig="";
//        String addId;
//        if(id != null && !id.isEmpty()) {
//            addId = "/" + id;
//        } else {
//            addId = "";
//        }
        Logger.info("creando...");
        String siteimg = params.get("siteimg");
        String idcam = params.get("idcam");
        String certtype = params.get("certtype");
        String idconfig = params.get("idconfig");
        String param=
                    "{\n" +
                        " \"campania\": \"" + idcam + "\",\n" +
                        " \"imageCert\": \"" + siteimg + "\",\n" +
                        " \"certificateType\": \"" + certtype + "\", \n" +
                        " \"imageCustomer\": \"\", \n" +
                        " \"certCustomerId\": \"1096\" \n" +
                        "}";

        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        System.out.println("idconfig"+idconfig+ idconfig.isEmpty());
//        Logger.info("Constants.API: >>>" + Constants.API + "/createImageConfig" + addId);
        if(idconfig!=null & !idconfig.isEmpty()){
            urlconfig="/ecert/updateImageConfig/"+idconfig;
            Logger.info("Constants.API: >>>" + Constants.API + "/ecert/updateImageConfig/" + idconfig);
        }else{
            urlconfig="/ecert/createImageConfig";
            Logger.info("Constants.API: >>>" + Constants.API + "/ecert/createImageConfig");
        }

        WS.WSRequest request = WS.url(Constants.API + urlconfig).authenticate(user, password);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        System.out.println(res);
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
    }
}