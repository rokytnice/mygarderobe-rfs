package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T13:10:13.367+0200")
@StaticMetamodel(Image.class)
public class Image_ {
	public static volatile SingularAttribute<Image, Integer> imageId;
	public static volatile SingularAttribute<Image, User> user;
	public static volatile SingularAttribute<Image, String> filename;
	public static volatile SingularAttribute<Image, String> name;
	public static volatile SetAttribute<Image, ImageOutfit> imageOutfits;
	public static volatile SetAttribute<Image, ImageTag> imageTags;
}
