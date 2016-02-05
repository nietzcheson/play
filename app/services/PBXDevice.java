package services;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by desarrollo1 on 04/02/2016.
 */
public class PBXDevice {
    private static Map<String, PBXDevice> devices = Collections.synchronizedMap(new LinkedHashMap<String, PBXDevice>());

    public String id;
    public String name;
    public String technology;
    public String calleridName;

    public long timeUpdated;

    private Map<String, Channel> channels = Collections.synchronizedMap(new HashMap<String, Channel>());

    public String getId(){
        return id;
    }

    public void addChannel(Channel channel){
        channels.put(channel.getId(), channel);
    }

    public void removeChannel(String id) {
        channels.remove(id);
    }

    public Collection<Channel> getChannels(){
        return channels.values();
    }

    public void save(){
        synchronized (devices) {
            if(id==null){
                id=(technology+"/"+name).toUpperCase();
            }

            timeUpdated = System.currentTimeMillis();
            devices.put(getId(), this);
        }
    }

    @Override
    public String toString() {
        return id + ((StringUtils.isNotBlank(calleridName))?" \"" +calleridName + "\"":"");
    }

    public static PBXDevice findById(String id){
        return devices.get(id);
    }

    public static PBXDevice findOrCreateById(String id) {
        PBXDevice device=null;
        synchronized (devices) {
            device = findById(id);

            if(device == null){
                int separator = id.indexOf('/');
                String tech = id.substring(0, separator).toUpperCase();
                String nameDev = id.substring(separator+1);

                device = new PBXDevice();
                device.technology = tech;
                device.name = nameDev;
                device.calleridName = "NOT KNOWN";
                device.save();
            }
        }

        return device;
    }

    public static List<PBXDevice> findAll(){
        List<PBXDevice>list = new ArrayList<PBXDevice>(devices.values());
        Collections.sort(list, new Comparator<PBXDevice>() {
            @Override
            public int compare(PBXDevice o1, PBXDevice o2) {
                return o1.id.compareTo(o2.id);
            }
        });
        return list;
    }
    public static List<PBXDevice> findAgent(){
        List<PBXDevice>list = new ArrayList<PBXDevice>();
        //List<PBXDevice>list = new ArrayList<PBXDevice>(devices.values());
        list.add(PBXDevice.findById("SIP/6004"));
        list.add(PBXDevice.findById("SIP/6005"));
        list.add(PBXDevice.findById("SIP/6006"));
        list.add(PBXDevice.findById("SIP/6007"));
        list.add(PBXDevice.findById("SIP/6008"));
        list.add(PBXDevice.findById("SIP/6009"));
        list.add(PBXDevice.findById("SIP/6010"));
        System.out.println("LISTA "+list.size());
        Collections.sort(list, new Comparator<PBXDevice>() {
            @Override
            public int compare(PBXDevice o1, PBXDevice o2) {
                return o1.id.compareTo(o2.id);
            }
        });
        return list;
    }


    public static List<PBXDevice> getPBXDeviceUpdatedSince(Date date){
        List<PBXDevice> list = new ArrayList<PBXDevice>();
        long timeSearch = date.getTime();

        synchronized (devices) {
            for(PBXDevice dev: devices.values()){
                //System.out.println("Device "+dev.id);

                if(timeSearch<dev.timeUpdated){
                    list.add(dev);
                }
            }
        }

        return list;
    }
}
