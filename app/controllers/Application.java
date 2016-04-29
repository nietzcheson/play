package controllers;
import com.google.gson.JsonObject;
import play.Logger;
import play.i18n.Lang;
import play.libs.WS;
import java.io.*;
import java.util.*;

import play.mvc.Scope;
import services.AsteriskServer;
import util.Constants;
import controllers.Security;

public class Application extends MasterController {

    public static void addPhoto(File photo){
        Logger.info("phtoo==>" + photo.getAbsolutePath());
    }
    public static boolean printParams(){
        Map<String,String[]>parameters=params.data;

        for(Map.Entry<String,String[]> entry:parameters.entrySet()){
            Logger.info(entry.getKey()+":"+entry.getValue());
        }
        return true;
    }
    public static void index() {
        //Checar permisos
        if(Security.check("ListarCliente"))
            redirect("/clients");
        else if(Security.check("ListarMasterBroker"))
            redirect("/master-brokers");
        else if(Security.check("ListarOferta"))
            redirect("/offers");
        else if(Security.check("ListarCampania"))
            redirect("/campaigns");
        else if(Security.check("ListarDestino"))
            redirect("/destinations");
        else if(Security.check("ListarHotel"))
            redirect("/hotels");
        else if(Security.check("SubirImagenesEcert"))
            redirect("/ecerts");
        else if(Security.check("ListarInventario"))
            redirect("/serials");
        else if(Security.check("SubirClientes"))
            redirect("/upload-leads");
        else if(Security.check("ListarBulkBank"))
            redirect("/templateBulkbank");
        else login();
    }

    public static void login() {
        render();
    }

    public static void recover() {
        Logger.info("recover.....");
        render();
    }

    public static void uploadLeads() {
        render();
    }

    public static void successfulRecovery() {
        renderTemplate("/Application/successful-recovery.html");
    }

    public static void change_language(String lan)
    {
        Lang.change(lan);
    }

