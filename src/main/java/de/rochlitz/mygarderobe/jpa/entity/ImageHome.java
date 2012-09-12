package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class Image.
 * @see de.rochlitz.mygarderobe.jpa.entity.Image
 * @author Hibernate Tools
 */
@Stateless
public class ImageHome {

    @Inject
    private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Image transientInstance) {
	log.log(Level.FINEST,"persisting Image instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"persist failed", re);
	    throw re;
	}
    }

    public void remove(Image persistentInstance) {
	log.log(Level.FINEST,"removing Image instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"remove failed", re);
	    throw re;
	}
    }

    public Image merge(Image detachedInstance) {
	log.log(Level.FINEST,"merging Image instance");
	try {
	    Image result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"merge failed", re);
	    throw re;
	}
    }

    public Image findById(Integer id) {
	log.log(Level.FINEST,"getting Image instance with id: " + id);
	try {
	    Image instance = entityManager.find(Image.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"get failed", re);
	    throw re;
	}
    }
}
