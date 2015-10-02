package services;

import util.Constants;

/**
 * Created by Fernando on 12/02/2015.
 */
public interface UrlService {
    public final static String END_POINT="http://localhost:9010/";
//    public final static String END_POINT="http://127.0.0.1:9010/";
    public final static String OFFER_LIST= Constants.API + "/offers";
    public final static String COUNTRY_LIST= Constants.API + "/countries";
    public final static String BROKERS_LIST= Constants.API + "/users/getBrokers";
    public final static String SEGMENTS_LIST= Constants.API + "/segment/getSegments";
    public final static String CALLCENTER_LIST= Constants.API + "/callCenter";
    public final static String RESERVATION_GROUPS_LIST= Constants.API + "/reservationGroup/getReservationGroups";
    public final static String MERCHANTS_LIST= Constants.API + "/merchant/getMerchants";
    public final static String OFFERS_LIST= Constants.API + "/offers?pageLength=1000";
    public final static String USER_PERMISSION=END_POINT+"v1/user/hasPermission";
}
