package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T09:22:54.009+0200")
@StaticMetamodel(ImageTag.class)
public class ImageTag_ {
	public static volatile SingularAttribute<ImageTag, Integer> recordId;
	public static volatile SingularAttribute<ImageTag, Image> image;
	public static volatile SingularAttribute<ImageTag, Tag> tag;
}
