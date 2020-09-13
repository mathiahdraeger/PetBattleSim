package TextBased.Data;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import Shared.Skills.PaperCut;
import Shared.Skills.ReversalOfFortune;
import Shared.Skills.RockThrow;
import Shared.Skills.ScissorsPoke;
import Shared.Skills.ShootTheMoon;
import Shared.Skills.Skill;
import Shared.Skills.Skills;
import TextBased.Events.AttackEvent;
import TextBased.Events.BaseEvent;
import TextBased.Events.FightStartEvent;
import TextBased.Events.PlayerEventInfo;
import TextBased.Events.RoundStartEvent;

@SuppressWarnings("deprecation")
public class SmartAI extends Pet implements Observer
{
	private Random skillGenerator; // new random generator for AI
	private final long SEED = 1234567890; //hard-coded seed for AI random skillGenerator
	private final double DAMAGE_DIFF_THRESHOLD = 3;
	private final int ROCKNUM = 1;
	private final int SCISSORSNUM = 2;
	private final int PAPERNUM = 3;
	private final int MOONNUM = 4;
	private final int REVERSALNUM = 5;
	private final int REVERSAL_THRESH = 20;
	private final int PREDICT_REVERSAL_THRESH = -20;
	private static final double SPEED_UPPER_THRESHOLD = 0.75;
	private static final double SPEED_LOWER_THRESHOLD = 0.25;
	private int myIndex;
	private int myVictimIndex;
	private double damageDifference;
	private ArrayList<ImportantPetInfo> petInfoList;
	

	public SmartAI(String playerName, String petName, PetTypes type, double battleHp) 
	{
		super(playerName, petName, type, battleHp);
		this.skillGenerator = new Random(SEED);
		setPlayerType(PlayerTypes.SMARTAI);
		petInfoList = new ArrayList<ImportantPetInfo>();
	}
	
	/**
	 * gets input from the AI and returns a valid skill
	 */
	public Skills chooseSkill() 
	{
		Skills currentPetSkill;
		if(getPetType() == PetTypes.POWER)
			currentPetSkill = chooseSkillPower();
		else if(getPetType() == PetTypes.SPEED)
			currentPetSkill = chooseSkillSpeed();
		else if(getPetType() == PetTypes.INTELLIGENCE)
			currentPetSkill = chooseSkillIntelligence();
		else
			currentPetSkill = chooseRandomSkill();
		return currentPetSkill;
	}
	
	/**
	 * If SmartAI is of type Intelligence, this method selects its skill
	 */
	private Skills chooseSkillIntelligence() 
	{
		Skills currentPetSkill = null;
		ImportantPetInfo victim = petInfoList.get(myVictimIndex);
		if(victim.getPetType() == PetTypes.INTELLIGENCE)
		{
			currentPetSkill = intelligenceVsIntelligence();
		}
		else
		{
			currentPetSkill = intelligenceVsOther();
		}
		getSkill(currentPetSkill).useSkill();
		return currentPetSkill;
	}
	
	
	/**
	 * If SmartAi and its opponent are both of type intelligence, this method selects
	 * the skill for the smart Ai.  It is based on a offensive strategy.
	 * optimized for 2 pet battles, may not work well in 3+ pet battles
	 * @return
	 */
	private Skills intelligenceVsIntelligence() 
	{
		Skills currentPetSkill = null;
		ImportantPetInfo victim = petInfoList.get(myVictimIndex);
		ImportantPetInfo myInfo = petInfoList.get(myIndex);
		// three checks for +3 conditional damage
		if(!victim.isRecharged(Skills.SCISSORS_POKE) && myInfo.isRecharged(Skills.ROCK_THROW))
		{
			currentPetSkill = Skills.ROCK_THROW;
		}
		else if(!victim.isRecharged(Skills.PAPER_CUT) && myInfo.isRecharged(Skills.SCISSORS_POKE))
		{
			currentPetSkill = Skills.SCISSORS_POKE;
		}
		else if(!victim.isRecharged(Skills.ROCK_THROW) && myInfo.isRecharged(Skills.PAPER_CUT))
		{
			currentPetSkill = Skills.PAPER_CUT;
		}
		// if we will get +2 from Moon recharging, use a +2 skill
		else if(!victim.isRecharged(Skills.SHOOT_THE_MOON))
		{
			if(!victim.isRecharged(Skills.SCISSORS_POKE) && myInfo.isRecharged(Skills.SCISSORS_POKE))
			{
				currentPetSkill = Skills.SCISSORS_POKE;
			}
			else if(!victim.isRecharged(Skills.PAPER_CUT) && myInfo.isRecharged(Skills.PAPER_CUT))
			{
				currentPetSkill = Skills.PAPER_CUT;
			}
			else if(!victim.isRecharged(Skills.ROCK_THROW) && myInfo.isRecharged(Skills.ROCK_THROW))
			{
				currentPetSkill = Skills.ROCK_THROW;
			}
			else
			{
				currentPetSkill = chooseRandomSkill();
			}
		}
		// we can't do conditional based on Rock, Paper, or Scissors, so try to use Reversal
		else if(myInfo.isRecharged(Skills.REVERSAL_OF_FORTUNE))
		{
			currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
		}
		// if no skill chosen, pick randomly
		else
		{
			currentPetSkill = chooseRandomSkill();
		}
		return currentPetSkill;
	}
	
