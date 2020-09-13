package UIBased.Model.Data;

public enum PlayerTypes 
{
	HUMAN,
	COMPUTER;
	@Override
	public String toString() 
	{
		switch (this) 
		{
		case HUMAN:
			return "Human";
		case COMPUTER:
			return "Computer";
		default:
			return "choose valid type";              
		}
	}
}
