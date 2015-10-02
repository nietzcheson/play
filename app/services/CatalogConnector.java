package services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.m4c.model.entity.*;
import com.m4c.model.entity.online.Country;
import controllers.MasterController;
import play.libs.WS;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Orlando on 12/02/2015.
 */
public class CatalogConnector extends MasterController {
    public List<Country> findAllCountries(){
        List<Country> countryLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.COUNTRY_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<Country>>() {}.getType();
            countryLst= gson.fromJson(response, listType);
        }
        return countryLst;
    }

    public List<UserCRM> findAllUsers() {
        List<UserCRM> userLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.BROKERS_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<UserCRM>>() {}.getType();
            userLst= gson.fromJson(response, listType);
        }
        return userLst;
    }

    public List<Segment> findAllSegments() {
        List<Segment> segmentLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.SEGMENTS_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<Segment>>() {}.getType();
            segmentLst= gson.fromJson(response, listType);
        }
        return segmentLst;
    }

    public List<CallCenter> findAllCallCenter() {
        List<CallCenter> segmentLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.CALLCENTER_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<CallCenter>>() {}.getType();
            segmentLst= gson.fromJson(response, listType);
        }
        return segmentLst;
    }

    public List<ReservationGroup> findAllReservationGroups() {
        List<ReservationGroup> reservationGroupLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.RESERVATION_GROUPS_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<ReservationGroup>>() {}.getType();
            reservationGroupLst= gson.fromJson(response, listType);
        }
        return reservationGroupLst;
    }

    public List<Merchant> findAllMerchants() {
        List<Merchant> merchantLst=null;
        Gson gson=new GsonBuilder().create();
        WS.WSRequest req = WS.url(UrlService.MERCHANTS_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();
        if(res.getStatus()==200) {
            Type listType = new TypeToken<List<Merchant>>() {}.getType();
            merchantLst= gson.fromJson(response, listType);
        }
        return merchantLst;
    }

    public List<Offer> findAllOffers() {
        List<Offer> offerLst=null;
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        WS.WSRequest req = WS.url(UrlService.OFFERS_LIST).authenticate(user, password);
        WS.HttpResponse res = req.get();
        JsonElement response=res.getJson();

        if(res.getStatus()==200) {
            JsonObject jsonObject=response.getAsJsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray("elements");

            Type listType = new TypeToken<List<Offer>>() {}.getType();
            offerLst= gson.fromJson(jsonArray, listType);

        }
        return offerLst;
    }

    public Country create() {
        return null;
    }


}