	/**
	 * If SmartAi is of type intelligence and its opponent is not, this method selects
	 * the skill for the smart Ai.  This is based on a defensive strategy.  
	 * Our AI will choose the skill that would be critical hit by the enemy's recharging skill.
	 * Optimized for 2 pet battles, may not work well in 3+ pet battles.
	 * @return
	 */
	private Skills intelligenceVsOther() 
	{
		Skills currentPetSkill = null;
		ImportantPetInfo victim = petInfoList.get(myVictimIndex);
		ImportantPetInfo myInfo = petInfoList.get(myIndex);
		
		if(!victim.isRecharged(Skills.ROCK_THROW) && myInfo.isRecharged(Skills.SCISSORS_POKE))
		{
			currentPetSkill = Skills.SCISSORS_POKE;
		}
		else if(!victim.isRecharged(Skills.SCISSORS_POKE) && myInfo.isRecharged(Skills.PAPER_CUT))
		{
			currentPetSkill = Skills.PAPER_CUT;
		}
		else if(!victim.isRecharged(Skills.PAPER_CUT) && myInfo.isRecharged(Skills.ROCK_THROW))
		{
			currentPetSkill = Skills.ROCK_THROW;
		}
		else if(myInfo.isRecharged(Skills.SHOOT_THE_MOON) && !enemyIsIntelligence())
		{
			currentPetSkill = Skills.SHOOT_THE_MOON;
			predictSkill();
		}
		else if(myInfo.isRecharged(Skills.REVERSAL_OF_FORTUNE) && damageDifference > 0)
		{
			currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
		}
		else
		{
			currentPetSkill = chooseRandomSkill();
		}
		return currentPetSkill;
	}
	
	/**
	 * If smartAI is of type Speed, this method chooses its skill
	 * NOT FINISHED
	 * Logic in this method was for testing purposes to see which type
	 * we should continue optimizing
	 */
	private Skills chooseSkillSpeed() 
	{
		Skills currentPetSkill = null;
		ImportantPetInfo victim = petInfoList.get(myVictimIndex);
		ImportantPetInfo myInfo = petInfoList.get(myIndex);
		double victimHpPercent = victim.getHp() / victim.getMaxHp();
		if(victimHpPercent >= SPEED_UPPER_THRESHOLD && myInfo.isRecharged(Skills.ROCK_THROW))
		{
			currentPetSkill = Skills.ROCK_THROW;
		}
		else if(victimHpPercent < SPEED_UPPER_THRESHOLD 
				&& victimHpPercent >= SPEED_LOWER_THRESHOLD 
				&& myInfo.isRecharged(Skills.SCISSORS_POKE))
		{
			currentPetSkill = Skills.SCISSORS_POKE;
		}
		else if(victimHpPercent < SPEED_LOWER_THRESHOLD && myInfo.isRecharged(Skills.PAPER_CUT))
		{
			currentPetSkill = Skills.PAPER_CUT;
		}
		else if(myInfo.isRecharged(Skills.SHOOT_THE_MOON) && !enemyIsIntelligence())
		{
			currentPetSkill = Skills.SHOOT_THE_MOON;
			predictSkill();
		}
		else if(myInfo.isRecharged(Skills.REVERSAL_OF_FORTUNE) && damageDifference > DAMAGE_DIFF_THRESHOLD)
		{
			currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
		}
		else
		{
			currentPetSkill = chooseRandomSkill();
		}
		getSkill(currentPetSkill).useSkill();
		return currentPetSkill;
	}

