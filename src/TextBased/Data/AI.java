package TextBased.Data;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import Shared.Skills.Skill;
import Shared.Skills.Skills;

@SuppressWarnings("deprecation")
public class AI extends Pet implements Observer
{
	private Random skillGenerator; // new random generator for AI
	private final long SEED = 8675309; //hard-coded seed for AI random skillGenerator

	public AI(String playerName, String petName, PetTypes type, double battleHp) 
	{
		super(playerName, petName, type, battleHp);
		this.skillGenerator = new Random(SEED);
		setPlayerType(PlayerTypes.COMPUTER);
	}
	
	/**
	 * gets input from the AI and returns a valid skill
	 */
	public Skills chooseSkill() 
	{
		Skills currentPetSkill = null;
		Skill skill = null;
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
			skill = getMoon();
			int predictedSkillNum = getSkillNum();
			setCurrentSkillPrediction(Skills.values()[predictedSkillNum - 1]);
			break;
		case 5:
			currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
			skill = getReversal();
			break;
		}
		skill.useSkill();
		return currentPetSkill;
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
	 * In the future, this method will get any data from the
	 * Referee that the AI needs to make a decision.  
	 * For iteration3, the AI is purely random.
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//if(arg0 == val)
			//System.out.println("A.I update method");	//arg0.notifyObservers(arg1);
	}
}
