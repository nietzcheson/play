package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by desarrollo1 on 19/05/2016.
 */
public class Functions {

    public static Map<String, String> getContext(Integer context){
        Map<String, String> deptoInfo= new HashMap<String,String>();
        switch (context){
            //Cobranza
            case 2: deptoInfo.put("context","texto_cobranza"); deptoInfo.put("account", "cobranza"); break;
            //Respush
            case 5: deptoInfo.put("context","texto_respush"); deptoInfo.put("account", "respush"); break;
            //Reservaciones Ingles
            case 6: deptoInfo.put("context","texto_reservations"); deptoInfo.put("account", "reservations"); break;
            //Reservaciones Español
            case 7: deptoInfo.put("context","texto_reservaciones"); deptoInfo.put("account", "reservaciones"); break;
            //Atencion a Clientes Ingles
            case 10: deptoInfo.put("context","texto_customer_service"); deptoInfo.put("account", "customer_service"); break;
            //Yucatan Holidays
            case 12: deptoInfo.put("context","from-internal"); deptoInfo.put("account", "admon_yh"); break;
            //Transportacion (Verificacion)
            case 15: deptoInfo.put("context","from-internal"); deptoInfo.put("account", "transporta"); break;
            //Atencion a Clientes Español
            case 17: deptoInfo.put("context","texto_servicio_cliente"); deptoInfo.put("account", "atencion_socios"); break;
            //Default
            default: deptoInfo.put("context","from-internal");deptoInfo.put("account", "general");
        }
        return deptoInfo;
    }
}
