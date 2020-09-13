package TextBased.Data;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import TextBased.BattleStructure.BattleSettings;
import javafx.beans.value.ObservableValue;
import Shared.Skills.Skill;
import Shared.Skills.Skills;

@SuppressWarnings("deprecation")
public class Human extends Pet implements Playable
{

	private final Scanner in = BattleSettings.in;

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
		Skills currentPetSkill = null;
		Skill skill = null;
		int skillNum = -1;
		boolean valid = false;
		while (!valid) {
			System.out.println(getPlayerName() + ", choose a skill: "
					+ "1 for Rock Throw, 2 for Scissors Poke, "
					+ "3 for Paper Cut, 4 for Shoot the Moon, "
					+ "5 for Reversal of Fortune.");
			try {
				skillNum = Integer.parseInt(in.nextLine());
			} catch (Exception e) {
				valid = false;
			}
			switch (skillNum) {
			case 1:
				currentPetSkill = Skills.ROCK_THROW;
				valid = !isRecharging(Skills.ROCK_THROW);
				skill = getRock();
				break;
			case 2:
				currentPetSkill = Skills.SCISSORS_POKE;
				valid = !isRecharging(Skills.SCISSORS_POKE);
				skill = getScissors();
				break;
			case 3:
				currentPetSkill = Skills.PAPER_CUT;
				valid = !isRecharging(Skills.PAPER_CUT); 
				skill = getPaper();
				break;
			case 4:
				currentPetSkill = Skills.SHOOT_THE_MOON;
				valid = !isRecharging(Skills.SHOOT_THE_MOON);
				skill = getMoon();
				if(valid)
					predictSkill();
				break;
			case 5:
				currentPetSkill = Skills.REVERSAL_OF_FORTUNE;
				valid = !isRecharging(Skills.REVERSAL_OF_FORTUNE);
				skill = getReversal();
				break;
			default:
				valid = false;
				System.out.println("Invalid skill.");
			}
		}
		skill.useSkill();
		return currentPetSkill;
	}

	/**
	 * Gets user input for Shoot The Moon predictions
	 */
	private void predictSkill() 
	{
		Skills predictedSkill = null;
		int skillNum = -1;
		boolean valid = false;
		while (!valid) {
			System.out.println(getPlayerName() + ", predict your victim's skill: "
					+ "1 for Rock Throw, 2 for Scissors Poke, "
					+ "3 for Paper Cut, 4 for Shoot the Moon, "
					+ "5 for Reversal of Fortune.");
			try {
				skillNum = Integer.parseInt(in.nextLine());
			} catch (Exception e) {
				valid = false;
			}
			switch (skillNum) {
			case 1:
				predictedSkill = Skills.ROCK_THROW;
				valid = true;
				break;
			case 2:
				predictedSkill = Skills.SCISSORS_POKE;
				valid = true;
				break;
			case 3:
				predictedSkill = Skills.PAPER_CUT;
				valid = true;
				break;
			case 4:
				predictedSkill = Skills.SHOOT_THE_MOON;
				valid = true;
				break;
			case 5:
				predictedSkill = Skills.REVERSAL_OF_FORTUNE;
				valid = true;
				break;
			default:
				valid = false;
				System.out.println("Invalid skill.");
			}
		}
		setCurrentSkillPrediction(predictedSkill);
	}

	/**
	 * This method won't do anything in the human version of Pet. It is required by
	 * Observable.
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//if(arg0 == val)
			//System.out.println("Human update method");	//arg0.notifyObservers(arg1);
		
	}
}

	



