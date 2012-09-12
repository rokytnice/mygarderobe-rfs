package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class Outfit.
 * @see de.rochlitz.mygarderobe.jpa.entity.Outfit
 * @author Hibernate Tools
 */
@Stateless
public class OutfitHome {

    @Inject  private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Outfit transientInstance) {
	log.log(Level.FINEST,"persisting Outfit instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"persist failed", re);
	    throw re;
	}
    }

    public void remove(Outfit persistentInstance) {
	log.log(Level.FINEST,"removing Outfit instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"remove failed", re);
	    throw re;
	}
    }

    public Outfit merge(Outfit detachedInstance) {
	log.log(Level.FINEST,"merging Outfit instance");
	try {
	    Outfit result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"merge failed", re);
	    throw re;
	}
    }

    public Outfit findById(Integer id) {
	log.log(Level.FINEST,"getting Outfit instance with id: " + id);
	try {
	    Outfit instance = entityManager.find(Outfit.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"get failed", re);
	    throw re;
	}
    }
}
