package services;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.asteriskjava.live.*;
import org.asteriskjava.live.internal.AsteriskAgentImpl;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.apache.commons.lang.StringUtils;
/**
 * Created by desarrollo1 on 04/02/2016.
 */
public class AsteriskServer extends DefaultAsteriskServer {
    /** Static Factory Methods **************************/
    static private Map<String, AsteriskServer> servers = new Hashtable<String, AsteriskServer>();
    static private AsteriskServer defaultServer;

    static public void add(String name, String host, String user, String pass) {
        AsteriskServer server = new AsteriskServer(host, user, pass);
        if (servers.size() == 0) {
            setDefaultServer(server);
        }
        servers.put(name, server);

    }

    static public AsteriskServer get(String name) {
        AsteriskServer server = servers.get(name);
        server.initialize();
        return server;
    }

    public static AsteriskServer getDefault() {
        return defaultServer;
    }

    public static void setDefaultServer(AsteriskServer defaultServer) {
        AsteriskServer.defaultServer = defaultServer;
        defaultServer.initialize();

        defaultServer.addAsteriskServerListener(asteriskServerListener);
    }

    /** Constructors ************************************/

    private AsteriskServer(String hostname, String username, String password) {
        super(hostname, username, password);
    }

    public static void init() {
        AsteriskServer server = getDefault();
        server.initialize();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //initialize avaliable devices
        String technologies[] = {"sip", "iax2"};
        for(String tech:technologies){
            String result = server.executeCliCommand(tech + " show peers").toString(); //iax2
            System.out.println(result);
            String[] splited = result.split(",");
            int size = splited.length-3;
            //println "Protocol: $protocol"
            String header = splited[0].toUpperCase();
            //println "Header: ${header}"
            int iName = header.indexOf("NAME");
            int iHost = header.indexOf("HOST");
            //int iPort = header.indexOf("PORT")
            //def iStatus = header.indexOf("STATUS")

            for(int i=1; i<size; i++ ){
                String row = splited[i];
                //println row
                String name = row.substring(iName, iHost).trim();
                System.out.println(name);
                int index = name.indexOf('/');
                if(index>=0) name = name.substring(0, index);
                name = tech + "/" + name;
                PBXDevice.findOrCreateById(name);
            }
        }


        //initialize channels
        for(AsteriskChannel ch: getAstChannels()){
            new Channel(ch).save();
        }
    }

    public static Collection<AsteriskChannel> getAstChannels() {
        return getDefault().getChannels();
    }

    public static void originateCall(AsteriskServer server, String callerId,
                                     String src, String dst, String context,  String account, String provider) throws Exception {

        dst = provider + dst;
        System.out.println(src + " calling... " + dst);
        OriginateAction originateAction;
        ManagerResponse originateResponse;

        callerId = ((callerId!=null)?callerId:"") + "<" +StringUtils.substring(src, 4)+ ">";

        originateAction = new OriginateAction();
        originateAction.setChannel(src);
        originateAction.setCallerId(callerId);
        originateAction.setContext(context);
        originateAction.setExten(dst);
        originateAction.setPriority(1);
        originateAction.setAccount(account);
        // originateAction.setTimeout(30000);

        server.initialize();
        ManagerConnection mc = server.getManagerConnection();

        // send the originate action and wait for a maximum of 30 seconds for
        // Asterisk
        // to send a reply

        try {
            originateResponse = mc.sendAction(originateAction, 60000);
            // print out whether the originate succeeded or not
            System.out.println(originateResponse.getResponse());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void originateCall(String callerId, String src, String dst, String context, String account, String provider) throws Exception {
        originateCall(getDefault(),  callerId, src, dst, context, account, provider);
    }

    public static void originateCall(String callerId, String src, String dst, String context, String provider) throws Exception {
        originateCall(getDefault(),  callerId, src, dst, context,"", provider);
    }

    /***************** LISTENERS ***************************/
    public static AsteriskServerListener asteriskServerListener = new AsteriskServerListener(){
        @Override
        public void onNewAsteriskChannel(AsteriskChannel channel) {
            new Channel(channel).save();
        }

        @Override
        public void onNewMeetMeUser(MeetMeUser user) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onNewAgent(AsteriskAgentImpl agent) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onNewQueueEntry(AsteriskQueueEntry entry) {
            // TODO Auto-generated method stub

        }
    };

}
