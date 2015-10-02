package controllers;

import play.Logger;
import util.Constants;

/**
 * Created by desarrollo1 on 11/05/2015.
 */
public class Ecert extends MasterController {
    public static void list(Integer id) {
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String name = params.get("name");
        String idBroker = params.get("id-value");
        Logger.info("name: >>>" + name);

        if(mode != null && !mode.isEmpty()) {
            Logger.info("mode: >>>" + mode);
            if(mode.equals("edit")) {
                successfullyUpdated = true;
                Logger.info("Edition correcta");
            } else if(mode.equals("create")) {
                successfullyCreated = true;
                Logger.info("Creación correcta");
            }
        }
        renderArgs.put("url", Constants.API);
        renderArgs.put("name", name);
        renderArgs.put("id", id);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);
        render();
    }

}