	/**
	 * Given a skillNum, this method returns a skill instance
	 * @param skillEnum
	 * @return
	 */
	private Skill getSkill(Skills skillEnum) 
	{
		Skill skill = null;
		switch (skillEnum) 
		{
		case ROCK_THROW:
			skill = getRock();
			break;
		case PAPER_CUT:
			skill = getPaper();
			break;
		case SCISSORS_POKE:
			skill = getScissors();
			break;
		case SHOOT_THE_MOON:
			skill = getMoon();
			break;
		case REVERSAL_OF_FORTUNE:
			skill = getReversal();
			break;
		}
		return skill;
	}
	
	/**
	 * If smartAI is of type Power, this method chooses its skill
	 * NOT FINISHED
	 * Logic in this method was for testing purposes to see which type
	 * we should continue optimizing
	 */
	private Skills chooseSkillPower() 
	{
		Skills currentPetSkill = null;
		Skill skill = null;
		boolean valid = false;
		int skillNum = -1;
		while(!valid)
		{
			skillNum = -1;
			// if using reversal of fortune would immediately kill the enemy, use it 
			// (provided that it is not charging)
			if(reversalWillKill() && !isRecharging(Skills.REVERSAL_OF_FORTUNE) && 
					damageDifference >= DAMAGE_DIFF_THRESHOLD) 
				skillNum = REVERSALNUM;
			
			//if reversal would do 20 or more damage, use it
			else if(!isRecharging(Skills.REVERSAL_OF_FORTUNE) && 
					damageDifference >= REVERSAL_THRESH) 
				skillNum = REVERSALNUM;
			
			//If enemy has three skills recharging, use shoot the moon
			else if(moonAndReversalRecharging() && !myMoonRecharging() && enemyCommonSkillRecharging() != null && !enemyIsIntelligence())
				skillNum = MOONNUM;
			
			//If enemy has both shoot the moon and reversal recharging, use shoot the moon
			else if(moonAndReversalRecharging() && !myMoonRecharging() && !enemyIsIntelligence())
				skillNum = MOONNUM;
			
			//If my shoot the moon is recharging and enemy has three skills recharging,
			// make an informed choice of common skill
			else if(myMoonRecharging() && moonAndReversalRecharging() && enemyCommonSkillRecharging() != null)
			{

				if (enemyCommonSkillRecharging() == Skills.ROCK_THROW)
				{
					if(!isRecharging(Skills.SCISSORS_POKE))
					    skillNum = SCISSORSNUM;
					else if(damageDifference > DAMAGE_DIFF_THRESHOLD && !isRecharging(Skills.REVERSAL_OF_FORTUNE))
						skillNum = REVERSALNUM;
					else if(!isRecharging(Skills.ROCK_THROW))
						skillNum = ROCKNUM;
				}
				else if (enemyCommonSkillRecharging() == Skills.SCISSORS_POKE)
				{
					if(!isRecharging(Skills.PAPER_CUT))
					    skillNum = PAPERNUM;
					else if(damageDifference > DAMAGE_DIFF_THRESHOLD && !isRecharging(Skills.REVERSAL_OF_FORTUNE))
						skillNum = REVERSALNUM;
					else if(!isRecharging(Skills.SCISSORS_POKE))
						 skillNum = SCISSORSNUM;
				}
				else if (enemyCommonSkillRecharging() == Skills.PAPER_CUT)
				{
					if(!isRecharging(Skills.ROCK_THROW))
					    skillNum = ROCKNUM;
					else if(damageDifference > DAMAGE_DIFF_THRESHOLD && !isRecharging(Skills.REVERSAL_OF_FORTUNE))
						skillNum = REVERSALNUM;
					else if(!isRecharging(Skills.PAPER_CUT))
						skillNum = PAPERNUM;
				}

			}
			
			
			//If reversal would do more than 3 damage, use it
			else if(!isRecharging(Skills.REVERSAL_OF_FORTUNE) && 
					damageDifference >= DAMAGE_DIFF_THRESHOLD) 
				skillNum = REVERSALNUM;
			
			else
		    	skillNum = getSkillNum();
			
			if(skillNum < 0)
				skillNum = getSkillNum();
			
			valid = !isRecharging(Skills.values()[skillNum - 1]); //sets valid true if skill is charged
			switch (skillNum) 
			{
			case 1:
				currentPetSkill = Skills.ROCK_THROW;
				skill = getRock();
				break;
			case 2:
				currentPetSkill = Skills.SCISSORS_POKE;
				skill = getScissors();
				break;
			case 3:
				currentPetSkill = Skills.PAPER_CUT;
				skill = getPaper();
				break;
			case 4:
				currentPetSkill = Skills.SHOOT_THE_MOON;
				if(!enemyIsIntelligence())  //if enemy type is intelligence, don't use shoot the moon
				{	
				    skill = getMoon();
				    predictSkill();
				}
				else 
					valid = false;
				break;
			case 5:
				if(damageDifference >= DAMAGE_DIFF_THRESHOLD)
				{
					currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
					skill = getReversal();
				}
				else
					valid = false;
				break;
			}
		}
		skill.useSkill();
		return currentPetSkill;
	}

