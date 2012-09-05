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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "mygarderobe", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 458027087511696716L;
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String authority;
    private boolean enabled;
    private Integer currentOutfitId;
    private Set<Outfit> outfits = new HashSet<Outfit>(0);
    private Set<Tag> tags = new HashSet<Tag>(0);
    private Set<Image> images = new HashSet<Image>(0);

    public User() {
    }

    public User(String username, String password, String email, String authority, boolean enabled) {
	this.username = username;
	this.password = password;
	this.email = email;
	this.authority = authority;
	this.enabled = enabled;
    }

    public User(String username, String password, String email, String authority, boolean enabled, Integer currentOutfitId, Set<Outfit> outfits, Set<Tag> tags, Set<Image> images) {
	this.username = username;
	this.password = password;
	this.email = email;
	this.authority = authority;
	this.enabled = enabled;
	this.currentOutfitId = currentOutfitId;
	this.outfits = outfits;
	this.tags = tags;
	this.images = images;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    public Integer getUserId() {
	return this.userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
	return this.username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
	return this.password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Column(name = "authority", nullable = false)
    public String getAuthority() {
	return this.authority;
    }

    public void setAuthority(String authority) {
	this.authority = authority;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
	return this.enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

    @Column(name = "current_outfit_id")
    public Integer getCurrentOutfitId() {
	return this.currentOutfitId;
    }

    public void setCurrentOutfitId(Integer currentOutfitId) {
	this.currentOutfitId = currentOutfitId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Outfit> getOutfits() {
	return this.outfits;
    }

    public void setOutfits(Set<Outfit> outfits) {
	this.outfits = outfits;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Tag> getTags() {
	return this.tags;
    }

    public void setTags(Set<Tag> tags) {
	this.tags = tags;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Image> getImages() {
	return this.images;
    }

    public void setImages(Set<Image> images) {
	this.images = images;
    }

}
