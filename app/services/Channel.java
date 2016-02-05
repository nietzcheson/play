package services;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.lang.StringUtils;
import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.CallerId;
import org.asteriskjava.live.ChannelState;
import org.asteriskjava.live.Extension;
import org.asteriskjava.live.ExtensionHistoryEntry;

import java.util.*;

/**
 * Created by desarrollo1 on 04/02/2016.
 */
public class Channel {
    static private Map<String, Channel> channels = Collections.synchronizedMap(new HashMap<String, Channel>());

    AsteriskChannel achannel;
    PBXDevice pbxDevice;

    public Channel(AsteriskChannel astChannel){
        achannel = astChannel;

    }

    public String getId(){
        return achannel.getId();
    }

    public String getName(){
        return achannel.getName();
    }

    public AsteriskChannel getDialingChannel() {
        return achannel.getDialingChannel();
    }

    public AsteriskChannel getDialedChannel() {
        return achannel.getDialedChannel();
    }

    public CallerId getCallerId() {
        return achannel.getCallerId();
    }

    public ChannelState getState() {
        return achannel.getState();
    }

    public AsteriskChannel getLinkedChannel() {
        return achannel.getLinkedChannel();
    }

    public List<ExtensionHistoryEntry> getExtensionHistory() {
        return achannel.getExtensionHistory();
    }

    public Object getLastUpdateMillis() {
        return achannel.getLastUpdateMillis();
    }

    public Date getDateOfCreation() {
        return achannel.getDateOfCreation();
    }

    public PBXDevice getPBXDevice() {
        return pbxDevice;
    }

    public int getDirection(){
        AsteriskChannel dialingC = achannel.getDialingChannel();
        AsteriskChannel dialedC = achannel.getDialedChannel();

        if(dialingC != null){
            return -1;
        }else if(dialedC != null){
            return 1;
        }else{
            AsteriskChannel linkedChannel = achannel.getLinkedChannel();
            if(linkedChannel!=null){
                CallerId callerId = linkedChannel.getCallerId();
                if(callerId.getName() == null){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                List<ExtensionHistoryEntry> history = achannel.getExtensionHistory();
                if(history.size()>0){
                    ExtensionHistoryEntry entry = history.get(history.size()-1);
                    Extension ext = entry.getExtension();
                    if(ext != null){
                        return 1;
                    }
                }

            }
        }

        return 0;
    }

    public Object getOtherEnd(){
        AsteriskChannel dialingC = achannel.getDialingChannel();
        AsteriskChannel dialedC = achannel.getDialedChannel();
        if(dialingC != null){

            return dialingC.getCallerId();
        }else if(dialedC != null){
            return dialedC.getCallerId();
        }else{

            AsteriskChannel linkedChannel = achannel.getLinkedChannel();
            if(linkedChannel!=null){
                return linkedChannel.getCallerId();
            }else{
                List<ExtensionHistoryEntry> history = achannel.getExtensionHistory();
                if(history.size()>0){
                    ExtensionHistoryEntry entry = history.get(history.size()-1);
                    Extension ext = entry.getExtension();
                    if(ext != null){
                        return ext;
                    }

                }

            }
        }

        return null;
    }

    public Object getOtherEndToStringCC(){
        Object o = getOtherEnd();

        if(o==null) return "";

        if(o instanceof CallerId){
            System.out.println("CHANNEL :"+o.toString());
            return o.toString();
        }

		/*if(o instanceof Extension){
			Extension ext = (Extension)o;
			StringBuilder sb = new StringBuilder();
			sb.append(ext.getExtension());
			if(ext.getApplication()!=null){
				sb.append("(").append(ext.getApplication()).append(")");
			}
		}*/

        return o.toString();
    }
    public Object getOtherEndToString(){
        Object o = getOtherEnd();

        if(o==null) return "";

        if(o instanceof CallerId){
            System.out.println("CHANNEL :"+o.toString());
            return o.toString();
        }

		/*if(o instanceof Extension){
			Extension ext = (Extension)o;
			StringBuilder sb = new StringBuilder();
			sb.append(ext.getExtension());
			if(ext.getApplication()!=null){
				sb.append("(").append(ext.getApplication()).append(")");
			}
		}*/

        return o.toString();
    }

    private void addPropertyChangeListener(PropertyChangeListener pcl) {
        achannel.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        achannel.removePropertyChangeListener(pcl);
    }

    public void save(){
        synchronized (channels) {
            String id = getId();
            if(!channels.containsKey(id)){
                channels.put(getId(), this);
                addPropertyChangeListener(channelPropertyListener);

                String name = getName();
                name = name.substring(0, name.indexOf('-'));

                pbxDevice = PBXDevice.findOrCreateById(name);
                pbxDevice.addChannel(this);
            }
        }
    }

    public void delete() {
        String id = getId();
        pbxDevice.removeChannel(id);
        channels.remove(id);
        removePropertyChangeListener(channelPropertyListener);
    }

    private static PropertyChangeListener channelPropertyListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            AsteriskChannel ach = (AsteriskChannel) event.getSource();
            Channel ch = findById(ach.getId());
            String propertyName = event.getPropertyName();

            if(StringUtils.equals(propertyName, "state")){
//		    	ChannelState oldValue = (ChannelState) event.getOldValue();
                ChannelState newValue = (ChannelState) event.getNewValue();

                if(newValue == ChannelState.HUNGUP){
                    if(ch!=null) ch.delete();
                }
            }

            ch.getPBXDevice().save();
        }
    };

    public static Channel findById(String id){
        return channels.get(id);
    }
}
