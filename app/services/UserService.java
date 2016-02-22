package services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controllers.MasterController;
import play.Logger;
import play.libs.WS;
import play.mvc.Scope;
import util.Constants;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Fernando on 10/02/2015.
 */
public class UserService extends MasterController{

    public boolean checkPermission(String resource,String permission){
        String userToken=Scope.Session.current().get("user_token");

        HashMap params=new HashMap();

        WS.HttpResponse res ;
        WS.WSRequest request = WS.url(UrlService.USER_PERMISSION);
        params.put("userToken", userToken);
        params.put("resource", resource);
        params.put("permission", permission);

        request.params(params);
        res=request.post();
        if(res.getStatus() ==200) {
            return true;
        }
     return false;
    }

    public boolean authenticate(String userName,String pass){
        WS.HttpResponse res ;
        WS.WSRequest request = WS.url(Constants.API + "/intranet/login").authenticate(user, password);
        request.setParameter("username", userName);
        request.setParameter("password", pass);
        res=request.post();
        JsonElement jsonElement=res.getJson();
        if(res.getStatus() ==200) {
            Scope.Session.current().put("password",pass);
            setParamsSessionUser(jsonElement.getAsJsonObject());
            return true;
        }
    return false;
    }
    public void setParamsSessionUser(JsonObject userJson){
        System.out.println(userJson);
        String username = userJson.get("usuario").isJsonNull() ? "" :userJson.get("usuario").getAsString();
        String nombre = userJson.get("nombre").isJsonNull() ? "" :userJson.get("nombre").getAsString();
        String extension = userJson.get("extension").isJsonNull() ? "" :userJson.get("extension").getAsString();
        Boolean userIntranet = userJson.get("userIntranet").isJsonNull() ? false : userJson.get("userIntranet").getAsBoolean();
        String acceso = userJson.get("acceso").isJsonNull() ? "" :userJson.get("acceso").getAsString();
        String deptoid = userJson.get("deptoid").isJsonNull() ? "" :userJson.get("deptoid").getAsString();
        String grupo = userJson.get("grupo").isJsonNull() ? "" :userJson.get("grupo").getAsString();
        JsonArray permisos = userJson.get("permisos").isJsonNull() ? new JsonArray() :userJson.get("permisos").getAsJsonArray();

        Scope.Session.current().put("username", username);
        Scope.Session.current().put("user_name", nombre);
        Scope.Session.current().put("user_token", permisos);
        Scope.Session.current().put("extension",extension);
        Scope.Session.current().put("userIntranet",userIntranet);
        Scope.Session.current().put("acceso",acceso);
        Scope.Session.current().put("deptoid",deptoid);
        System.out.println("Depto Id: "+deptoid);
        Scope.Session.current().put("grupo",grupo);
    }
}
