package UIBased.Model.Data;

import java.util.Observable;

import Shared.Skills.Skill;
import Shared.Skills.Skills;



@SuppressWarnings("deprecation")
public class Human extends Pet implements Playable
{


	private Skills currentSkillPrediction = null;
	private Skills currentPetSkill = null;

	public Human(String playerName, String petName, PetTypes type, double battleHp) {
		super(playerName, petName, type, battleHp);
		setPlayerType(PlayerTypes.HUMAN);
	}

	/**
	 * loops through asking for an input. Checks to see if the input is valid
	 * for a skill, and returns the skill enum
	 */
	@Override
	public Skills chooseSkill() 
	{
		Skill skill = null;
		switch (this.currentPetSkill) 
		{
		case ROCK_THROW:
			skill = getRock();
			break;
		case SCISSORS_POKE:
			skill = getScissors();
			break;
		case PAPER_CUT:
			skill = getPaper();
			break;
		case SHOOT_THE_MOON:
			skill = getMoon();
			break;
		case REVERSAL_OF_FORTUNE:
			skill = getReversal();
			break;
		}
		skill.useSkill();
		return currentPetSkill;
	}
	
	public void setCurrentSkill(Skills skill)
	{
		this.currentPetSkill = skill;
	}
	
	public Skills getPredictedSkill()
	{
		return this.currentSkillPrediction;
	}

	/**
	 * This method won't do anything in the human version of Pet. It is required by
	 * Observable.
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		
	}
}

	



