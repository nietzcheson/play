package controllers;
import play.mvc.Scope;
import services.UserService;
import java.util.Arrays;
/**
 * Created by Fernando on 10/02/2015.
 */
public class Security extends Secure.Security{

    static boolean authenticate(String username, String password) {
        UserService userService=new UserService();
        return userService.authenticate(username,password);
    }

    static boolean check(String resource) {
        String userlevel= Scope.Session.current().get("user_token").replace("[", "").replace("]", "").replaceAll("\"", "");
        String[] permisos= userlevel.split(",");
        return Arrays.asList(permisos).contains(resource);
    }
    static boolean checkPermission(String permission,String resource) {
        UserService userService=new UserService();
        return userService.checkPermission(permission,resource);
    }
}
