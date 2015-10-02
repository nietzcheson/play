package controllers;

import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;

import services.OfferServiceConnector;
import util.Constants;

@With(Secure.class)
public class Offers extends MasterController {

    public static void list() {

        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String offerName = params.get("offerName");
        String id = params.get("id-value");
        Logger.info("id: >>>" + id);

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

        OfferServiceConnector offerServiceConnector=new OfferServiceConnector();
        renderArgs.put("id", id);
        renderArgs.put("offers", offerServiceConnector.findAll());
        renderArgs.put("offerName", offerName);
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

        render();
    }

    public static void createOffer(String id) {
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
        String mealPlanId = params.get("mealPlanId");
        Logger.info("mealPlanId: >>>" + mealPlanId);
        String price = params.get("price");
        Logger.info("price: >>>" + price);
        String activationFee = params.get("activationFee");
        Logger.info("activationFee: >>>" + activationFee);
        String countryCode = params.get("countryCode");
        Logger.info("countryCode: >>>" + countryCode);
        String taxes = params.get("taxes");
        Logger.info("taxes: >>>" + taxes);
        String destinationIds = params.get("destinationIds");
        Logger.info("destinationIds: >>>" + destinationIds);
        String status = params.get("status");
        Logger.info("status: >>>" + status);
        String transportationId = params.get("transportationId");
        Logger.info("transportationId: >>>" + transportationId);
        String description = params.get("description");
        Logger.info("description: >>>" + description);
        String hookId = params.get("hookId");
        Logger.info("hookId: >>>" + hookId);
        String nights = params.get("nights");
        Logger.info("nights: >>>" + nights);

        String englishDescription = params.get("englishDescription");
        Logger.info("englishDescription: >>>" + englishDescription);
        String englishDetails = params.get("englishDetails");
        Logger.info("englishDetails: >>>" + englishDetails);
        String englishTerms = params.get("englishTerms");
        Logger.info("englishTerms: >>>" + englishTerms);

        String spanishDescription = params.get("spanishDescription");
        Logger.info("spanishDescription: >>>" + spanishDescription);
        String spanishDetails = params.get("spanishDetails");
        Logger.info("spanishDetails: >>>" + spanishDetails);
        String spanishTerms = params.get("spanishTerms");
        Logger.info("spanishTerms: >>>" + spanishTerms);

        String portugueseDescription = params.get("portugueseDescription");
        Logger.info("portugueseDescription: >>>" + portugueseDescription);
        String portugueseDetails = params.get("portugueseDetails");
        Logger.info("portugueseDetails: >>>" + portugueseDetails);
        String portugueseTerms = params.get("portugueseTerms");
        Logger.info("portugueseTerms: >>>" + portugueseTerms);
        if(name!=null && !name.isEmpty()){
            name="  \"name\": \"" + name + "\",\n";
        }
        String param="{\n" +
                name+
//                "  \"name\": \"" + name + "\",\n" +
                "  \"mealPlanId\": " + mealPlanId + ",\n" +
                "  \"price\": " + price + ",\n" +
                "  \"activationFee\": " + activationFee + ",\n" +
                "  \"taxes\": " + taxes + ",\n" +
                "  \"countryCode\": \"" + countryCode + "\",\n" +
                "  \"destinationIds\": " + destinationIds + ",\n" +
//                "  \"destinationIds\": [1],\n" +
                "  \"status\": " + status + ",\n" +
                "  \"transportationId\": " + transportationId + ",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"hookId\": " + hookId + ",\n" +
                "  \"nights\": " + nights + ",\n" +

                "  \"translations\" : [" +
                    "{\n" +
                        "  \"languageCode\": \"en\",\n" +
                        "  \"websiteDescription\": \"" + englishDescription + "\",\n" +
                        "  \"websiteDetails\": \"" + englishDetails + "\",\n" +
                        "  \"websiteTerms\": \"" + englishTerms + "\"\n" +
                    "},\n" +
                    "{\n" +
                        "  \"languageCode\": \"es\",\n" +
                        "  \"websiteDescription\": \"" + spanishDescription + "\",\n" +
                        "  \"websiteDetails\": \"" + spanishDetails + "\",\n" +
                        "  \"websiteTerms\": \"" + spanishTerms + "\"\n" +
                    "},\n" +
                    "{\n" +
                        "  \"languageCode\": \"pt-br\", \n" +
                        "  \"websiteDescription\": \"" + portugueseDescription + "\", \n" +
                        "  \"websiteDetails\": \"" + portugueseDetails + "\", \n" +
                        "  \"websiteTerms\": \"" + portugueseTerms + "\" \n" +
                    "}" +
                "]" +
        "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        Logger.info("Constants.API + offers + addId: >>>" + Constants.API + "/offers" + addId);
        WS.WSRequest request = WS.url(Constants.API + "/offers" + addId).authenticate(user, password);
//        WS.WSRequest request = WS.url(Constants.API + "/offers" + addId);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
//        res.getJson().getAsJsonObject().addProperty("draw", "djdkd");
//        renderText(res.getJson());


    }

}