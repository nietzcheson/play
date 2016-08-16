package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dao.M4CBBReservacionDTO;
import dao.M4CBBReservacionesDTO;
import play.libs.WS;
import util.Constants;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sistemas on 12/08/2016.
 */
public class M4CBBReservacionesService extends M4CBBReservacionesServiceBase
{

    public List<M4CBBReservacionesDTO> getFailures(Long template, int year)
    {
        WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/failures/?template=" + template + "&year=" + year).authenticate(user, password);
        request.mimeType = "application/json";

        List<M4CBBReservacionesDTO> reservations = null;
        WS.HttpResponse response = request.get();

        if(response.getStatus() == 200) {
            java.lang.reflect.Type collectionClientStatusAPIType = new TypeToken<Collection<M4CBBReservacionesDTO>>(){}.getType();
            reservations = new Gson().fromJson(response.getString(), collectionClientStatusAPIType);
        }

        return reservations;
    }

    public JsonObject resendBulkBank(int id)
    {
        WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/resend-bulkbank?id=" + id).authenticate(user, password);
        request.mimeType = "application/json";

        WS.HttpResponse response = request.get();

        JsonObject reservacion = new JsonObject();

        if(response.getStatus() == 200){

            reservacion = response.getJson().getAsJsonObject();
            reservacion.addProperty("status", 200);
        }

        return reservacion;

    }

}
