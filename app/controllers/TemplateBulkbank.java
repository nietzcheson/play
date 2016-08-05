package controllers;

import com.google.gson.JsonObject;
import play.Logger;
import play.libs.WS;
import play.mvc.With;
import util.Constants;

import java.text.ParseException;

/**
 * Created by desarrollo1 on 20/04/2016.
 */
@With(Secure.class)
public class TemplateBulkbank extends MasterController
{

    public static void list()
    {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String id = params.get("id");
        String templateName = params.get("name");

        if(mode != null && !mode.isEmpty()) {

            if(mode.equals("edit")) {
                successfullyUpdated = true;
            } else if(mode.equals("create")) {
                successfullyCreated = true;
            }
        }

        renderArgs.put("id", id);
        renderArgs.put("isOta", Security.check("ListarBulkBankOTAS"));
        renderArgs.put("templateName", templateName);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);

        render();
    }

    public static void create(/*Long id*/)
    {

        boolean editMode;
        /*if( id != null)
            editMode = true;
        else
            editMode = false;*/
        //renderArgs.put("editMode", editMode);
        //renderArgs.put("template", id);
        renderArgs.put("isOta", Security.check("ListarBulkBankOTAS"));
        render();
    }

    public static void createTemplate(Long id, Integer isOTA) throws ParseException
    {

        if(isOTA==null)
            isOTA=0;
        JsonObject jsonObject= new JsonObject();
        String addId;
        if(id != null)
            addId = "/" + id;
        else
            addId = "";

        String name = params.get("name");
        jsonObject.addProperty("name", name);
        String mealPlanId = params.get("mealPlanId");
        jsonObject.addProperty("mealPlanId", mealPlanId);
        String callCenterId = params.get("callCenterId");
        jsonObject.addProperty("callCenterId", callCenterId);
        String unitId = params.get("unitId");
        jsonObject.addProperty("unitId", unitId);
        String splitId = params.get("splitId");
        jsonObject.addProperty("splitId", splitId);
        String adults = params.get("adults");
        jsonObject.addProperty("adults", adults);
        String children = params.get("children");
        jsonObject.addProperty("children", children);
        String rate = params.get("rate");
        jsonObject.addProperty("rate", rate);
        String observations = params.get("observations");
        jsonObject.addProperty("observations", observations);
        String clubId = params.get("clubId");
        jsonObject.addProperty("clubId", clubId);
        WS.WSRequest request;
        WS.HttpResponse res;
        request = WS.url( Constants.API_Bulkbank + "/templateBulkBank"+ addId ).authenticate(user, password);
        System.out.println(Constants.API + "/templateBulkBank"+ addId);
        String params=jsonObject.toString();
        Logger.info("Parametros: " +params);
        try{
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
            System.out.println(res.getJson());
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            jsonObject.addProperty("user", user);
            renderText(jsonObject);
        }catch(Exception excepcion){
            renderText(excepcion);
        }
        renderArgs.put("isOta", isOTA);

    }

    public static void edit(Long id)
    {
        renderArgs.put("template", id);
        render();
    }
}
