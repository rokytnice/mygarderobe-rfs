package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class Roles.
 * @see de.rochlitz.mygarderobe.jpa.entity.Roles
 * @author Hibernate Tools
 */
@Stateless
public class RolesHome {

    @Inject  private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Roles transientInstance) {
	log.log(Level.FINEST,"persisting Roles instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"persist failed", re);
	    throw re;
	}
    }

    public void remove(Roles persistentInstance) {
	log.log(Level.FINEST,"removing Roles instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"remove failed", re);
	    throw re;
	}
    }

    public Roles merge(Roles detachedInstance) {
	log.log(Level.FINEST,"merging Roles instance");
	try {
	    Roles result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"merge failed", re);
	    throw re;
	}
    }

    public Roles findById(RolesId id) {
	log.log(Level.FINEST,"getting Roles instance with id: " + id);
	try {
	    Roles instance = entityManager.find(Roles.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"get failed", re);
	    throw re;
	}
    }
}