	/**
	 * sets predicted skill
	 */
	private void predictSkill() 
	{
		Skills predictedSkill = null;
		int skillNum = 0;
		boolean isRecharged = false;
		while (!isRecharged)
		{
		 	skillNum = ((skillGenerator.nextInt() % Skills.values().length));
		 	if(skillNum < 0)
				skillNum += Skills.values().length;
			skillNum += 1;
			isRecharged = petInfoList.get(myVictimIndex).isRecharged(skillNum);
		}
		
		//if enemy is likely to use reversal of fortune, predict it
		if(damageDifference < PREDICT_REVERSAL_THRESH && !enemyReversalRecharging())
			skillNum = REVERSALNUM;
		switch (skillNum) 
		{
		case 1:
			predictedSkill = Skills.ROCK_THROW;
			break;
		case 2:
			predictedSkill = Skills.SCISSORS_POKE;
			break;
		case 3:
			predictedSkill = Skills.PAPER_CUT;
			break;
		case 4:
			predictedSkill = Skills.SHOOT_THE_MOON;
			break;
		case 5:
			predictedSkill = Skills.REVERSAL_OF_FORTUNE;
			break;
		}
		setCurrentSkillPrediction(predictedSkill);
	}
	
	/**
	 * This method checks whether the pet we are attacking is of type intelligence
	 * @return
	 */
	private boolean enemyIsIntelligence()
	{
		if(petInfoList.get(myVictimIndex).getPetType().equals(PetTypes.INTELLIGENCE))
		    return true;
		return false; 		
	}
	
	/**
	 * Checks whether using reversal of fortune would immediately put the pet we 
	 * are attacking "to sleep."
	 * @return
	 */
	private boolean reversalWillKill()
	{
		return petInfoList.get(myVictimIndex).getHp() < damageDifference;
	}
	
	/**
	 * Checks if enemy's shoot the moon and reversal of fortune are recharging
	 * @return
	 */
	private boolean moonAndReversalRecharging()
	{
		if(!petInfoList.get(myVictimIndex).isRecharged(MOONNUM) && !petInfoList.get(myVictimIndex).isRecharged(REVERSALNUM))
		    return true;
		return false;		
	}
	
