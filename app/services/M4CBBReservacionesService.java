package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import play.libs.WS;
import util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sistemas on 12/08/2016.
 */
public class M4CBBReservacionesService extends M4CBBReservacionesServiceBase
{

    public List<ReservationDTO> getFailures()
    {
        WS.WSRequest request = WS.url(Constants.API_Bulkbank + "/bulkBank/failures").authenticate(user, password);
        List<ReservationDTO> reservations = null;
        WS.HttpResponse response = request.get();
        request.mimeType = "application/json";

        if(response.getStatus() == 200) {
            java.lang.reflect.Type collectionClientStatusAPIType = new TypeToken<Collection<ReservationDTO>>(){}.getType();
            reservations=new Gson().fromJson(response.getString(), collectionClientStatusAPIType);
        }

        return reservations;
    }

}
