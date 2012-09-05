package org.jboss.tools.example.richfaces.service;

import org.jboss.tools.example.richfaces.model.Member;

import de.rochlitz.mygarderobe.jpa.entity.User;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Member> memberEventSrc;

   public void register(Member member) throws Exception {
      log.info("Registering " + member.getName());
      
      User u = new User();
      u.setEmail(member.getEmail());
      u.setUsername(member.getName());
      u.setAuthority("ROLE_USER");
      u.setPassword(member.getName());
      em.persist(u);
      
//      em.persist(member);
      memberEventSrc.fire(member);
      
      
   }
}
