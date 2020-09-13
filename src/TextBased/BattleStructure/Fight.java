package TextBased.BattleStructure;
import java.util.ArrayList;
import java.util.List;

import TextBased.Data.Referee;

/**
 * The fight class maintains a list of rounds
 * 
 * @author 
 */
public class Fight
{
	private int fightNumber;
	private Referee ref;
	private List<Round> roundList = new ArrayList<>();
	
	public Fight(int fightNum, Referee ref)
	{
		this.fightNumber = fightNum;
		this.ref = ref;
	}
	
	/**
	 * a single fight of the battle
	 * pets choose a skill, calculate random and conditional damage,
	 * and receive the damage dealt by the other pet
	 */
    public void executeFight()
	{
    	System.out.println("Fight Start");
		Round round = null;
		int roundNumber = 1;
		String winner = null;
		boolean victory = false;
		while(!victory)
		{
			round = new Round(ref);
			System.out.println("Current Round: " + roundNumber);
			round.printRoundStats();
			ref.buildRoundStartEvent(roundNumber); // call referee method to build round start event
			round.executeRound();
			roundList.add(round);
			winner = ref.checkFightWinner();
			if(winner != null)
				victory = true;
			roundNumber++;
		}
		round.printRoundStats();
		System.out.println(winner + " have won fight " + fightNumber);
		System.out.println("");
	}

}