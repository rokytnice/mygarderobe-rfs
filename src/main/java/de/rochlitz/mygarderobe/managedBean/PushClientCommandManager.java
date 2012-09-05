package de.rochlitz.mygarderobe.managedBean;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
 
import org.richfaces.cdi.push.Push;
 
@Named
@RequestScoped
@ManagedBean(name = "pushClientCommandManager", eager = true)
public class PushClientCommandManager {
 
    public static final String PUSH_CDI_TOPIC = "PushClientCommand";
 
    /**
     * clientseitige javascriptfunktion
     */
    private String clientCommand;
 
 
    @Inject
    @Push(topic = PUSH_CDI_TOPIC)
    Event<String> pushEvent;
 
    /**
     *
     * @param clientCommand to send
     */
    public void sendClientCommand() {
        pushEvent.fire(clientCommand);
        clientCommand = "";
    }
 
    public String getClientCommand() {
        return clientCommand;
    }

    public void setClientCommand(String clientCommand) {
        this.clientCommand = clientCommand;
    }

}