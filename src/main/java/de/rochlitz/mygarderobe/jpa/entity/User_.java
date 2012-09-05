package de.rochlitz.mygarderobe.jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-30T13:13:30.074+0200")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> userId;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> authority;
	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile SingularAttribute<User, Integer> currentOutfitId;
	public static volatile SetAttribute<User, Outfit> outfits;
	public static volatile SetAttribute<User, Tag> tags;
	public static volatile SetAttribute<User, Image> images;
}