    public static void campaignsSearch(){
        String q = params.get("q");
        WS.WSRequest req = WS.url(Constants.API + "/campaign/list?q="+q).authenticate(user, password);
        System.out.println(Constants.API + "/campaign/list?pageLength=100&q="+q);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void offersSearch(){
        String q = params.get("q");
        WS.WSRequest req = WS.url(Constants.API + "/offers?q="+q).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void certCustomerSearch(String id) {
        String q = params.get("q");
        WS.WSRequest req = WS.url(Constants.API + "/certCustomer/search?q="+q).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void serialsList(String id) {
        String serialNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/campaign" + serialNumber+"/seriesCertificate?pageLength=500").authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void TableList(){
        String url =params.get("url");
        WS.WSRequest req = WS.url(Constants.API + url ).authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info("Listado de : " + Constants.API + url);
        renderText(res.getJson());
    }
    public static void TableListBulkBank(){
        String url =params.get("url");
        WS.WSRequest req = WS.url(Constants.API_Bulkbank + url ).authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info("Listado de : " + Constants.API_Bulkbank + url);
        renderText(res.getJson());
    }
    public static void UsersGroupList(){
        String user= Scope.Session.current().get("username");
        WS.WSRequest req = WS.url(Constants.API +"/users?group_username="+user ).authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info("Usuarios : " + Constants.API +"/users?group_username="+user );
        renderText(res.getJson());
    }

    public static void brokerShow(String id) {
        String userNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/certLogin" + userNumber).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void brokersList(String id) {
        String brokerNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/certLogin" + brokerNumber).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void masterbrokersList(String id) {
        String brokerNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/certLogin/Mb" + brokerNumber).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void hotelDelete(String id, String token) {
        System.out.println("token:"+token);
        String hotelNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/hotels" + hotelNumber + "/images/"+token).authenticate(user, password);
        WS.HttpResponse res = req.delete();
        Logger.info(Constants.API + "/hotels" + hotelNumber + "/images/"+token);
        renderText(res.getJson());
    }
    public static void certconfigList(String id) {
        String ecertNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/ecert" + ecertNumber).authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info("Constants.API + \"/ecert\" + ecertNumber: >>>" + Constants.API + "/ecert" + ecertNumber);
        renderText(res.getJson());
    }

    public static void certificate(String id) {
        String certificateNumber = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/certificates" + certificateNumber).authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
    }

    public static void saleShow(String id) {
        String saleId = (id == null) ? "" : id;
        WS.HttpResponse res;
        WS.WSRequest request = WS.url(Constants.API + "/sales/"+ saleId+"/detail").authenticate(user, password);
        JsonObject audit= MasterController.audit(new Integer(id), "Clientes", "Detalle Venta", "Visualizar venta", "Visualizar venta");
        String params = audit.toString();
        request.body = params;
        request.mimeType = "application/json";
        res = request.post();
        renderText(res.getJson());
    }
    public static void notesList(String id) {
        String noteId;
        JsonObject audit;
        String client =params.get("client");
        if(id == null){
            noteId="/list";
            Logger.info("Listar notas: "+Constants.API + "/customers/"+ client+"/notes"+noteId);
            audit =MasterController.audit(0, "Clientes", "Detalle Cliente", "Visualizar notas", "Visualizar notas");
        }else{
            noteId="/"+id;
            Logger.info("Detalle nota: "+Constants.API + "/customers/"+ client+"/notes"+noteId);
            audit=MasterController.audit(new Integer(id), "Clientes", "Detalle Cliente", "Visualizar notas", "Detalle nota");
        }
        WS.HttpResponse res;
        WS.WSRequest request = WS.url(Constants.API + "/customers/"+ client+"/notes"+noteId).authenticate(user, password);
        String params = audit.toString();
        Logger.info("Parametros: "+params);
        request.body = params;
        request.mimeType = "application/json";
        res = request.post();
        renderText(res.getJson());
    }

    public static void offersList(String id) {
        printParams();
        JsonObject jsonObject=new JsonObject();
        JsonObject jsonResponse;
        Integer pageLength =params.get("length",Integer.class);
        String searchValue = params.get("search[value]");
//        String searchable = params.get("searchable");
//        String start = params.get("start");
        String draw = params.get("draw");
        String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
        String page=String.valueOf(params.get("start",Integer.class)/pageLength +1);

        String orderColumn = params.get("order[0][column]");
        String orderBy = "";
        String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
        Logger.info("orderColumn: >>>" + orderColumn);
        if(orderColumn != null){
            if(orderColumn.equals("0")){
                Logger.info("ordercolumn vale: >>>" + "0");
               orderBy = "&orderBy=name";
            } else if(orderColumn.equals("1")) {
                Logger.info("ordercolumn vale: >>>" + "1");
                orderBy = "&orderBy=dateUpdated";
            }
        } else {
            Logger.info("ordercolumn es null");
        }
        Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));
        String queryString =
                "pageLength=" + pageLength +
                "&page=" + page +
                query +
                order +
                orderBy;
        queryString = "/offers" + "?" + queryString;
        Logger.info("queryString: >>>" + queryString);
        WS.WSRequest req = WS.url(Constants.API + queryString).authenticate(user, password);
        WS.HttpResponse res = req.get();
        jsonResponse=res.getJson().getAsJsonObject();
        jsonObject.addProperty("draw", draw);
        if(jsonResponse.get("totalElements") != null) {

            jsonObject.addProperty("recordsTotal",jsonResponse.get("totalElements").getAsLong());
            jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
        } else {
            jsonObject.addProperty("recordsTotal", 0);
            jsonObject.addProperty("recordsFiltered", 0);
        }
            jsonObject.add("data",jsonResponse.get("elements"));

        renderText(jsonObject);
    }

