package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class ImageTag.
 * @see de.rochlitz.mygarderobe.jpa.entity.ImageTag
 * @author Hibernate Tools
 */
@Stateless
public class ImageTagHome {

    @Inject  private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(ImageTag transientInstance) {
	log.log( Level.FINEST,"persisting ImageTag instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"persist failed", re);
	    throw re;
	}
    }

    public void remove(ImageTag persistentInstance) {
	log.log(Level.FINEST,"removing ImageTag instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"remove failed", re);
	    throw re;
	}
    }

    public ImageTag merge(ImageTag detachedInstance) {
	log.log(Level.FINEST,"merging ImageTag instance");
	try {
	    ImageTag result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"merge failed", re);
	    throw re;
	}
    }

    public ImageTag findById(Integer id) {
	log.log(Level.FINEST,"getting ImageTag instance with id: " + id);
	try {
	    ImageTag instance = entityManager.find(ImageTag.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"get failed", re);
	    throw re;
	}
    }
}
