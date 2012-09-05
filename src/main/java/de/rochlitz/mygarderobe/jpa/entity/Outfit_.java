package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T13:11:44.589+0200")
@StaticMetamodel(Outfit.class)
public class Outfit_ {
	public static volatile SingularAttribute<Outfit, Integer> outfitId;
	public static volatile SingularAttribute<Outfit, User> user;
	public static volatile SingularAttribute<Outfit, String> name;
	public static volatile SetAttribute<Outfit, ImageOutfit> imageOutfits;
	public static volatile SetAttribute<Outfit, OutfitTag> outfitTags;
}
