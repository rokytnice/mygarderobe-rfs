package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T13:12:55.851+0200")
@StaticMetamodel(Tag.class)
public class Tag_ {
	public static volatile SingularAttribute<Tag, Integer> tagId;
	public static volatile SingularAttribute<Tag, User> user;
	public static volatile SingularAttribute<Tag, String> tagText;
	public static volatile SetAttribute<Tag, ImageTag> imageTags;
	public static volatile SetAttribute<Tag, OutfitTag> outfitTags;
}
