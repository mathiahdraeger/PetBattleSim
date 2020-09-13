package UIBased.Presenter;

import Shared.Skills.Skills;
import UIBased.Model.Data.Playable;
import UIBased.Model.Data.PlayerTypes;
import UIBased.Model.Data.Referee;
import UIBased.View.BattleController;
import javafx.scene.Scene;

public class BattlePresenter 
{
	private BattleController view;
	private Scene scene;
	private int currentIndex;
	private Referee ref;
	private int fightNum;
	private int roundNum;
	private int numFights;
	private boolean predicting = false;
	
	private final String ROCK = "Rock Throw";
	private final String PAPER = "Paper Cut";
	private final String SCISSORS = "Scissors Poke";
	private final String MOON = "Shoot the Moon";
	private final String REVERSAL = "Reversal of Fortune";
	
	public BattlePresenter(Scene scene, Referee ref)
	{
		this.ref = ref;
		this.scene = scene;
	}
	
	/**
	 * Starts the next battle by reseting the fightNum and starting the first fight
	 */
	public void startBattle()
	{
		view.getUpdateWindow().clear();
		fightNum = 0;
		ref.startBattle();
		startFight();
	}
	
	/**
	 * Starts a new fight.  
	 * Should be called when the "Start" button is pressed after the battleView opens and 
	 * should be called when the "Start" button is pressed after a fight ends and a new 
	 * fight is about to start.
	 */
	public void startFight()
	{
		view.getBtn_StartFight().setVisible(false);
		numFights = ref.getNumFights();
		fightNum++;
		roundNum = 0;
		currentIndex = 0;
		ref.resetPlayables();
		ref.buildFightStartEvent(fightNum);
		view.getUpdateWindow().appendText("Fight Start\n");
		startRound();
	}
	
	/**
	 * Starts a new Round. 
	 * Should be called when a fight is started and
	 * should be called when a round is over, but there is no winner.
	 */
	private void startRound()
	{
		roundNum++;
		ref.buildRoundStartEvent(roundNum);
		view.getUpdateWindow().appendText("Current Round: " + roundNum + "\n");
		for(int i = 0; i < ref.getNumPlayables(); i++)
			view.getUpdateWindow().appendText(ref.printStats(i));
		nextPlayer();
	}

	/**
	 * Takes an AI's turn
	 * should be called if the first pet is an AI and
	 * should be called if the next pet is an AI after a Human or AI turn
	 */
	private void chooseSkillAI()
	{
		ref.chooseSkill(currentIndex, null);
		int newIndex = ref.getNextIndex(currentIndex);
		if(newIndex <= currentIndex)
			endRound();
		else
		{
			currentIndex = newIndex;
			nextPlayer();
		}
	}
	
	/**
	 * Does the appropriate setup and method calls for if the next player
	 * is Human or AI
	 */
	private void nextPlayer() 
	{
		Playable pet = ref.getPlayable(currentIndex);
		if(pet.getPlayerType() == PlayerTypes.COMPUTER)
			chooseSkillAI();
		else
		{
			rockRecharge(pet);
			paperRecharge(pet);
			scissorsRecharge(pet);
			moonRecharge(pet);
			reversalRecharge(pet);
			
			view.getLbl_PlayerName().setText(pet.getPlayerName() 
					+ " and " + pet.getPetName() + "'s turn.");	
		}
	}

	/**
	 * The following methods update the buttons to reflect the current
	 * pet's recharge times.
	 * @param pet
	 */
	private void rockRecharge(Playable pet) 
	{
		int time = pet.getSkillRechargeTime(Skills.ROCK_THROW);
		if(time != 0)
		{
			view.getBtn_Rock().setDisable(true);
			view.getBtn_Rock().setText(ROCK + "\nRecharged in\n" + time + " turns.");
		}
		else
		{
			view.getBtn_Rock().setDisable(false);
			view.getBtn_Rock().setText(ROCK);
		}
	}	
	
	private void paperRecharge(Playable pet) 
	{
		int time = pet.getSkillRechargeTime(Skills.PAPER_CUT);
		if(time != 0)
		{
			view.getBtn_Paper().setDisable(true);
			view.getBtn_Paper().setText(PAPER + "\nRecharged in\n" + time + " turns.");
		}
		else
		{
			view.getBtn_Paper().setDisable(false);
			view.getBtn_Paper().setText(PAPER);
		}
	}
	
	private void scissorsRecharge(Playable pet) 
	{
		int time = pet.getSkillRechargeTime(Skills.SCISSORS_POKE);
		if(time != 0)
		{
			view.getBtn_Scissors().setDisable(true);
			view.getBtn_Scissors().setText(SCISSORS + "\nRecharged in\n" + time + " turns.");
		}
		else
		{
			view.getBtn_Scissors().setDisable(false);
			view.getBtn_Scissors().setText(SCISSORS);
		}
	}
	
