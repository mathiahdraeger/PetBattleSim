package TextBased.Data;
import java.util.Random;
import java.util.Set;

import TextBased.Calculators.IntelligenceCalculator;
import TextBased.Calculators.PowerCalculator;
import TextBased.Calculators.SpeedCalculator;
import TextBased.Events.AttackEvent;
import TextBased.Events.AttackEvent.AttackEventBuilder;
import TextBased.Events.FightStartEvent;
import TextBased.Events.FightStartEvent.FightStartEventBuilder;
import TextBased.Events.PlayerEventInfo;
import TextBased.Events.PlayerEventInfo.PlayerEventInfoBuilder;
import TextBased.Events.RoundStartEvent;
import TextBased.Events.RoundStartEvent.RoundStartEventBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Shared.Skills.Skills;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * This class acts as the mediator for the game.
 * It maintains all random number generators and tracks the playableList, playable HP, and skill
 * recharge times for the game
 * @author 
 */
@SuppressWarnings("deprecation")
public class Referee extends Observable
{
	private Random generator;
	private long seed;
	private double battleHp;
	
	private List<Playable> playableList;
	private List<Skills> skillList;
	private List<Double> damageDifferenceList;
	private List<Integer> winTally; //parallel list to playableList to track number of wins for each playable
	private Queue<AttackEvent> attackEventQueue;
	ObservableList<Playable> obsList = FXCollections.observableArrayList();

	
	private PowerCalculator pCalc;
	private SpeedCalculator sCalc;
	private IntelligenceCalculator iCalc;

	public Referee()
	{
		playableList = new ArrayList<>();
		skillList = new ArrayList<>();
		damageDifferenceList = new ArrayList<>();
		winTally = new ArrayList<>();
		attackEventQueue = new LinkedList<>();
		
		pCalc = new PowerCalculator(this);
		sCalc = new SpeedCalculator(this);
		iCalc = new IntelligenceCalculator(this);		
	}	
	
	/**
	 * adds the playable to the ArrayList
	 * @param playable
	 */
	public void addPlayable(Playable playable)
	{
		playableList.add(playable);
		winTally.add(0);
		skillList.add(null);
		damageDifferenceList.add((double) 0);
		obsList.add(playable);
		addObserver(playable);
	}

	/**
	 * Sets the new seed and restarts the
	 * random generator with the seed
	 * @param seed
	 */
	public void setSeed(long seed) 
	{
		this.seed = seed;
		generator = new Random(seed);
	}

	/**
	 * @param i index of playable
	 * @return the playable with the given index
	 */
	public Playable getPlayable(int i) 
	{
		return playableList.get(i);
	}
	
	/**
	 * @param i index of playable
	 * @return the skill of the playable with index i
	 */
	public Skills getSkill(int i)
	{
		return skillList.get(i);
	}
	
	/**
	 * returns the next awake playable
	 * @param index
	 * @return
	 */
	public Playable getNextPlayable(int index)
	{
		return playableList.get(getNextIndex(index));
	}
	
	/**
	 * Returns the damageDifference of the playable at index
	 * @param index
	 * @return
	 */
	public double getRandomDamageDifference(int index) 
	{
		return damageDifferenceList.get(index);
	}
	
	/**
	 * Returns the index of the next awake playable
	 * @param startIndex the index of the current playable
	 * @return index of the next awake playable, -1 if not found
	 */
	public int getNextIndex(int startIndex)
	{
		int size = playableList.size();
		for(int i = startIndex + 1; i < (size + startIndex + 1); i++)
		{
			if(playableList.get(i % size).isAwake())
				return i % size;
		}
		return -1;
	}
	
	/**
	 * Returns the random number generator used for random damage
	 * @return 
	 */
	public Random getRNG()
	{
		return generator;
	}
	
	public int getNumPlayables()
	{
		return playableList.size();
	}

	/**
	 * Builds a new attackEvent and store it in the queue.
	 * Builder should handle null checks.
	 * @param randomDamage random damage of the attack
	 * @param conditionalDamage conditional damage of the attack
	 * @param index the index of the attacking pet
	 */
	public void buildAttackEvent(double randomDamage, double conditionalDamage, int index) 
	{
		AttackEventBuilder attackBuilder = new AttackEvent.AttackEventBuilder();
		AttackEvent attackEvent = attackBuilder
				.withAttackingPlayableIndex(index)
				.withVictimPlayableIndex(getNextIndex(index))
				.withAttackingSkillChoice(skillList.get(index))
				.withRandomDamage(randomDamage)
				.withConditionalDamage(conditionalDamage)
				.withPredictedSkillEnum(getPlayable(index).getSkillPrediction())
				.build();
		attackEventQueue.add(attackEvent);
	
	}
	
