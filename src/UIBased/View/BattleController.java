package UIBased.View;

import Shared.Skills.Skills;
import UIBased.Presenter.BattlePresenter;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

/**
 * This class initializes and handles the components of the Battle view
 */
public class BattleController 
{
	private BattlePresenter presenter;

	@FXML
	private Label lblPlayerTurn; //indicates which player's turn it is
	
	private FadeTransition fadeTransition; 
	
	@FXML
	private Button btnStartFight;
	
	@FXML
	private Button btnRock;
	
	@FXML
	private Button btnScissors;
	
	@FXML
	private Button btnPaper;
	
	@FXML
	private Button btnMoon;
	
	@FXML
	private Button btnReversal;
	
	@FXML
	private TextArea updateWindow; //Text area used to show round updates
	
	/**
	 * BattleController constructor
	 */
	public BattleController()
	{
		
	}
	
	/**
	 * Instantiate the button handler and the fade transition for the label indicating
	 * which player's turn it is
	 */
	@FXML
	public void initialize()
	{
		ButtonHandler buttonHandler = new ButtonHandler();
		btnStartFight.setOnAction(buttonHandler);
		btnRock.setOnAction(buttonHandler);
		btnScissors.setOnAction(buttonHandler);
		btnMoon.setOnAction(buttonHandler);
		btnReversal.setOnAction(buttonHandler);
		btnPaper.setOnAction(buttonHandler);
		
		fadeTransition = new FadeTransition(Duration.seconds(0.1), lblPlayerTurn);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(3);
	}


	/**
	 * @return lblPlayerName
	 */
	public Label getLbl_PlayerName() {
		return lblPlayerTurn;
	}

	/**
	 * 
	 * @return btnStartFight
	 */
	public Button getBtn_StartFight() {
		return btnStartFight;
	}

	/**
	 * 
	 * @param lbl_PlayerName
	 */
	public void setLbl_PlayerName(Label lbl_PlayerName) {
		this.lblPlayerTurn = lbl_PlayerName;
	}


	/**
	 * 
	 * @return btnRock
	 */
	public Button getBtn_Rock() {
		return btnRock;
	}


	/**
	 * 
	 * @param btn_Rock
	 */
	public void setBtn_Rock(Button btn_Rock) {
		this.btnRock = btn_Rock;
	}


	/**
	 * 
	 * @return btnScissors
	 */
	public Button getBtn_Scissors() {
		return btnScissors;
	}


	/**
	 * 
	 * @param btn_Scissors
	 */
	public void setBtn_Scissors(Button btn_Scissors) {
		this.btnScissors = btn_Scissors;
	}


	/**
	 * 
	 * @return btnPaper
	 */
	public Button getBtn_Paper() {
		return btnPaper;
	}


	/**
	 * 
	 * @param btn_Paper
	 */
	public void setBtn_Paper(Button btn_Paper) {
		this.btnPaper = btn_Paper;
	}


	/**
	 * 
	 * @return btnMoon
	 */
	public Button getBtn_Moon() {
		return btnMoon;
	}


	/**
	 * 
	 * @param btn_Moon
	 */
	public void setBtn_Moon(Button btn_Moon) {
		this.btnMoon = btn_Moon;
	}


	/**
	 * 
	 * @return
	 */
	public Button getBtn_Reversal() {
		return btnReversal;
	}


	/**
	 * 
	 * @param btn_Reversal
	 */
	public void setBtn_Reversal(Button btn_Reversal) {
		this.btnReversal = btn_Reversal;
	}


	/**
	 * 
	 * @return updateWindow
	 */
	public TextArea getUpdateWindow() {
		return updateWindow;
	}


	/**
	 * 
	 * @param updateWindow
	 */
	public void setUpdateWindow(TextArea updateWindow) {
		this.updateWindow = updateWindow;
	}
	
	/**
	 * Makes the play again button visible after the battle is over
	 */
	public void setPlayAgainButtonVisible()
	{
		btnStartFight.setText("Play again?");
		btnStartFight.setVisible(true);
		btnStartFight.setDisable(false);
	}
	
	/**
	 * 
	 * @param battlePresenter
	 */
	public void setPresenter(BattlePresenter battlePresenter)
	{
		this.presenter = battlePresenter;
	}
	
	/**
	 * Handles the buttons in the view
	 *
	 */
	private class ButtonHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e) 
		{
			fadeTransition.play(); //Make playerTurn label flash
				
			if (e.getTarget() == btnStartFight)
			{
				if(btnStartFight.getText().equals("Play again?"))
				{
					btnStartFight.setText("Start Fight");
					presenter.startBattle();
				}
				else
				{
					try {
						presenter.startFight();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
			if (e.getTarget() == btnRock)
			{
				try 
				{			
					java.awt.Toolkit.getDefaultToolkit().beep();
					presenter.chooseSkillHuman(Skills.ROCK_THROW);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
			if (e.getTarget() == btnScissors)
			{
				try 
				{
					java.awt.Toolkit.getDefaultToolkit().beep();
					presenter.chooseSkillHuman(Skills.SCISSORS_POKE);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
			if (e.getTarget() == btnPaper)
			{
				try 
				{
					java.awt.Toolkit.getDefaultToolkit().beep();
					presenter.chooseSkillHuman(Skills.PAPER_CUT);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
			if (e.getTarget() == btnMoon)
			{
				try 
				{
					java.awt.Toolkit.getDefaultToolkit().beep();
					presenter.chooseSkillHuman(Skills.SHOOT_THE_MOON);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
			if (e.getTarget() == btnReversal)
			{
				try 
				{
					java.awt.Toolkit.getDefaultToolkit().beep();
					presenter.chooseSkillHuman(Skills.REVERSAL_OF_FORTUNE);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
			}
			
		}
	}
}
