package TextBased.Data;
/**
 * The purpose of this enumeration is to identify pet types (opposed to using instanceof). This
 * enumeration has a toString method, which prints the name of the pet type in a user-friendly
 * format. This format will convert the enumeration name to replace underscores
 * with spaces and only capitalize the first letter of each word.
 * @author kruge
 *
 */
public enum PetTypes 
{
	POWER,
	SPEED,
	INTELLIGENCE;
	@Override
	public String toString() 
	{
		switch (this) 
		{
		case POWER:
			return "Power";
		case SPEED:
			return "Speed";
		case INTELLIGENCE:
			return "Intelligence";
		default:
			return "choose valid type";              
		}
	}
}