    public static void usersList(String id) {
        printParams();
        JsonObject jsonObject=new JsonObject();
        JsonObject jsonResponse;
        Integer pageLength =params.get("length",Integer.class);
        String searchValue = params.get("search[value]");
//        String searchable = params.get("searchable");
//        String start = params.get("start");
        String draw = params.get("draw");
        String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
        String page=String.valueOf(params.get("start",Integer.class)/pageLength +1);

        String orderColumn = params.get("order[0][column]");
        String orderBy = "";
        String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
        Logger.info("orderColumn: >>>" + orderColumn);
        if(orderColumn != null){
            if(orderColumn.equals("0")){
                Logger.info("ordercolumn vale: >>>" + "0");
                orderBy = "&orderBy=companyName";
            } else if(orderColumn.equals("1")) {
                Logger.info("ordercolumn vale: >>>" + "1");
                orderBy = "&orderBy=responsableName";
            }else if(orderColumn.equals("2")) {
                Logger.info("ordercolumn vale: >>>" + "1");
                orderBy = "&orderBy=user";
            }else if(orderColumn.equals("3")) {
                Logger.info("ordercolumn vale: >>>" + "1");
                orderBy = "&orderBy=id";
            }
        } else {
            Logger.info("ordercolumn es null");
        }
        Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));
        String queryString =
                "pageLength=" + pageLength +
                        "&page=" + page +
                        query +
                        order +
                        orderBy;
        queryString = "/certCustomer" + "?" + queryString;
        Logger.info("queryString: >>>" + queryString);
        WS.WSRequest req = WS.url(Constants.API + queryString).authenticate(user, password);
        WS.HttpResponse res = req.get();
        jsonResponse=res.getJson().getAsJsonObject();
        jsonObject.addProperty("draw", draw);
        if(jsonResponse.get("totalElements") != null) {
            jsonObject.addProperty("recordsTotal",jsonResponse.get("totalElements").getAsLong());
            jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
        } else {
            jsonObject.addProperty("recordsTotal", 0);
            jsonObject.addProperty("recordsFiltered", 0);
        }
        jsonObject.add("data",jsonResponse.get("elements"));

        renderText(jsonObject);
    }

    public static void campaignsList(String id) {
        String draw = params.get("draw");
        String queryString = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonResponse;
        if(draw != null) {
            Integer pageLength = params.get("length", Integer.class);
            String searchValue = params.get("search[value]");
//            String searchable = params.get("searchable");
//            String start = params.get("start");
            String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
            String page = String.valueOf(params.get("start", Integer.class) / pageLength + 1);
            String orderColumn = params.get("order[0][column]");
            String orderBy = "";
            String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
            Logger.info("orderColumn: >>>" + orderColumn);
            Logger.info("order: >>>" + order);
            if (orderColumn != null) {
                if (orderColumn.equals("0")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                    orderBy = "&orderBy=IDCAMPANIA";
                } else if (orderColumn.equals("2")) {
                    Logger.info("ordercolumn vale: >>>" + "2");
                    orderBy = "&orderBy=DATE_UPDATED";
                } else {
                    orderBy = "";
                }
            } else {
                Logger.info("ordercolumn es null");
            }

            Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));


            queryString =
                    "?" +
                    "pageLength=" + pageLength +
                    "&page=" + page +
                    query +
                    order +
                    orderBy;

            queryString = "/campaign/list" + queryString;

            Logger.info("queryString: >>>" + queryString);
            WS.WSRequest req = WS.url(Constants.API + queryString).authenticate(user, password);
            WS.HttpResponse res = req.get();
            jsonResponse = res.getJson().getAsJsonObject();

            jsonObject.addProperty("draw", draw);
            if (jsonResponse.get("totalElements") != null) {

                jsonObject.addProperty("recordsTotal", jsonResponse.get("totalElements").getAsLong());
                jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
            } else {
                jsonObject.addProperty("recordsTotal", 0);
                jsonObject.addProperty("recordsFiltered", 0);
            }
            jsonObject.add("data", jsonResponse.get("elements"));

            renderText(jsonObject);
        } else  {
            String campaignId = (id == null) ? "" : "/" + id;
            WS.WSRequest req = WS.url(Constants.API + "/campaign" + campaignId).authenticate(user, password);
            WS.HttpResponse res = req.post();
            renderText(res.getJson());
        }
    }

    public static void inventoryList(String id) {
        String draw = params.get("draw");
        String queryString = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonResponse;
        if(draw != null) {
            Integer pageLength = params.get("length", Integer.class);
            String searchValue = params.get("search[value]");
            String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
            String page = String.valueOf(params.get("start", Integer.class) / pageLength + 1);

            String orderColumn = params.get("order[0][column]");
            String orderBy = "";
            String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
            Logger.info("orderColumn: >>>" + orderColumn);
            Logger.info("order: >>>" + order);
            if (orderColumn != null) {
                if (orderColumn.equals("0")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                orderBy = "&orderBy=id";
                } else if (orderColumn.equals("1")) {
                    Logger.info("ordercolumn vale: >>>" + "2");
                orderBy = "&orderBy=createdDate";
                } else {
                    orderBy = "";
                }
            } else {
                Logger.info("ordercolumn es null");
            }

            Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));


            queryString =
            "?" +
            "pageLength=" + pageLength +
            "&page=" + page +
            query +
            order +
            orderBy;

            queryString = "/inventory" + queryString;

            Logger.info("queryString: >>>" + queryString);
            WS.WSRequest req = WS.url(Constants.API + queryString).authenticate(user, password);
            WS.HttpResponse res = req.get();
            jsonResponse = res.getJson().getAsJsonObject();

            jsonObject.addProperty("draw", draw);
            if (jsonResponse.get("totalElements") != null) {

                jsonObject.addProperty("recordsTotal", jsonResponse.get("totalElements").getAsLong());
            jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
            } else {
                jsonObject.addProperty("recordsTotal", 0);
            jsonObject.addProperty("recordsFiltered", 0);
            }
        jsonObject.add("data", jsonResponse.get("elements"));

        renderText(jsonObject);
        } else  {
            String inventoryId = (id == null) ? "" : "/" + id;
        WS.WSRequest req = WS.url(Constants.API + "/inventory" + inventoryId + "").authenticate(user, password);
        WS.HttpResponse res = req.get();
        renderText(res.getJson());
        }

    }

    public static void paymentList(String bookingid, String paymentid) {
        WS.HttpResponse res;
        if(paymentid == null){
            paymentid = (paymentid == null) ? "" : "/" + paymentid;
            WS.WSRequest req = WS.url(Constants.API+"/sales/"+bookingid+"/payments"+paymentid).authenticate(user, password);
            res = req.get();
            Logger.info(Constants.API+"/sales/"+bookingid+"/payments");
        }else{
            paymentid = (paymentid == null) ? "" : "/" + paymentid;
            JsonObject jsonObject= MasterController.audit(new Integer(bookingid), "M4C", "PAGOS", "CONSULTAR", "Consultar pagos");
            String params=jsonObject.toString();
            WS.WSRequest request = WS.url(Constants.API+"/sales/"+bookingid+"/payments"+paymentid).authenticate(user, password);
            request.body = params;
            request.mimeType="application/json";
            res = request.post();
            Logger.info(Constants.API+"/sales/"+bookingid+"/payments");
        }
        renderText(res.getJson());
    }

    public static void leadsList(String id) {
        String draw = params.get("draw");
        String queryString = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonResponse;
        System.out.println(draw);
        if(draw!=null) {
            Integer pageLength = params.get("length", Integer.class);
            String searchValue = params.get("search[value]");
            String []values = searchValue.split(",");
            String from = "";
            String to = "";
            if(values.length>1){
                searchValue=values[0];
                from =values[1];
                to =values[2];
            }
            String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
            query += (!from.equals(null) && !from.isEmpty()) ? "&fromDate=" + from : "";
            query += (!to.equals(null) && !to.isEmpty()) ? "&toDate=" + to : "";
            String page = String.valueOf(params.get("start", Integer.class) / pageLength + 1);
            String orderColumn = params.get("order[0][column]");
            String orderBy = "";
            String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]").toUpperCase()  : "";
            Logger.info("orderColumn: >>>" + orderColumn);
            Logger.info("order: >>>" + order);
            if (orderColumn != null) {
                if (orderColumn.equals("0")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                    orderBy = "&orderBy=firstname";
                }else if (orderColumn.equals("1")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                    orderBy = "&orderBy=idbooking";
                }else if (orderColumn.equals("2")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                    orderBy = "&orderBy=numcert";
                }else if (orderColumn.equals("3")) {
                    Logger.info("ordercolumn vale: >>>" + "0");
                    orderBy = "&orderBy=campaing";
                }else if(orderColumn.equals("4")){
                    orderBy = "&orderBy=fechaventa";
                }
            } else {
                Logger.info("ordercolumn es null");
                orderBy = "&orderBy=fechaventa";

            }
            Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));
            queryString =
                    "?" +
                            "pageLength=" + pageLength +
                            "&page=" + page +
                            query +
                            order +
                            orderBy;
            queryString = "/customers" + queryString;

            Logger.info("queryString: >>>" + queryString);
            WS.WSRequest req = WS.url(Constants.API + queryString).authenticate(user, password);
            WS.HttpResponse res = req.get();
            jsonResponse = res.getJson().getAsJsonObject();
            jsonObject.addProperty("draw", draw);
            if (jsonResponse.get("totalElements") != null) {
                jsonObject.addProperty("recordsTotal", jsonResponse.get("totalElements").getAsLong());
                jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
            } else {
                jsonObject.addProperty("recordsTotal", 0);
                jsonObject.addProperty("recordsFiltered", 0);
            }
            jsonObject.add("data", jsonResponse.get("elements"));
            System.out.println(jsonObject);
            renderText(jsonObject);
        } else  {
            String campaignNumber = (id == null) ? "" : "/" + id  ; // Descomentar id cuando el servicio lo acepte
            WS.WSRequest req = WS.url(Constants.API + "/customers" + campaignNumber).authenticate(user, password);
            WS.HttpResponse res = req.get();
            System.out.println(res.getJson());
            renderText(res.getJson());
        }
    }

    public static void templateBulkbankList() {
        String draw = params.get("draw");
        String queryString = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonResponse;
        if(draw != null) {
            Integer pageLength = params.get("length", Integer.class);
            String searchValue = params.get("search[value]");
            String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
            String page = String.valueOf(params.get("start", Integer.class) / pageLength + 1);

            String orderColumn = params.get("order[0][column]");
            String orderBy = "";
            String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
            Logger.info("orderColumn: >>>" + orderColumn);
            Logger.info("order: >>>" + order);
            if (orderColumn != null) {
                if (orderColumn.equals("0")) {
                    orderBy = "&orderBy=id";
                } else if (orderColumn.equals("1")) {
                    orderBy = "&orderBy=name";
                } else if (orderColumn.equals("2")) {
                    orderBy = "&orderBy=hotel";
                } else if (orderColumn.equals("3")) {
                    orderBy = "&orderBy=unitId";
                } else if (orderColumn.equals("4")) {
                    orderBy = "&orderBy=splitId";
                } else if (orderColumn.equals("5")) {
                    orderBy = "&orderBy=observations";
                }
            } else {
                Logger.info("ordercolumn es null");
            }
            Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));
            queryString =
                    "?" +
                            "pageLength=" + pageLength +
                            "&page=" + page +
                            query +
                            order +
                            orderBy;

            queryString = "/templateBulkBank" + queryString;

            Logger.info("queryString: >>>" + Constants.API_Bulkbank + queryString);
            WS.WSRequest req = WS.url(Constants.API_Bulkbank + queryString).authenticate(user, password);
            WS.HttpResponse res = req.get();
            jsonResponse = res.getJson().getAsJsonObject();

            jsonObject.addProperty("draw", draw);
            if (jsonResponse.get("totalElements") != null) {

                jsonObject.addProperty("recordsTotal", jsonResponse.get("totalElements").getAsLong());
                jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
            } else {
                jsonObject.addProperty("recordsTotal", 0);
                jsonObject.addProperty("recordsFiltered", 0);
            }
            jsonObject.add("data", jsonResponse.get("elements"));

            renderText(jsonObject);
        }
    }

    public static void bulkbankList() {
        String draw = params.get("draw");
        String queryString = "";
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonResponse;
        if(draw != null) {
            Integer pageLength = params.get("length", Integer.class);
            String searchValue = params.get("search[value]");
            String query = (!searchValue.equals(null) && !searchValue.isEmpty()) ? "&q=" + searchValue : "";
            String page = String.valueOf(params.get("start", Integer.class) / pageLength + 1);

            String orderColumn = params.get("order[0][column]");
            String orderBy = "";
            String order = (params.get("order[0][dir]") != null) ? "&order=" + params.get("order[0][dir]") : "";
            Logger.info("orderColumn: >>>" + orderColumn);
            Logger.info("order: >>>" + order);
            if (orderColumn != null) {
                if (orderColumn.equals("0")) {
                    orderBy = "&orderBy=templateName";
                } else if (orderColumn.equals("1")) {
                    orderBy = "&orderBy=hotelName";
                } else if (orderColumn.equals("2")) {
                    orderBy = "&orderBy=unitId";
                } else if (orderColumn.equals("3")) {
                    orderBy = "&orderBy=weekday";
                } else if (orderColumn.equals("4")) {
                    orderBy = "&orderBy=year";
                }
            } else {
                Logger.info("ordercolumn es null");
            }
            Logger.info("order[0][column]: >>>" + params.get("order[0][column]"));
            queryString =
                    "?" +
                            "pageLength=" + pageLength +
                            "&page=" + page +
                            query +
                            order +
                            orderBy;

            queryString = "/bulkBank" + queryString;

            Logger.info("queryString: >>>" + Constants.API_Bulkbank + queryString);
            WS.WSRequest req = WS.url(Constants.API_Bulkbank + queryString).authenticate(user, password);
            WS.HttpResponse res = req.get();
            jsonResponse = res.getJson().getAsJsonObject();

            jsonObject.addProperty("draw", draw);
            if (jsonResponse.get("totalElements") != null) {

                jsonObject.addProperty("recordsTotal", jsonResponse.get("totalElements").getAsLong());
                jsonObject.addProperty("recordsFiltered", jsonResponse.get("totalElements").getAsLong());
            } else {
                jsonObject.addProperty("recordsTotal", 0);
                jsonObject.addProperty("recordsFiltered", 0);
            }
            jsonObject.add("data", jsonResponse.get("elements"));

            renderText(jsonObject);
        }
    }

    /*  Click to dial */
    public static String clictodial(){
        String phone = params.get("phone");
        String name = params.get("name");
        String agenteEXT = Scope.Session.current().get("extension");
        String deptoid = Scope.Session.current().get("deptoid");
        String context = params.get("context");
        String idbooking = params.get("idbooking");
        //Contexto
        System.out.println("Depto: "+deptoid);
        WS.HttpResponse res;
        WS.WSRequest req = WS.url(Constants.API+"/areas/"+deptoid).authenticate(user, password);
        res = req.get();
        JsonObject response= new JsonObject();
        if(res.getStatus() ==200) {
            String contextByDepto=
                    !res.getJson().getAsJsonObject().get("contexto").isJsonNull() ?
                            res.getJson().getAsJsonObject().get("contexto").getAsString() : "from-internal";
            try{
                WS.WSRequest request = WS.url(Constants.API + "/audit").authenticate(user, password);
                JsonObject audit= MasterController.audit(new Integer(idbooking), "Clientes", "Detalle Cliente", "Llamada", "Intento de llamada al "+ phone);
                String params = audit.toString();
                request.body = params;
                request.mimeType = "application/json";
                res = request.post();
                AsteriskServer.originateCall(name, "SIP/"+agenteEXT, phone, contextByDepto ,context);
                response.addProperty("success", "Success");
            }catch (Exception e){
                Logger.error("Error al marcar");
                response.addProperty("error", "Error Asterisk");
            }
        }else
            response.addProperty("error", "Error to get context");

        return response.toString();
    }
}