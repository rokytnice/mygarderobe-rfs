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
 * Tag generated by hbm2java
 */
@Entity
@Table(name = "tag", catalog = "mygarderobe")
public class Tag implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2515236947890324823L;
    private Integer tagId;
    private User user;
    private String tagText;
    private Set<ImageTag> imageTags = new HashSet<ImageTag>(0);
    private Set<OutfitTag> outfitTags = new HashSet<OutfitTag>(0);

    public Tag() {
    }

    public Tag(User user, String tagText) {
	this.user = user;
	this.tagText = tagText;
    }

    public Tag(User user, String tagText, Set<ImageTag> imageTags, Set<OutfitTag> outfitTags) {
	this.user = user;
	this.tagText = tagText;
	this.imageTags = imageTags;
	this.outfitTags = outfitTags;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id", unique = true, nullable = false)
    public Integer getTagId() {
	return this.tagId;
    }

    public void setTagId(Integer tagId) {
	this.tagId = tagId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @Column(name = "tag_text", nullable = false)
    public String getTagText() {
	return this.tagText;
    }

    public void setTagText(String tagText) {
	this.tagText = tagText;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    public Set<ImageTag> getImageTags() {
	return this.imageTags;
    }

    public void setImageTags(Set<ImageTag> imageTags) {
	this.imageTags = imageTags;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    public Set<OutfitTag> getOutfitTags() {
	return this.outfitTags;
    }

    public void setOutfitTags(Set<OutfitTag> outfitTags) {
	this.outfitTags = outfitTags;
    }

}
