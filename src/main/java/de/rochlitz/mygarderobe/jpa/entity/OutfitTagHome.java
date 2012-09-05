package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class OutfitTag.
 * @see de.rochlitz.mygarderobe.jpa.entity.OutfitTag
 * @author Hibernate Tools
 */
@Stateless
public class OutfitTagHome {

    @Inject  private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OutfitTag transientInstance) {
	log.log(Level.FINEST,"persisting OutfitTag instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.ALL,"persist failed", re);
	    throw re;
	}
    }

    public void remove(OutfitTag persistentInstance) {
	log.log(Level.FINEST,"removing OutfitTag instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.ALL,"remove failed", re);
	    throw re;
	}
    }

    public OutfitTag merge(OutfitTag detachedInstance) {
	log.log(Level.FINEST,"merging OutfitTag instance");
	try {
	    OutfitTag result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.ALL,"merge failed", re);
	    throw re;
	}
    }

    public OutfitTag findById(Integer id) {
	log.log(Level.FINEST,"getting OutfitTag instance with id: " + id);
	try {
	    OutfitTag instance = entityManager.find(OutfitTag.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.ALL,"get failed", re);
	    throw re;
	}
    }
}
