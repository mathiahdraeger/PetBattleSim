package TextBased.BattleStructure;

import java.util.ArrayList;
import java.util.List;

import TextBased.Data.Referee;

/**
 *  This class creates multiple fights and determines the winner
 *  after all fights have taken place.
 */
public class Battle 
{
	private int numFights;
	
	private Referee ref;
	private Fight fight;

	private List<Fight> fightList = new ArrayList<>();
	
	public Battle(BattleSettings settings)
	{
		this.numFights = settings.getNumFights();
		this.ref = settings.getRef();
	}
    
	/**
	 * Calls the proper number of fights
	 */
	public void battle()
	{
		for(int i = 1; i < numFights + 1; i++)
		{
			//Resets the stats of all players between fights
			ref.resetPlayables();		
			fight = new Fight(i, ref);
			ref.buildFightStartEvent(i); 
			fight.executeFight();
			fightList.add(fight);
		}
	}
	
	/**
	 * Determines the winner of the battle
	 * @return winner
	 */
	public String crownWinner()
	{
		String winningPets;
		winningPets = ref.determineBattleWinner();
		return winningPets;
	}
}
