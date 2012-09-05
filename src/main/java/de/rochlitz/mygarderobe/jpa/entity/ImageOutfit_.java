package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T09:22:54.004+0200")
@StaticMetamodel(ImageOutfit.class)
public class ImageOutfit_ {
	public static volatile SingularAttribute<ImageOutfit, Integer> recordId;
	public static volatile SingularAttribute<ImageOutfit, Image> image;
	public static volatile SingularAttribute<ImageOutfit, Outfit> outfit;
	public static volatile SingularAttribute<ImageOutfit, String> topPosition;
	public static volatile SingularAttribute<ImageOutfit, String> leftPosition;
	public static volatile SingularAttribute<ImageOutfit, Integer> zindex;
}