	/**
	 * Builds a new RoundStartEvent.
	 * Builder should handle null checks.
	 * @param roundNumber
	 */
	public void buildRoundStartEvent(int roundNumber) 
	{
		RoundStartEventBuilder roundStartBuilder = new RoundStartEvent.RoundStartEventBuilder();
		RoundStartEvent roundStartEvent = roundStartBuilder
				.withRoundNumber(roundNumber)
				.build();
		
		removeDeadPets();
		setChanged();
		notifyObservers(roundStartEvent);
		clearChanged();
	}

	
	/**
	 * if a pet is dead, set it's HP to negative infinity so it
	 * can't win ties
	 */
	private void removeDeadPets() 
	{
		for(int i = 0; i < getNumPlayables(); i++)
		{
			Playable currentPlayable = playableList.get(i);
			if(!currentPlayable.isAwake())
				currentPlayable.setCurrentHp(Double.NEGATIVE_INFINITY);
		}
	}

	/**
	 * Builds a new FightStartEvent.
	 * Builder should handle null checks.
	 * @param fightNumber
	 */
	public void buildFightStartEvent(int fightNumber) 
	{
		PlayerEventInfoBuilder playerEventInfoBuilder = new PlayerEventInfo.PlayerEventInfoBuilder();
		ArrayList<PlayerEventInfo> playerEventInfoList = new ArrayList<>();
		
		for(int i = 0; i < getNumPlayables(); i++)
		{
			Playable pet = playableList.get(i);
			playerEventInfoList.add(playerEventInfoBuilder
					.withPetName(pet.getPetName())
					.withPetType(pet.getPetType())
					.withPlayerType(pet.getPlayerType())
					.withStartingHp(battleHp)
					.build());
		}
		
		FightStartEventBuilder fightStartBuilder = new FightStartEvent.FightStartEventBuilder();
		FightStartEvent fightStartEvent = fightStartBuilder
				.withFightNumber(fightNumber)
				.withPlayerEventInfo(playerEventInfoList)
				.build();
		
		resetLists();
		setChanged();
		notifyObservers(fightStartEvent);
		clearChanged();
	}

	/**
	 * clears all data from all lists except playableList and winTally
	 */
	private void resetLists() 
	{
		for(int i = 0; i < getNumPlayables(); i++)
		{
			damageDifferenceList.set(i, (double) 0);
			skillList.set(i, null);
		}
		attackEventQueue.clear();
	}

	/**
	 * Builds a list of PlayerEventInfo and returns it.
	 * @return
	 */
	private ArrayList<PlayerEventInfo> buildPlayerEventInfoList() 
	{
		
		ArrayList<PlayerEventInfo> playerEventInfoList = new ArrayList<PlayerEventInfo>();
		PlayerEventInfoBuilder playerEventInfoBuilder = new PlayerEventInfo.PlayerEventInfoBuilder();
		Set<Skills> skillSet =  new HashSet<>();
		for(int i = 0; i < Skills.values().length; i++)
		{
			skillSet.add(Skills.values()[i]);
		}
		for(int i = 0; i < playableList.size(); i++)
		{
			Playable currentPlayable = getPlayable(i);
			PlayerEventInfo playerEventInfo = playerEventInfoBuilder
					.withPetType(currentPlayable.getPetType())
					.withPlayerType(currentPlayable.getPlayerType())
					.withSkillSet(skillSet)
					.withStartingHp(battleHp)
					.build();
			playerEventInfoList.add(playerEventInfo);
		}
		return playerEventInfoList;
	}

	/**
	 * Checks if the Playable at index is awake.
	 * If it is awake, calls the appropriate Calculator.calculateDamage()
	 * @param index of attacking playable
	 */
	public void dealDamage(int index)
	{
        Playable currentPlayable = getPlayable(index);
        if(currentPlayable.isAwake())
        {
        	PetTypes type = currentPlayable.getPetType();
        	if(type == PetTypes.POWER)
        		pCalc.calculateDamage(index);
        	else if(type == PetTypes.SPEED)
        		sCalc.calculateDamage(index);
        	else if(type == PetTypes.INTELLIGENCE)
        		iCalc.calculateDamage(index);
        }
	}
	
	/**
	 * Resets all Playables
	 */
	public void resetPlayables()
	{
		for(int i = 0; i < playableList.size(); i++)
		{
			getPlayable(i).reset();
		}
	}
	
	public void setBattleHp(double battleHp)
	{
		this.battleHp = battleHp;
	}	
	
	/**
	 * if all but one pet is dead, returns the winning pet
	 * if all pets are dead calls tieWinner()
	 * @return string of the "<playerName> and <petName>"
	 */
	public String checkFightWinner() 
	{
		int firstAlive = getNextIndex(-1);
		int winnerIndex = getNextIndex(firstAlive);
		if(winnerIndex == firstAlive && winnerIndex != -1) //last alive
		{
			tallyFightWin(winnerIndex);
			return playableList.get(winnerIndex).getPlayerName() + " and " 
					+ playableList.get(winnerIndex).getPetName();
		}
		else if(winnerIndex == -1) //all dead
		{
			winnerIndex = tieWinner();
			tallyFightWin(winnerIndex);
			return playableList.get(winnerIndex).getPlayerName() + " and " 
					+ playableList.get(winnerIndex).getPetName();
		}
		else //more than 1 alive
		{
			return null;
		}
	}
	
