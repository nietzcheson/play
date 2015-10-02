package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.m4c.model.entity.Offer;
import play.libs.WS;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Fernando on 12/02/2015.
 */
public class OfferServiceConnector  {

    public List<Offer> findAll() {
        List<Offer> offerLst=null;
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        WS.HttpResponse res = WS.url(UrlService.OFFER_LIST).put();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<Offer>>() {}.getType();
            offerLst= gson.fromJson(response, listType);
        }
        return offerLst;
    }

    public Offer create() {
        return null;
    }


}
