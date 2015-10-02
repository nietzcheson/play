package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.m4c.model.entity.Offer;
import com.m4c.model.entity.Transportation;
import play.libs.WS;
import util.Constants;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Juan on 2/17/2015.
 */
public class TansportationService {

    // TODO: Todos las constantes de url irian con su respectivo servicio
    public static final String TRANSPORTATION_URL = Constants.API + "/transportation";

    public List<Transportation> findAll() {

        List<Transportation> transportationList = null;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        WS.HttpResponse res = WS.url(TRANSPORTATION_URL).get();
        JsonElement response = res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<Offer>>() {}.getType();
            transportationList = gson.fromJson(response, listType);
        }
        return transportationList;
    }
}
