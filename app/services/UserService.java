package services;

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
            System.out.println(jsonElement);
            return true;
        }
    return false;
    }
    public void setParamsSessionUser(JsonObject userJson){
        System.out.println(userJson);
        Scope.Session.current().put("username",userJson.get("usuario").getAsString());
        Scope.Session.current().put("user_name",userJson.get("nombre").getAsString());
        Scope.Session.current().put("user_token",userJson.get("permisos").getAsJsonArray());
    }
}
