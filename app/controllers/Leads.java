package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import play.*;
import play.libs.WS;
import play.mvc.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import util.Constants;

@With(Secure.class)
public class Leads extends MasterController {

    public static void list() {
        render();
    }

    public static void create(Integer id, String certificate) {
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
        renderArgs.put("certificate", certificate);
        render();
    }
    public static void sale(Integer clientid, Integer bookingid, Integer callcenter) {
        if(bookingid!=null) {
            JsonObject jsonObject;
            String ip = request.remoteAddress;
            System.out.println("IP:" + ip);
            JsonObject audit =MasterController.audit(bookingid, "Venta", "Datos de la Venta ", "Detalle de la venta", "Detalle de la venta");
//            audit.addProperty("username", Scope.Session.current().get("username"));
//            audit.addProperty("idBooking", bookingid);
//            audit.addProperty("ip", ip);
//            audit.addProperty("modulo", "Pagos");
//            audit.addProperty("ventana", "Datos de la Venta ");
//            audit.addProperty("accion", "Eliminar Pago");
//            audit.addProperty("detalle", "Eliminar Pago");
            WS.HttpResponse res;
            Logger.info("Detalle de venta");
            Logger.info("Servicio Detalle de venta: " + Constants.API + "/sales/" + bookingid + "/detail");
            try {
                String params = audit.toString();
                WS.WSRequest request = WS.url(Constants.API + "/sales/" + bookingid + "/detail").authenticate(user, password);
                request.body = params;
                request.mimeType = "application/json";
                res = request.post();
                jsonObject = res.getJson().getAsJsonObject();
                jsonObject.addProperty("responsestatus", res.getStatus());
                renderArgs.put("sale", jsonObject);
//                renderText(jsonObject);
            } catch (Exception excepcion) {
                renderArgs.put("error", "El idbooking no existe");
//                renderText(excepcion);
            }
        }
        renderArgs.put("clientid", clientid);
        renderArgs.put("bookingid", bookingid);
        renderArgs.put("callcenter", callcenter);
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
        renderArgs.put("urlfile", urlfile);
        renderArgs.put("user", user);
        render();
    }
    public static void reservations(String page, String id, String booking, String callcenter) {
        String username= Scope.Session.current().get("username");
        String password= Scope.Session.current().get("password");
        String pageurl;
        if(page.equals("cancel")){
            pageurl= urlintranet + "/M4CApp/Certificados/CERVENTARecord2.jsp?IDBOOKING="+booking+"&IDCLIENTE="+id;
        }else if(page.equals("record")){
            pageurl=urlintranet + "/M4CApp/M4CRecordings_2.jsp?id="+booking;
        }else if(page.equals("reserve")){
            pageurl=urlintranet + "/M4CApp/M4CRESERVACIONRecord.jsp?IDCLIENTE="+id+"&IDRESERVACION="+callcenter+"&IDBOOKING="+booking;
        }else if(page.equals("survey")){
            pageurl=urlintranet + "/M4CApp/M4CSURVEYRecord.jsp?IDBOOKING="+booking+"&amp;IDCLIENTE="+id+"&amp;IDSURVEY=";
        }else if(page.equals("customer")){
            pageurl=urlintranet + "/M4CApp/CustomerServiceSurvey.jsp?IDCLIENTE="+id+"&IDBOOKING="+booking;
        }else if(page.equals("file")){
            pageurl=urlfile + "/M4CApp/M4CArchivoDigital.jsp?idbook="+booking+"&idcc="+callcenter+"&idcliente="+id+"&area=C&iduser="+username;
        }else if(page.equals("deletecert")){
            pageurl=urlintranet + "/M4CApp/Certificados/deleteCertificate.jsp?IDCLIENTE="+id+"&IDBOOKING="+booking+"&NUMCERT="+callcenter;
        }else if(page.equals("openfile")){
            pageurl=urlintranet + "/ImagenesApps/ReturnToParadise/"+booking+".jpg";
        }else{
            pageurl=urlintranet + "/M4CApp/Certificados/CERCLIENTERecord.jsp?IDCLIENTE="+id;
        }
        renderArgs.put("username", username);
        renderArgs.put("password", password);
        renderArgs.put("pageurl", pageurl);
        renderArgs.put("urlportal", urlportal);
        System.out.println(pageurl);
//        renderArgs.put("id", id);

        render();
    }
    public static void createLead(String id) throws ParseException {
        JsonObject jsonObject;
        String addId;
        if(id != null && !id.isEmpty())
            addId = "/" + id;
        else
            addId = "";
        Logger.info("Creando..");
        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String certificate = params.get("certificate");
        String campaign = params.get("campaign");
        String birthdate = params.get("birthdate");
        Logger.info("Birthdate: >>>" + birthdate);
        String year = params.get("year");
        String month = params.get("month");
        String day = params.get("day");
        String city = params.get("city");
        String country = params.get("countries");
        String state = params.get("states");
        String zip = params.get("zipcode");
        String address = params.get("street");
        String email = params.get("email");
        String email2 = params.get("email2");
        String phone = params.get("phone");
        String typephone = params.get("type_phone");
        String typephone1 = params.get("type_phone1");
        System.out.println("typephone" + typephone1);
        String typephone2 = params.get("type_phone2");
        System.out.println(typephone2);
        String phone1 = params.get("phone1");
        System.out.println(phone1);
        String phone2 = params.get("phone2");
        System.out.println(phone2);
        String phone_text="  \""+ typephone + "\": \" "+ phone + "\", \n";
        if(typephone1!= null) {
            phone_text=phone_text.concat("  \""+ typephone1 + "\": \" "+ phone1 + "\", \n");
        }
        if(typephone2!= null) {
            phone_text=phone_text.concat("  \""+ typephone2 + "\": \" "+ phone2 + "\", \n");
        }
        Logger.info("phone_text: >>>" + phone_text);
        String status = params.get("status");
        String occupation = params.get("occupation");
        String interested = params.get("features");
        String username= Scope.Session.current().get("username");
        String title = params.get("title");
        System.out.println(interested);
        String param="{\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"firstName\":\" " + firstname + "\",\n" +
                "  \"lastName\": \"" + lastname + "\",\n" +
                "  \"email\": \" "+ email + "\", \n"+
                "  \"email2\": \" "+ email2 + "\", \n"+
                phone_text+
                "  \"state\": \""+ state + "\", \n"+
                "  \"city\": \""+ city + "\", \n"+
                "  \"countryCode\": \""+ country + "\", \n"+
                "  \"userName\": \""+ username + "\", \n"+
                "  \"birthdate\": \"1989/05/05\", \n"+
                "  \"dOBYear\": \""+year+"\", \n"+
                "  \"dOBMonth\": \""+month+"\", \n"+
                "  \"dOBDay\": \""+day+"\", \n"+
                "  \"zip\": \""+zip+"\", \n"+
                "  \"address\": \""+address+"\", \n"+
                "  \"occupation\": \""+occupation+"\", \n"+
                "  \"statusMarital\": \""+status+"\", \n"+
                "  \"sale\" : " +
                "{\n" +
                "  \"certificate\": \"" + certificate + "\", \n" +
                "  \"campaignId\": \"" + campaign + "\" \n" +
                "},\n" +
                "  \"featureLst\" : [" +
                        interested+
                    "]" +
                "}";
        Logger.info("param: >>>" + param);
        WS.HttpResponse res;
        Logger.info("Constants.API + customers + addId: >>>" + Constants.API + "/customers" + addId);
        WS.WSRequest request = WS.url(Constants.API + "/customers" + addId).authenticate(user, password);
        request.body = param;
        request.mimeType="application/json";
        res=request.post();
        jsonObject=res.getJson().getAsJsonObject();
        jsonObject.addProperty("responsestatus", res.getStatus() );
        renderText(jsonObject);
    }

