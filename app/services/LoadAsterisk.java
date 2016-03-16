package services;

import org.asteriskjava.live.AsteriskServer;
import play.Logger;
import play.jobs.*;

/**
 * Created by desarrollo1 on 04/02/2016.
 */

@OnApplicationStart
public class LoadAsterisk extends Job{
    public void doJob() {
        try {
            Logger.info("--- Loading Asterisk Server...");
            System.out.println("--- Loading Asterisk Server...");
            services.AsteriskServer.add("m4sg-taurus", "taurus.it.sunset.com.mx", "dialapplet", "DAsys73xrv#");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Error al conectarse con el servidor asterisk!!");
            Logger.info("Error al conectarse con el servidor asterisk!!");
            e.printStackTrace();
        }
    }
}