	private void moonRecharge(Playable pet) 
	{
		int time = pet.getSkillRechargeTime(Skills.SHOOT_THE_MOON);
		if(time != 0)
		{
			view.getBtn_Moon().setDisable(true);
			view.getBtn_Moon().setText(MOON + "\nRecharged in\n" + time + " turns.");
		}
		else
		{
			view.getBtn_Moon().setDisable(false);
			view.getBtn_Moon().setText(MOON);
		}
	}
	
	private void reversalRecharge(Playable pet) 
	{
		int time = pet.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE);
		if(time != 0)
		{
			view.getBtn_Reversal().setDisable(true);
			view.getBtn_Reversal().setText(REVERSAL + "\nRecharged in\n" + time + " turns.");
		}
		else
		{
			view.getBtn_Reversal().setDisable(false);
			view.getBtn_Reversal().setText(REVERSAL);
		}
	}

	/**
	 * Takes a Human turn
	 * Should be called from view when one of the buttons is pressed
	 * @param skill a Skills enum
	 */
	public void chooseSkillHuman(Skills skill)
	{
		if(predicting)
		{
			ref.setPredictedSkill(currentIndex, skill);
			startNextPlayerTurn();
		}
		else
		{
			ref.chooseSkill(currentIndex, skill);
			if(skill == Skills.SHOOT_THE_MOON)
			{
				predicting = true;
				enableButtons();
				view.getLbl_PlayerName().setText(
						view.getLbl_PlayerName().getText() + " Predict the skill " 
						+ ref.getNextPlayable(currentIndex).getPetName() + " will use.");
			}
			else
			{
				startNextPlayerTurn();		
			}
		}
	}
	
	/**
	 * Updates index and either calls the next player's turn or ends the round.
	 */
	private void startNextPlayerTurn()
	{
		predicting = false;
		int newIndex = ref.getNextIndex(currentIndex);
		if(newIndex <= currentIndex)
			endRound();
		else
		{
			currentIndex = newIndex;
			nextPlayer();
		}		
	}
	
	/**
	 * Once all skills have been chosen, damage is calculated
	 * and attacks are executed
	 */
	private void endRound()
	{
		for (int i = 0; i < ref.getNumPlayables(); i++) 
			ref.dealDamage(i);
		for (int i = 0; i < ref.getNumPlayables(); i++) 
			view.getUpdateWindow().appendText(ref.executeAttackEvent());
		ref.endRound();
		String winner = ref.checkFightWinner();
		if(winner != null)
		{
			view.getUpdateWindow().appendText("\n" + winner + " win the fight!\n\n");
			endFight();
		}
		else
		{
			currentIndex = ref.getNextIndex(-1);
			startRound();
		}
	}
	
	/**
	 * Checks if it is the last fight.
	 */
	private void endFight() 
	{
		if(fightNum == numFights)
			endBattle();
		else
			waitForStartFight();
	}

	/**
	 * Puts the View in a state to wait for the user to click the 
	 * "Start Fight" button.
	 */
	private void waitForStartFight() 
	{
		disableButtons();
		view.getLbl_PlayerName().setText("");
		view.getBtn_StartFight().setVisible(true);
	}

	/**
	 * Enables the skill buttons
	 */
	private void enableButtons()
	{
		view.getBtn_Rock().setText(ROCK);
		view.getBtn_Rock().setDisable(false);
		view.getBtn_Paper().setText(PAPER);
		view.getBtn_Paper().setDisable(false);
		view.getBtn_Scissors().setText(SCISSORS);
		view.getBtn_Scissors().setDisable(false);
		view.getBtn_Moon().setText(MOON);
		view.getBtn_Moon().setDisable(false);
		view.getBtn_Reversal().setText(REVERSAL);
		view.getBtn_Reversal().setDisable(false);
	}
	
	/**
	 * disables the skill buttons
	 */
	private void disableButtons() 
	{
		view.getBtn_Rock().setText(ROCK);
		view.getBtn_Rock().setDisable(true);
		view.getBtn_Paper().setText(PAPER);
		view.getBtn_Paper().setDisable(true);
		view.getBtn_Scissors().setText(SCISSORS);
		view.getBtn_Scissors().setDisable(true);
		view.getBtn_Moon().setText(MOON);
		view.getBtn_Moon().setDisable(true);
		view.getBtn_Reversal().setText(REVERSAL);
		view.getBtn_Reversal().setDisable(true);
	}

	/**
	 * Prints the winners
	 */
	private void endBattle() 
	{
		disableButtons();
		view.getUpdateWindow().appendText(ref.determineBattleWinner() + "WIN THE BATTLE!");
		view.setPlayAgainButtonVisible();
	}

	/**
	 * 
	 * @param battleController
	 */
	public void setView(BattleController battleController)
	{
		this.view = battleController;
	}
}