    //Remover un pago del Lead
    public static void removePayment(String id) throws ParseException {
        String bookingId = params.get("bookingId");
//        String ip=request.remoteAddress;
        JsonObject audit= MasterController.audit(new Integer(id), "Pagos", "Datos de la Venta", "Eliminar Pago", "Eliminar Pago");
//        JsonObject jsonObject;
        WS.HttpResponse res;
        Logger.info("Eliminar Pago #" + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
        Logger.info("Servicio Eliminar Pago: " + Constants.API + "/sales/" + bookingId+"/payments/"+id+"/delete");
        try{
            String params=audit.toString();
            WS.WSRequest request = WS.url(Constants.API + "/sales/" + bookingId+"/payments/"+id+"/delete").authenticate(user, password);
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
//            System.out.println(res.getJson());
//            jsonObject=res.getJson().getAsJsonObject();
//            jsonObject.addProperty("responsestatus", res.getStatus() );
            renderText(res.getJson());
        }catch(Exception excepcion){
            System.out.println("Exception "+excepcion);
            renderText(excepcion);
        }

    }
    //Remover un pago del Lead
    public static void removeService(String id) throws ParseException {
        String bookingId = params.get("bookingId");
//        String ip=request.remoteAddress;
        String ip=request.remoteAddress;
        System.out.println("IP:"+ip);
        JsonObject audit= MasterController.audit(new Integer(id), "Servicios Contratados", "Datos de la Venta", "Eliminar Servicio", "Eliminar Servicio");
//        JsonObject audit= new JsonObject();
        JsonObject jsonObject;
//        audit.addProperty("username", Scope.Session.current().get("username"));
//        audit.addProperty("idBooking", bookingId);
//        audit.addProperty("ip", ip);
//        audit.addProperty("modulo", "Servicios Contratados");
//        audit.addProperty("ventana", "Datos de la Venta ");
//        audit.addProperty("accion", "Eliminar Servicio");
//        audit.addProperty("detalle", "Eliminar Servicio");
        WS.HttpResponse res;
        Logger.info("Eliminar Servicio Contratado #" + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
        Logger.info("Servicio Eliminar Servicio Contratado: " + Constants.API + "/sales/" + bookingId+"/providedServices/"+id+"/delete");
        try{
            String params=audit.toString();
            Logger.info("Params: "+params);
            WS.WSRequest request = WS.url(Constants.API + "/sales/" + bookingId+"/providedServices/"+id+"/delete").authenticate(user, password);
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            renderText(jsonObject);
        }catch(Exception excepcion){
            renderText(excepcion);
        }

    }
    //Crear o Actualizar un pago un pago del Lead
    public static void createPayment(String id) throws ParseException {
        String bookingId = params.get("bookingId");

        //Datos Audit
//        String ip=request.remoteAddress;
        String text_module;

//        JsonObject audit= new JsonObject();
        JsonObject jsonObject;
        JsonObject payment= new JsonObject();
//        audit.addProperty("username", Scope.Session.current().get("username"));
//        audit.addProperty("idBooking", bookingId);
//        audit.addProperty("ip", ip);
//        audit.addProperty("modulo", "Pagos");
//        audit.addProperty("ventana", "Datos de la Venta ");
        Integer idaudit;
        if(id==null){
            text_module="Crear Pago";
            idaudit=0;
        }else{
            idaudit=new Integer(id);
            text_module="Editar Pago";
        }
        JsonObject audit= MasterController.audit(idaudit, "Pagos", "Datos de la Venta", text_module, text_module);
//        audit.addProperty("accion", text_module);
//        audit.addProperty("detalle", text_module);
        payment.add("audit", audit);

        //Datos Pago
        String terminal = params.get("terminal");
        payment.addProperty("terminal", terminal);
        String amount = params.get("amount");
        payment.addProperty("amount", amount);
        String currency = params.get("currency");
        payment.addProperty("currency", currency);
        String authorizationNumber = params.get("authorizationNumber");
        payment.addProperty("authorizationNumber", authorizationNumber);
        String paymentDate = params.get("paymentDate");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = formatter.parse(paymentDate);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        paymentDate = formatter2.format(date);
        payment.addProperty("paymentDate", paymentDate);
        String userCommission = params.get("userCommission");
        payment.addProperty("userCommission", userCommission);
        String userPayment = params.get("userPayment");
        payment.addProperty("userPayment", userPayment);
        String exchangeRate = params.get("exchangeRate");
        payment.addProperty("exchangeRate", exchangeRate);

        //Datos Tarjeta
        JsonObject creditcard= new JsonObject();
        String name = params.get("name");
        creditcard.addProperty("name", name);
        String lastName = params.get("lastName");
        creditcard.addProperty("lastName", lastName);
        String nCard = params.get("nCard");
        creditcard.addProperty("nCard", nCard);
        String cvv = params.get("cvv");
        creditcard.addProperty("cvv", cvv);
        String exp = params.get("exp");
        creditcard.addProperty("exp", exp);
        String typeCard = params.get("typeCard");
        creditcard.addProperty("typeCard", typeCard);

        String servicesId[] = params.get("servicesId").split(",");
        String servicesAmount[] = params.get("servicesAmount").split(",");
        JsonArray servicesPayment= new JsonArray();
        JsonObject services;
        payment.add("creditCardDTO", creditcard);

        for (int i=0; i< servicesId.length; i++){
            services= new JsonObject();
            services.addProperty("id", servicesId[i]);
            services.addProperty("amount", servicesAmount[i]);
            servicesPayment.add(services);
        }
        payment.add("providedServicesDTO", servicesPayment);
        WS.HttpResponse res;
        if(id != null && !id.isEmpty()){
            Logger.info("Actualizar Pago #" + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
            Logger.info("Servicio Actualizar Pago: " + Constants.API + "/sales/" + bookingId+"/payments/"+id);
            try{
                String params=payment.toString();
                WS.WSRequest request = WS.url( Constants.API + "/sales/" + bookingId+"/payments/"+id).authenticate(user, password);
                request.body = params;
                request.mimeType="application/json";
                res=request.put();
                System.out.println(res.getJson());
                jsonObject=res.getJson().getAsJsonObject();
                jsonObject.addProperty("responsestatus", res.getStatus() );
                System.out.println("Status:" + res.getStatus());
                renderText(jsonObject);
            }catch(Exception excepcion){
                renderText(excepcion);
            }
        }else {
            String params=payment.toString();
            Logger.info("Crear Pago " + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
            Logger.info("Servicio Crear Pago: " + Constants.API + "/sales/" + bookingId+"/payments");
            Logger.info("Parametros: " +params);
            try{
                WS.WSRequest request = WS.url( Constants.API + "/sales/" + bookingId+"/payments").authenticate(user, password);
                request.body = params;
                request.mimeType="application/json";
                res=request.post();
                System.out.println("Json:"+res.getJson());
                jsonObject=res.getJson().getAsJsonObject();
                jsonObject.addProperty("responsestatus", res.getStatus() );
                System.out.println("Status:"+res.getStatus());
                renderText(jsonObject);
            }catch(Exception excepcion){
                renderText(excepcion);
            }
        }
    }

    //Crear o Actualizar un servicio al Lead
    public static void createService(String id) throws ParseException {
        String bookingId = params.get("bookingId");

        //Datos Audit
//        String ip=request.remoteAddress;
        Integer idaudit;
        if(id==null)
            idaudit=0;
        else
            idaudit=new Integer(id);
        JsonObject audit= MasterController.audit(idaudit, "Pagos", "Datos de la Venta", "Crear Servicio", "Crear Servicio");
//        JsonObject audit= new JsonObject();
        JsonObject jsonObject;
        JsonObject payment= new JsonObject();
//        audit.addProperty("username", Scope.Session.current().get("username"));
//        audit.addProperty("idBooking", bookingId);
//        audit.addProperty("ip", ip);
//        audit.addProperty("modulo", "Pagos");
//        audit.addProperty("ventana", "Datos de la Venta ");
//        audit.addProperty("accion", "Crear Servicio");
//        audit.addProperty("detalle", "Crear Servicio");
        payment.add("audit", audit);

        //Datos Pago
        String idsubservicio = params.get("idsubservicio");
        payment.addProperty("idsubservicio", idsubservicio);
        String adults = params.get("adults");
        payment.addProperty("adults", adults);
        String minors = params.get("minors");
        payment.addProperty("minor", minors);
        String amount = params.get("amount");
        payment.addProperty("amount", amount);
        String description = params.get("description");
        payment.addProperty("description", description);

        WS.HttpResponse res;
        if(id != null && !id.isEmpty()){
            Logger.info("Actualizar Pago #" + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
            Logger.info("Servicio Actualizar Pago: " + Constants.API + "/sales/" + bookingId+"/providedServices/"+id);
            id="/"+id;
        }else {
            id="";
            Logger.info("Crear Pago " + id + " del bookingId: " + bookingId+" en: "+ Constants.API);
            Logger.info("Servicio Crear Pago: " + Constants.API + "/sales/" + bookingId+"/providedServices");
        }
        String params=payment.toString();
        Logger.info("Parametros: " +params);
        try{
            System.out.println("Url: "+Constants.API + "/sales/" + bookingId+"/providedServices"+id);
            WS.WSRequest request = WS.url( Constants.API + "/sales/" + bookingId+"/providedServices"+id).authenticate(user, password);
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
            System.out.println(res.getJson());
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            jsonObject.addProperty("user", user);
            System.out.println("Status:" + res.getStatus());
            renderText(jsonObject);
        }catch(Exception excepcion){
            renderText(excepcion);
        }
    }

    //Crear o Actualizar una Venta
    public static void createSale(String id) throws ParseException {
        //Datos Audit
//        String ip=request.remoteAddress;
        Integer auditid;
        if(id==null)
            auditid=0;
        else
            auditid=new Integer(id);
//        JsonObject audit= new JsonObject();
        JsonObject audit= MasterController.audit(auditid, "Pagos", "Datos de la Venta", "Crear Venta", "Crear Venta");
        JsonObject jsonObject;
        JsonObject sale= new JsonObject();
        sale.add("audit", audit);

        //Datos Venta
        String campaignId = params.get("campania");
        sale.addProperty("campaignId", campaignId);
        String checker = params.get("checker");
        sale.addProperty("checker", checker);
        String supervisor = params.get("supervisor");
        sale.addProperty("supervisor", supervisor);
        String status = params.get("status");
        sale.addProperty("status", status);
        String numRecord = params.get("numRecord");
        sale.addProperty("numRecord", numRecord);
        String comment = params.get("comment");
        sale.addProperty("comment", comment);
        String extraBonus = params.get("extraBonus");
        sale.addProperty("extraBonus", extraBonus);
        String numCert = params.get("numCert");
        sale.addProperty("numCert", numCert);
        WS.WSRequest request;
        WS.HttpResponse res;
        sale.addProperty("agent", "ATCLIENTES");
        if(id != null && !id.isEmpty()){
//            String agent = params.get("agent");
//            sale.addProperty("agent", agent);
            Logger.info("Servicio Editar Venta: " + Constants.API + "/sales/" + id);
            request = WS.url( Constants.API + "/sales/" + id).authenticate(user, password);
        }else {
            id="";
            String client = params.get("client");
            Logger.info("Servicio Crear Venta: " + Constants.API + "/customers/"+client+"/sales");
            System.out.println("Url: "+Constants.API + "/customers/" + client+"/sales");
            request = WS.url( Constants.API + "/customers/"+client+"/sales").authenticate(user, password);
        }
        String params=sale.toString();
        Logger.info("Parametros: " +params);
        try{
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
            System.out.println(res.getJson());
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            jsonObject.addProperty("user", user);
            System.out.println("Status:" + res.getStatus());
            renderText(jsonObject);
        }catch(Exception excepcion){
            renderText(excepcion);
        }
    }
    //Crear o Actualizar una Nota
    public static void createNote(String id) throws ParseException {
        //Datos Audit
//      String ip="200.79.231.94";
//        String ip=request.remoteAddress;
        JsonObject audit= MasterController.audit(0, "Notas", "Datos de la Venta", "Crear Nota", "Crear Nota");
        JsonObject jsonObject;
        JsonObject note= new JsonObject();
        note.add("audit", audit);
        //Datos Venta
        String mood = params.get("mood");
        note.addProperty("mood", mood);
        String nota = params.get("nota");
        note.addProperty("description", nota);
        String requesicion = params.get("requesicion");
        note.addProperty("specialReq", requesicion);
        String type = params.get("type");
        note.addProperty("type", type);
        Boolean follow_up = Boolean.parseBoolean(params.get("follow_up"));
        System.out.println(follow_up);
        if(follow_up){
            String user = params.get("user");
            String datenote = params.get("date");
            //String paymentDate = params.get("paymentDate");
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = formatter.parse(datenote);
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            datenote = formatter2.format(date);
            note.addProperty("remindTOusername", user);
            note.addProperty("contactDate", datenote);
        }

        WS.WSRequest request;
        WS.HttpResponse res;
        String client = params.get("client");
        Logger.info("Servicio Crear Nota: " + Constants.API + "/customers/"+client+"/notes");
        System.out.println("Url: "+Constants.API + "/customers/" + client+"/notes");
        request = WS.url( Constants.API + "/customers/"+client+"/notes").authenticate(user, password);
        String params=note.toString();
        Logger.info("Parametros: " +params);
        try{
            request.body = params;
            request.mimeType="application/json";
            res=request.post();
            System.out.println(res.getJson());
            jsonObject=res.getJson().getAsJsonObject();
            jsonObject.addProperty("responsestatus", res.getStatus() );
            jsonObject.addProperty("user", user);
            System.out.println("Status:" + res.getStatus());
            renderText(jsonObject);
        }catch(Exception excepcion){
            renderText(excepcion);
        }
    }

}