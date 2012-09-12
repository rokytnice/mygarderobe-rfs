package de.rochlitz.mygarderobe.managedBean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.application.push.Topic;
import org.richfaces.application.push.TopicEvent;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;
import org.richfaces.application.push.impl.DefaultMessageDataSerializer;
 
@Named
@ViewScoped
@ManagedBean(name = "pushClientCommandManager", eager = true)
public class PushClientCommandManager implements Serializable{
 
    /**
     * 
     */
    private static final long serialVersionUID = 527852454405855273L;

    @Inject
    Logger log;
    
    public String push_cdi_topic;
    
 
    public String getPush_cdi_topic() {
        return push_cdi_topic;
    }

    

    /**
     * browser client javascript function
     */
    private String clientCommand;

    public  String getPushCdiTopic() {
        return push_cdi_topic;
    }

    private String username;
 
    Topic pushJmsTopic;
    TopicEvent tevent;
    TopicKey topicKey;
    @PostConstruct
    public void initialize() {
        if (username == null) {
            username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        
            push_cdi_topic = getUsername()+TopicKey.SUBCHANNEL_SEPARATOR+"PUSH_CDI_TOPIC";
//        push_cdi_topic = "PUSH_CDI_TOPIC";
        
        
        }
    }
 
//    @Inject
//    @Push(topic = push_cdi_topic, subtopic = "#{PushClientCommandManager.username}")
//    Event<String> pushEvent;
 
    /**
     *
     * @param clientCommand to send
     * @throws Exception 
     */
    public void sendClientCommand() throws Exception {
        try {
            TopicsContext topicsContext = TopicsContext.lookup();
            topicKey = new TopicKey(getPushCdiTopic());
            
//            pushJmsTopic = (Topic) topicsContext.getOrCreateTopic( topicKey ) ;
//            pushJmsTopic.setMessageDataSerializer(DefaultMessageDataSerializer.instance());
            //tevent = new TopicEvent(pushJmsTopic);
            //pushJmsTopic.publishEvent(tevent);
            
            topicsContext.publish(topicKey, clientCommand);
//	    pushEvent.fire(clientCommand);
//	    clientCommand = "";
	} catch (Exception e) {
	    log.log(Level.SEVERE, "Error in PushClientCommand.sendClient " + e.getMessage());
	    throw new Exception();
	}
    }
 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientCommand() {
        return clientCommand;
    }

    public void setClientCommand(String clientCommand) {
        this.clientCommand = clientCommand;
    }

}