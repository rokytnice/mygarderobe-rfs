package de.rochlitz.mygarderobe.jpa.entity;

// Generated 30.08.2012 09:22:52 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Outfit generated by hbm2java
 */
@Entity
@Table(name = "outfit", catalog = "mygarderobe")
public class Outfit implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9048252412908153193L;
    private Integer outfitId;
    private User user;
    private String name;
    private Set<ImageOutfit> imageOutfits = new HashSet<ImageOutfit>(0);
    private Set<OutfitTag> outfitTags = new HashSet<OutfitTag>(0);

    public Outfit() {
    }

    public Outfit(User user, String name) {
	this.user = user;
	this.name = name;
    }

    public Outfit(User user, String name, Set<ImageOutfit> imageOutfits, Set<OutfitTag> outfitTags) {
	this.user = user;
	this.name = name;
	this.imageOutfits = imageOutfits;
	this.outfitTags = outfitTags;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "outfit_id", unique = true, nullable = false)
    public Integer getOutfitId() {
	return this.outfitId;
    }

    public void setOutfitId(Integer outfitId) {
	this.outfitId = outfitId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "outfit")
    public Set<ImageOutfit> getImageOutfits() {
	return this.imageOutfits;
    }

    public void setImageOutfits(Set<ImageOutfit> imageOutfits) {
	this.imageOutfits = imageOutfits;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "outfit")
    public Set<OutfitTag> getOutfitTags() {
	return this.outfitTags;
    }

    public void setOutfitTags(Set<OutfitTag> outfitTags) {
	this.outfitTags = outfitTags;
    }

}
