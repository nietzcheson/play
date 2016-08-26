package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.M4CBBReservacionDTO;
import play.libs.WS;
import play.mvc.Scope;
import play.mvc.With;
import services.M4CBBReservacionesService;
import dao.M4CBBReservacionesDTO;
import util.Constants;

import java.util.List;

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

        if(templateId != null && year != null && day != null){
            List<M4CBBReservacionesDTO> reservations = new M4CBBReservacionesService().failuresDTO(templateId, year);
            renderArgs.put("reservations", reservations);
        }

        renderArgs.put("editMode", editMode);
        renderArgs.put("templateId", templateId);
        renderArgs.put("year", year);
        renderArgs.put("day", day);
        //renderArgs.put("failures", m4CBBReservacionesService.getFailures());

        render();
    }

    public static void createBulkbank(){

        String quantity[] = params.getAll("quantity");
        String ids[] = params.getAll("ids");
        String weekDayId[] = params.getAll("weekDayId");
        JsonObject bulkbank = new JsonObject();
        String templateId = params.get("templateId");
        bulkbank.addProperty("templateId", templateId);
        String year = params.get("year");
        bulkbank.addProperty("year", year);
        String weekDay = params.get("weekDay");
        bulkbank.addProperty("weekDay", weekDay);

        Integer diff = ids.length - quantity.length;
        JsonArray weeks = new JsonArray();
        JsonObject week;

        for(int i = 0; i < quantity.length; i++){

            if(Integer.parseInt(quantity[i]) > 0  || Integer.parseInt(ids[i+diff])!= 0){

                week = new JsonObject();
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

        try{
            String params = bulkbank.toString();
            WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/edit").authenticate(user, password);
            request.body = params;
            request.mimeType = "application/json";
            res = request.post();
            System.out.println(res);
            bulkbank = res.getJson().getAsJsonObject();
            bulkbank.addProperty("responsestatus", res.getStatus());
            renderText(bulkbank);

        }catch(Exception excepcion) {
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

        String firstnames[] = params.getAll("firstname");
        String lastnames[] = params.getAll("lastname");
        String id = params.get("bulkBankId");
        JsonObject bulkbank = new JsonObject();
        JsonArray names = new JsonArray();
        JsonObject name;

        for (int i=0; i<firstnames.length; i++){
            if(!firstnames[i].equals("")){
                name = new JsonObject();
                name.addProperty("firstname", firstnames[i]);
                name.addProperty("lastname", lastnames[i]);
                names.add(name);
            }
        }

        bulkbank.add("names", names);
        bulkbank.addProperty("userName", Scope.Session.current().get("username"));
        WS.HttpResponse res;

        try {

            String params = bulkbank.toString();
            WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/breakdown/"+id).authenticate(user, password);
            request.body = params;
            request.mimeType = "application/json";
            res = request.post();
            bulkbank = res.getJson().getAsJsonObject();
            bulkbank.addProperty("responsestatus", res.getStatus());
            renderText(bulkbank);

        } catch (Exception excepcion) {
            renderText("error");
        }
    }

    public static void resendBulkBank(int id)
    {
        JsonObject resend = new M4CBBReservacionesService().resendBulkBank(id);
        renderText(resend);
    }

    public static void breakdownFailures()
    {
        String reservations = new M4CBBReservacionesService().failuresJson(Long.parseLong(params.get("template")), Integer.parseInt(params.get("year")));

        renderText(reservations);
    }

    public static void breakdownTest()
    {
        WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/breakdown-test").authenticate(user, password);
        request.body = params;
        request.mimeType = "application/json";

        WS.HttpResponse response = request.post();

        JsonObject reservacion = new JsonObject();

        if(response.getStatus() == 200){

            reservacion = response.getJson().getAsJsonObject();
        }

        //response.getJson().getAsJsonObject();

        renderText(reservacion);



    }

}
