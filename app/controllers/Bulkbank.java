package controllers;

import play.Logger;

/**
 * Created by desarrollo1 on 18/04/2016.
 */
public class Bulkbank extends MasterController {

    public static void list() {
        Logger.info("Entrando a BulkBank.list()");
        Boolean successfullyCreated = false;
        Boolean successfullyUpdated = false;
        String mode = params.get("mode");
        String id = params.get("id-value");
        String hotelName = params.get("hotelName");
        if(mode != null && !mode.isEmpty()) {
            if(mode.equals("edit")) {
                successfullyUpdated = true;
            } else if(mode.equals("create")) {
                successfullyCreated = true;
            }
        }
        renderArgs.put("id", id);
        renderArgs.put("hotelName", hotelName);
        renderArgs.put("successfullyCreated", successfullyCreated);
        renderArgs.put("successfullyUpdated", successfullyUpdated);

        render();
    }

    public static void create(Long template) {
        boolean editMode;
        if( template != null)
            editMode = true;
        else
            editMode = false;
        renderArgs.put("editMode", editMode);
        renderArgs.put("template", template);
        render();
    }
}
