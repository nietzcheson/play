package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import play.libs.WS;
import play.mvc.Scope;
import play.mvc.With;
import util.Constants;

/**
 * Created by desarrollo1 on 18/04/2016.
 */
@With(Secure.class)
public class Bulkbank extends MasterController {

    public static void list(Integer isOTA) {
        if(isOTA==null)
            isOTA=0;
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String id = params.get("id-value");
        String hotelName = params.get("Bulkbank");
        if(mode != null && !mode.isEmpty()) {
            if(mode.equals("edit"))
                successfullyUpdated = true;
            else if(mode.equals("create"))
                successfullyCreated = true;
        }
        renderArgs.put("id", id);
        renderArgs.put("isOTA", isOTA);
        renderArgs.put("hotelName", hotelName);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);
//        renderTemplate("/Bulkbank/list-react.html");
        render();
    }

    public static void create(Long templateId, Integer year, Integer day) {
        boolean editMode;
        if( templateId != null)
            editMode = true;
        else
            editMode = false;
        renderArgs.put("editMode", editMode);
        renderArgs.put("templateId", templateId);
        renderArgs.put("year", year);
        renderArgs.put("day", day);
        render();
    }

    public static void createBulkBank(){
        String quantity[]=params.getAll("quantity");
        String ids[] =params.getAll("ids");
        String weekDayId[] =params.getAll("weekDayId");
        JsonObject bulkbank=new JsonObject();
        String templateId= params.get("templateId");
        bulkbank.addProperty("templateId", templateId);
        String year= params.get("year");
        bulkbank.addProperty("year", year);
        String weekDay= params.get("weekDay");
        bulkbank.addProperty("weekDay", weekDay);

        Integer diff= ids.length - quantity.length;
        JsonArray weeks= new JsonArray();
        JsonObject week;
        for(int i=0; i < quantity.length; i++){
            if(Integer.parseInt(quantity[i]) > 0  || Integer.parseInt(ids[i+diff])!=0){
                week= new JsonObject();
                week.addProperty("weekDayId", weekDayId[i+diff]);
                week.addProperty("quantity", quantity[i]);
                if(Integer.parseInt(ids[i+diff]) != 0)
                    week.addProperty("id", ids[i+diff]);

                weeks.add(week);
            }
        }
        bulkbank.addProperty("username", Scope.Session.current().get("username"));
        bulkbank.add("weekDTOList", weeks);
        WS.HttpResponse res;
        try {
            String params = bulkbank.toString();
            WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/edit").authenticate(user, password);
            request.body = params;
            request.mimeType = "application/json";
            res = request.post();
            System.out.println(res);
            bulkbank = res.getJson().getAsJsonObject();
            bulkbank.addProperty("responsestatus", res.getStatus());
            renderText(bulkbank);
        } catch (Exception excepcion) {
            renderText("error");
        }
    }


    public static void createBulkBankOtas(Long id){
        JsonObject bulkbank=new JsonObject();
        String quantity = params.get("quantity");
        bulkbank.addProperty("quantity", quantity);
        String checkin = params.get("checkin");
        bulkbank.addProperty("checkIn", checkin);
        String checkout = params.get("checkout");
        bulkbank.addProperty("checkOut", checkout);
        bulkbank.addProperty("username", Scope.Session.current().get("username"));
        String id_text="";
        if(id==null){
            String templateId= params.get("templateId");
            bulkbank.addProperty("templateId", templateId);
        }else
            id_text="/"+id;
        WS.HttpResponse res;
        try {
            String params = bulkbank.toString();
            System.out.println(params);
            WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank"+id_text).authenticate(user, password);
            request.body = params;
            request.mimeType = "application/json";
            res = request.post();
            System.out.println(res);
            bulkbank = res.getJson().getAsJsonObject();
            bulkbank.addProperty("responsestatus", res.getStatus());
            renderText(bulkbank);
        } catch (Exception excepcion) {
            renderText("error");
        }
    }

    public static void createBreakdown(){
        String firstnames[]=params.getAll("firstname");
        String lastnames[]=params.getAll("lastname");
        Gson gson = new Gson();
        String id = params.get("bulkBankId");
        JsonObject bulkbank = new JsonObject();
        JsonArray names= new JsonArray();
        JsonObject name;
        for (int i=0; i<firstnames.length; i++){
            if(!firstnames[i].equals("")){
                name= new JsonObject();
                name.addProperty("firstname", firstnames[i]);
                name.addProperty("lastname", lastnames[i]);
                names.add(name);
            }
        }
        bulkbank.add("names", names);
        bulkbank.addProperty("userName", Scope.Session.current().get("username"));
        WS.HttpResponse res;
        try {
            String params =bulkbank.toString();
            WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/breakdown/"+id).authenticate(user, password);
            request.body = params;
            request.mimeType = "application/json";
            res = request.post();
            System.out.println("Response");
            bulkbank = res.getJson().getAsJsonObject();
            System.out.println(bulkbank);
            bulkbank.addProperty("responsestatus", res.getStatus());
            renderText(bulkbank);
        } catch (Exception excepcion) {
            renderText("error");
        }
    }

}
