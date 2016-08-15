package services;

import controllers.MasterController;

/**
 * Created by Sistemas on 12/08/2016.
 */
abstract class M4CBBReservacionesServiceBase extends MasterController
{

    private String template;
    private String annio;

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

    public String getAnnio() {
        return this.annio;
    }

}