	/**
	 * Checks if enemy's shoot the moon is recharging
	 * @return
	 */
	private boolean enemyReversalRecharging()
	{
		if(!petInfoList.get(myVictimIndex).isRecharged(REVERSALNUM))
		    return true;
		return false;		
	}
	
	/**
	 * Checks if smartAI's shot the moon is recharging
	 * @return
	 */
	private boolean myMoonRecharging()
	{
		if(isRecharging(Skills.SHOOT_THE_MOON))
			return true;
		return false;
	}
	
	/**
	 * If either the enemy's rock, paper, or scissors is recharging, the skill is returned
	 */
	private Skills enemyCommonSkillRecharging()
	{
		if(!petInfoList.get(myVictimIndex).isRecharged(ROCKNUM))
			return Skills.ROCK_THROW;
		if(!petInfoList.get(myVictimIndex).isRecharged(SCISSORSNUM))
			return Skills.SCISSORS_POKE;
		if(!petInfoList.get(myVictimIndex).isRecharged(PAPERNUM))
			return Skills.PAPER_CUT;
		return null;
	}

	/**
	 * returns a number 1 to Skills.values().length 
	 *                  1 to 5
	 * @return
	 */
	private int getSkillNum() 
	{
		int skillNum = ((skillGenerator.nextInt() % Skills.values().length));
		if(skillNum < 0)
			skillNum += Skills.values().length;
		//% can be negative
		skillNum += 1;
		//skillNum was 0 to 4, now it's 1 to 5
		
		return skillNum;
	}

	/**
	 * Does the logic for random skill selection
	 * including predicting a skill
	 */
	private Skills chooseRandomSkill()
	{
		Skills currentPetSkill = null;
		boolean valid = false;
		int skillNum = -1;
		while(!valid)
		{
			skillNum = getSkillNum();
			valid = !isRecharging(Skills.values()[skillNum - 1]);
		}
		switch (skillNum) 
		{
		case 1:
			currentPetSkill = Skills.ROCK_THROW;
			break;
		case 2:
			currentPetSkill = Skills.SCISSORS_POKE;
			break;
		case 3:
			currentPetSkill = Skills.PAPER_CUT;
			break;
		case 4:		
    		currentPetSkill = Skills.SHOOT_THE_MOON;
			predictSkill();	
			break;
		case 5:
			currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
			break;
		}
		return currentPetSkill;
	}
	
	/**
	 * Takes Events from the Referee calls the appropriate method
	 */
	@Override
	public void update(Observable arg0, Object event) 
	{
		if(event instanceof BaseEvent)
		{
			switch(((BaseEvent) event).getEventType())
			{
			case ATTACK:
				handleAttackEvent((AttackEvent)event);
				break;
			case FIGHT_START:
				handleFightStartEvent((FightStartEvent)event);
				break;
			case ROUND_START:
				handleRoundStartEvent((RoundStartEvent)event);
				break;
			}
		}
	}
	
	/**
	 * decrements recharge times
	 * @param RoundStartEvent Isn't used now, but might be in the future
	 */
	private void handleRoundStartEvent(RoundStartEvent RoundStartEvent)
	{
		for(int i = 0; i < petInfoList.size(); i++)
		{
			petInfoList.get(i).decrementRechargeTimes();
		}
	}

	/**
	 * Clear knowledge of previous fight
	 * Create a new list of ImportantPetInfo
	 * @param fightStartEvent
	 */
	private void handleFightStartEvent(FightStartEvent fightStartEvent) 
	{
		damageDifference = 0;
		if(petInfoList != null)
			petInfoList.clear();
		
		ArrayList<PlayerEventInfo> playerEventInfoList 
				= (ArrayList<PlayerEventInfo>) fightStartEvent.getPlayerEventInfo();
		
		for(int i = 0; i < playerEventInfoList.size(); i++)
		{
			PlayerEventInfo playerInfo = playerEventInfoList.get(i);
			if(getPetName().equals(playerInfo.getPetName()))
				this.myIndex = i;
			petInfoList.add(new ImportantPetInfo(playerInfo.getPetType(), playerInfo.getStartingHp()));
		}
	}