	/**
	 * returns the index of the Playable who just died with the least negative Hp
	 * @return
	 */
	private int tieWinner() 
	{
		int winnerIndex = 0;
		for(int i = winnerIndex + 1; i < getNumPlayables(); i++)
			if(playableList.get(i).getCurrentHp() > playableList.get(winnerIndex).getCurrentHp()) 
				winnerIndex = i;
		return winnerIndex;
	}
	
	/*
	 Takes the index of the winner of a fight as a parameter
	 and awards the win to that playable.
	 To accomplish this, the number of wins at the given index of
	 winTally is incremented.
	 @param winningPlayable
	 */
	private void tallyFightWin(int index)
	{
		int currentNumWinsAtIndex = winTally.get(index);
		winTally.set(index, ++currentNumWinsAtIndex); 
	}
	
	/**
	 * Returns a string containing all winning players and pets
	 * @return
	 */
	public String determineBattleWinner()
	{
		String winningPets = "";
		int mostWins = 0;
		for(int i = 0; i < getNumPlayables(); i++)
		{
			int checkWins = winTally.get(i);
			if(checkWins > mostWins)
				mostWins = checkWins;
		}
		for(int i = 0; i < getNumPlayables(); i++ )
		{
			if(winTally.get(i) == mostWins)
			{
				Playable winner = getPlayable(i);
				winningPets += (winner.getPlayerName() + " and " 
						+ winner.getPetName() + " with " + winTally.get(i) + "\n");
			}
		}
		return winningPets;
	}
	
	/**
	 * Checks every Playable.  
	 * If isAwake, recharges skills
	 * else, sets Hp to NEGATIVE_INFINITY so that 
	 * the playable won't be mistaken for a winner in tieWinner()
	 */
	public void endRound()
	{
		for(int i = 0; i < getNumPlayables(); i++)
		{
			Playable currentPlayable = playableList.get(i);
			if(currentPlayable.isAwake())
				currentPlayable.decrementRechargeTimes();
		}
		System.out.println("");
	}
	
	/**
	 * Checks if the Playable at index is awake, if it is, chooses a skill
	 * @param index
	 */
	public void chooseSkill(int index)
	{
		Playable currentPlayable = playableList.get(index);
		if(currentPlayable.isAwake())
			skillList.set(index, currentPlayable.chooseSkill());
	}
	
	/**
	 * takes all attack events from the queue
	 * updates hp of all pets based on the events
	 */
	public void executeAttackEvents()
	{
		while(!attackEventQueue.isEmpty())  
		{
			AttackEvent attack = attackEventQueue.remove();
			int attackingIndex = attack.getAttackingPlayableIndex();
			int victimIndex = attack.getVictimPlayableIndex();
			double randomDamage = attack.getRandomDamage();
			double conditionalDamage = attack.getConditionalDamage();
			double attackingDamageDifference = damageDifferenceList.get(attackingIndex);
			double victimDamageDifference = damageDifferenceList.get(victimIndex);
			
			playableList.get(victimIndex).updateHp(randomDamage + conditionalDamage);
			damageDifferenceList.set(attackingIndex, (attackingDamageDifference - randomDamage));
			damageDifferenceList.set(victimIndex, (victimDamageDifference + randomDamage));
			System.out.println(playableList.get(attackingIndex).getPetName() + " attacked " 
					+ playableList.get(victimIndex).getPetName() + " with " 
					+ skillList.get(attackingIndex) + " and did " 
					+ (randomDamage + conditionalDamage) + " damage.");
			
			setChanged();
			notifyObservers(attack);
			clearChanged();
		}
		
	}	
	
	/**
	 * If the pet at index is awake, prints the stats
	 * @param index
	 */
	public void printStats(int index)
	{
		if(playableList.get(index).isAwake())
			System.out.println(playableStats(index));
	}	
	
	/**
	 * returns the stats of the playable at index
	 * @param index
	 * @return
	 */
	private String playableStats(int index)
	{
		String stats = "";
		Playable currentPlayable = playableList.get(index);
		stats = stats + currentPlayable.getPlayerName() + ": Pet: " 
				+ currentPlayable.getPetName() + " Type: " + currentPlayable.getPetType()
				+ " HP: " + currentPlayable.getCurrentHp() + " ";
		if(skillList.get(index) != null)
		{
			stats = stats + "Recharging: ";
			for(int i = 0; i < Skills.values().length; i++)
			{
				Skills skill = Skills.values()[i];
				int rechargeTime = currentPlayable.getSkillRechargeTime(skill);
				if(rechargeTime != 0)
					stats = stats + skill + " " + rechargeTime + " turns. ";
			}
		}
		stats = stats + "Damage Difference: " + damageDifferenceList.get(index);
		return stats;
	}	
}




