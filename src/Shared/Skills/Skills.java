package Shared.Skills;

/**
 * This is an enum containing the set of skills to be used by a pet.
 * 
 * @author kruge
 */

public enum Skills 
{
	ROCK_THROW, SCISSORS_POKE, PAPER_CUT, SHOOT_THE_MOON, REVERSAL_OF_FORTUNE;

	@Override
	public String toString() 
	{
		switch (this) {
		case ROCK_THROW:
			return "Rock Throw";
		case SCISSORS_POKE:
			return "Scissors Poke";
		case PAPER_CUT:
			return "Paper Cut";
		case SHOOT_THE_MOON:
			return "Shoot the Moon";
		case REVERSAL_OF_FORTUNE:
			return "Reversal of Fortune";
		default:
			return "choose valid type";
		}
	}
}