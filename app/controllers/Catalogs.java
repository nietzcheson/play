package controllers;

import com.google.gson.JsonObject;
import play.Logger;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.With;
import util.Constants;

/**
 * Created by desarrollo1 on 18/03/2015.
 */
@With(Secure.class)
public class Catalogs extends MasterController{
//    private static final MasterController masterController = new MasterController();
    public static void countriesList(){
        WS.WSRequest req = WS.url(Constants.API + "/countries").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/countries").get();
        Logger.info("Constants.API + \"/countries\"");
        renderText(res.getJson());
    }
    public static void statesList(){
        WS.WSRequest req = WS.url(Constants.API + "/states").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/states").get();
        Logger.info("Constants.API + \"/states\"");
        renderText(res.getJson());
    }
    public static void titleList(){
        WS.WSRequest req = WS.url(Constants.API + "/titles").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/titles").get();
        Logger.info("Constants.API + \"/titles\"");
        renderText(res.getJson());
    }
    public static void maritalList(){
        WS.WSRequest req = WS.url(Constants.API + "/maritalstatus").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/maritalstatus").get();
        Logger.info("Constants.API + \"/maritalstatus\"");
        renderText(res.getJson());
    }
    public static void featureList(){
        WS.WSRequest req = WS.url(Constants.API + "/features").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/features").get();
        Logger.info("Constants.API + \"/features\"");
        renderText(res.getJson());
    }
    public static void mealplanList(){
        WS.WSRequest req = WS.url(Constants.API + "/mealplans").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/features").get();
        Logger.info("Constants.API + \"/mealplans\"");
        renderText(res.getJson());
    }
    public static void hookList(){
        WS.WSRequest req = WS.url(Constants.API + "/hooks").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/features").get();
        Logger.info("Constants.API + \"/hooks\"");
        renderText(res.getJson());
    }
    public static void transportationList(){
        WS.WSRequest req = WS.url(Constants.API + "/transportation").authenticate(user, password);
        WS.HttpResponse res = req.get();
//        WS.HttpResponse res = WS.url(Constants.API + "/features").get();
        Logger.info("Constants.API + \"/transportation\"");
        renderText(res.getJson());
    }
    public static void ecertimagesList(){
        String page = params.get("page");
        WS.WSRequest req = WS.url(Constants.API + "/ecert/images/?page="+page).authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info(Constants.API+ "/ecert/images?page="+page);
        renderText(res.getJson());
    }
    public static void ecertList(){
        WS.WSRequest req = WS.url(Constants.API + "/ecert/").authenticate(user, password);
        WS.HttpResponse res = req.get();
        Logger.info(Constants.API+ "/ecert/");
        renderText(res.getJson());
    }
}
