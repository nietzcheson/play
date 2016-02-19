package controllers;

import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.SourceTree;
import play.Play;
import play.i18n.Lang;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Scope;
import util.Constants;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by desarrollo1 on 01/04/2015.
 */
public class MasterController extends Controller {

    protected static final String user=Play.configuration.getProperty("api.user");
    protected static final String password=Play.configuration.getProperty("api.password");
    protected static final String urlintranet=Play.configuration.getProperty("api.urlintranet");
    protected static final String urlportal=Play.configuration.getProperty("api.urlportal");
    protected static final String urlfile=Play.configuration.getProperty("api.urlfile");

    @play.mvc.Before
    public static void setParameters(){
        String name= Scope.Session.current().get("user_name");
        String username= Scope.Session.current().get("username");
        String extension = Scope.Session.current().get("extension");
        String acceso = Scope.Session.current().get("acceso");
        String grupo = Scope.Session.current().get("grupo");
        Boolean userIntranet = Boolean.valueOf(Scope.Session.current().get("userIntranet"));
        String urlservicio= Constants.API;
        String urlecert= Constants.URL_ECERT;
        String version= Constants.version;
        String lan=Lang.get();
        String intranet="http://10.194.17.173";
        renderArgs.put("user_name", name);
        renderArgs.put("username", username);
        renderArgs.put("urlservicio", urlservicio);
        renderArgs.put("urlecert", urlecert);
        renderArgs.put("lan", lan);
        renderArgs.put("version", version);
        renderArgs.put("extension", extension);
        renderArgs.put("acceso", acceso);
        renderArgs.put("grupo", grupo);
        renderArgs.put("intranet", intranet);
        renderArgs.put("userIntranet", userIntranet);

    }

    public static JsonObject audit(Integer bookingid, String  modulo, String ventana, String accion, String detalle){
//        String ip = request.remoteAddress;
        String ip = "10.194.17.25";
//        System.out.println("IP: " + ip);
        JsonObject audit = new JsonObject();
        audit.addProperty("username", Scope.Session.current().get("username"));
        audit.addProperty("idBooking", bookingid);
        audit.addProperty("ip", ip);
        audit.addProperty("modulo", modulo);
        audit.addProperty("ventana", ventana);
        audit.addProperty("accion", accion);
        audit.addProperty("detalle", detalle);
        return audit;
    }
}
