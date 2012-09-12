package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Level;

/**
 * Home object for domain model class Tag.
 * @see de.rochlitz.mygarderobe.jpa.entity.Tag
 * @author Hibernate Tools
 */
@Stateless
public class TagHome {

    @Inject  private Logger log;

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Tag transientInstance) {
	log.log(Level.FINEST,"persisting Tag instance");
	try {
	    entityManager.persist(transientInstance);
	    log.log(Level.FINEST,"persist successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"persist failed", re);
	    throw re;
	}
    }

    public void remove(Tag persistentInstance) {
	log.log(Level.FINEST,"removing Tag instance");
	try {
	    entityManager.remove(persistentInstance);
	    log.log(Level.FINEST,"remove successful");
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"remove failed", re);
	    throw re;
	}
    }

    public Tag merge(Tag detachedInstance) {
	log.log(Level.FINEST,"merging Tag instance");
	try {
	    Tag result = entityManager.merge(detachedInstance);
	    log.log(Level.FINEST,"merge successful");
	    return result;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"merge failed", re);
	    throw re;
	}
    }

    public Tag findById(Integer id) {
	log.log(Level.FINEST,"getting Tag instance with id: " + id);
	try {
	    Tag instance = entityManager.find(Tag.class, id);
	    log.log(Level.FINEST,"get successful");
	    return instance;
	} catch (RuntimeException re) {
	    log.log(Level.SEVERE,"get failed", re);
	    throw re;
	}
    }
}
