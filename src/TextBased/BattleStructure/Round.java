package TextBased.BattleStructure;
import TextBased.Data.Referee;

/**
 * Each round consists of each player choosing a skill
 * and the damage being calculated and applied 
 * @author 
 */
public class Round
{
	private Referee ref;
	
	public Round(Referee ref)
	{
		this.ref = ref;
	}
	
	/**
	 * Handles a round by calling methods for pets to choose their skills and attack
	 */
	public void executeRound()
	{
		chooseRoundSkills(); 
		doRoundAttacks(); 	
		ref.executeAttackEvents();
		ref.endRound();
	}
	
	/**
	 * loops through pets so they can choose their skill for the round
	 */
	public void chooseRoundSkills() 
	{
		for (int i = 0; i < ref.getNumPlayables(); i++) 
		{
			ref.chooseSkill(i);
		}
	}

	
	/**
	 *  loops through each pet to determine the damage inflicted upon each during the round
	 */
	public void doRoundAttacks() 
	{
		for (int i = 0; i < ref.getNumPlayables(); i++) 
		{
			ref.dealDamage(i);
		}
	}
		
	/**
	 * Prints all necessary information about both pets at the start of every round
	 */
	public void printRoundStats() 
	{
		for(int i = 0; i < ref.getNumPlayables(); i++)
		{
			ref.printStats(i);
		}
	}
}
