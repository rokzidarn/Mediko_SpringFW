package si.fri.t15.login.controllers;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import si.fri.t15.models.user.User;


@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	EntityManager em;
	
	
    @Override
    @Transactional
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        for (SecurityContext securityContext : event.getSecurityContexts())
        {
            Authentication authentication = securityContext.getAuthentication();
            long creationTime = ((StandardSessionFacade)event.getSource()).getCreationTime();
            User user = (User) authentication.getPrincipal();
            
            user = em.merge(user);
            user.setLastLogin(new Timestamp(creationTime));
            
            em.persist(user);
        }
    }

}