	/**
	 * takes the AttackEvent and uses it to update ImportantPetInfoList
	 * @param event
	 */
	private void handleAttackEvent(AttackEvent attackEvent) 
	{
		int attackingIndex = attackEvent.getAttackingPlayableIndex();
		int victimIndex = attackEvent.getVictimPlayableIndex();
		ImportantPetInfo attackingPetInfo = petInfoList.get(attackingIndex);
		ImportantPetInfo victimPetInfo = petInfoList.get(victimIndex);
		double randomDamage = attackEvent.getRandomDamage();
		
		attackingPetInfo.useSkill(attackEvent.getAttackingSkillChoice());
		victimPetInfo.updateHp(randomDamage + attackEvent.getConditionalDamage());
		
		if(attackingIndex == myIndex)
		{
			damageDifference -= randomDamage; 
			myVictimIndex = victimIndex;
		}
		if(victimIndex == myIndex)
		{
			damageDifference += randomDamage; 
		}
	}

	/**
	 * An inner class that represents Pets.  Only the information
	 * important to our algorithms is stored.
	 */
	public static class ImportantPetInfo
	{
		private PetTypes petType;
		private double hp;
		private double maxHp;
		private RockThrow rock = new RockThrow();
		private ScissorsPoke scissors = new ScissorsPoke();
		private PaperCut paper = new PaperCut();
		private ShootTheMoon moon = new ShootTheMoon();
		private ReversalOfFortune reversal = new ReversalOfFortune();
		
		public ImportantPetInfo(PetTypes petType, double maxHp)
		{
			this.petType = petType;
			this.hp = maxHp;
			this.maxHp = maxHp;
		}
		
		/**
		 * Takes a skills enum and uses the skill, updating the recharge times
		 * @param skill
		 */
		public void useSkill(Skills skill) 
		{
			switch (skill) 
			{
			case ROCK_THROW:
				rock.useSkill();
				break;
			case PAPER_CUT:
				paper.useSkill();
				break;
			case SCISSORS_POKE:
				scissors.useSkill();
				break;
			case SHOOT_THE_MOON:
				moon.useSkill();
				break;
			case REVERSAL_OF_FORTUNE:
				reversal.useSkill();
				break;
			}
		}

		public PetTypes getPetType() 
		{
			return petType;
		}

		public double getHp() 
		{
			return hp;
		}
		
		public double getMaxHp()
		{ 
			return maxHp;
		}

		/**
		 * updates this.hp to take damage
		 * @param hp
		 */
		public void updateHp(double hp) 
		{
			this.hp -= hp;
		}
				
		/**
		 * takes an int representing the skills enum and returns
		 * the recharge time associated with it
		 * @param skillNum
		 * @return
		 */
		public boolean isRecharged(int skillNum)
		{
			boolean isCharged = false;
			switch (skillNum) 
			{
			case 1:
				isCharged = rock.isCharged();
				break;
			case 2:
				isCharged = paper.isCharged();
				break;
			case 3:
				isCharged = scissors.isCharged();
				break;
			case 4:
				isCharged = moon.isCharged();
				break;
			case 5:
				isCharged = reversal.isCharged();
				break;
			}
			return isCharged;
		}
		
		/**
		 * takes a skills enum and returns
		 * the recharge time associated with it
		 * @param skillNum
		 * @return
		 */
		public boolean isRecharged(Skills skill)
		{
			boolean isCharged = false;
			switch (skill) 
			{
			case ROCK_THROW:
				isCharged = rock.isCharged();
				break;
			case PAPER_CUT:
				isCharged = paper.isCharged();
				break;
			case SCISSORS_POKE:
				isCharged = scissors.isCharged();
				break;
			case SHOOT_THE_MOON:
				isCharged = moon.isCharged();
				break;
			case REVERSAL_OF_FORTUNE:
				isCharged = reversal.isCharged();
				break;
			}
			return isCharged;
		}
		
		/**
		 * recharges each skill by 1
		 */
		public void decrementRechargeTimes() 
		{
			rock.recharge();
			scissors.recharge();
			paper.recharge();
			moon.recharge();
			reversal.recharge();
		}
	}
}
