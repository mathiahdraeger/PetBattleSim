package UIBased.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import UIBased.Presenter.BattleSettingsPresenter;

/**
 * This class initializes and handles the components of the Battle Settings view
 */
public class BattleSettingsController extends BorderPane
{
	private BattleSettingsPresenter settings;
	private int playerNum = 1;
	
	@FXML
	private BorderPane mainPane;
    @FXML
    private GridPane settingsPane;
    
	@FXML
	private Label lblPlayerNum;
    
    @FXML
    private Button btnEnterSettings;

    @FXML
    private Label lblEnterSeed;

    @FXML
    private Label lblEnterPlayerNumber;

    @FXML
    private Label lblEnterFightNumber;

    @FXML
    private Label lblEnterHp;

    @FXML
    private Spinner<Double> battleHpSpinner;

    @FXML
    private Spinner<Double> seedSpinner;

    @FXML
    private Spinner<Double> numPlayersSpinner;

    @FXML
    private Spinner<Double> numFightsSpinner;
    
    @FXML
    private Label lblEnterPlayerName;

    @FXML
    private Label lblEnterPetName;

    @FXML
    private RadioButton btnPower;

    @FXML
    private RadioButton btnSpeed;

    @FXML
    private RadioButton btnIntelligence;

    @FXML
    private RadioButton btnHuman;

    @FXML
    private RadioButton btnComputer;

    @FXML
    private TextField txtPlayerName;

    @FXML
    private TextField txtPetName;

    @FXML
    private Button btnEnterInfo;

    @FXML
    private Label lblPlayerNumber;
	
    /**
     * BattleSettingsController constructor
     */
	public BattleSettingsController()
	{
		
	}
	
	/**
	 * Instantiate the button handler and sets the player info components initially
	 * invisible
	 */
	@FXML
	public void initialize()
	{
		ButtonHandler buttonHandler = new ButtonHandler();
		btnEnterSettings.setOnAction(buttonHandler);
		btnEnterInfo.setOnAction(buttonHandler);
		txtPlayerName.setVisible(false);
		txtPetName.setVisible(false);
		btnPower.setVisible(false);
		btnSpeed.setVisible(false);
		btnIntelligence.setVisible(false);
		btnHuman.setVisible(false);
		btnComputer.setVisible(false);
		btnEnterInfo.setVisible(false);
		lblEnterPlayerName.setVisible(false);
		lblEnterPetName.setVisible(false);
		lblPlayerNum.setVisible(false);
		btnPower.setSelected(true);
		btnHuman.setSelected(true);
	}
	
	
	/**
	 * @return battleHpSpinner
	 */
	public Spinner<Double> getBattleHpSpinner() {
		return battleHpSpinner;
	}
	
	/**
	 * 
	 * @return battleHpSpinner value
	 */
	public Double getBattleHp() {
		return battleHpSpinner.getValue();	
	}

	/**
	 * @return seedSpinner
	 */
	public Spinner<Double> getSeedSpinner() {
		return seedSpinner;
	}
	
	/**
	 * @return seed
	 */
	public Long getSeed() {
		
		return seedSpinner.getValue().longValue();
	}

	/**
	 * @return numPlayersSpinner
	 */
	public Spinner<Double> getNumPlayersSpinner() {
		return numPlayersSpinner;
	}

	/**
	 * @return numFightsSpinner
	 */
	public Spinner<Double> getNumFightsSpinner() {
		return numFightsSpinner;
	}

	/**
	 * @return btnEnterSettings
	 */
	public Button getBtn_EnterSettings() {
		return btnEnterSettings;
	}

	/**
	 * @param btn_EnterSettings
	 */
	public void setBtn_EnterSettings(Button btn_EnterSettings) {
		this.btnEnterSettings = btn_EnterSettings;
	}
	
	/**
	 * @return txtPlayerName
	 */
	public TextField getTxt_PlayerName() {
		return txtPlayerName;
	}

	/**
	 * @param txt_EnterPlayerName
	 */
	public void setTxt_EnterPlayerName(TextField txt_EnterPlayerName) {
		this.txtPlayerName = txt_EnterPlayerName;
	}

	/**
	 * @return txtPetName
	 */
	public TextField getTxt_PetName() {
		return txtPetName;
	}

	/**
	 * @param txt_PetName
	 */
	public void setTxt_PetName(TextField txt_PetName) {
		this.txtPetName = txt_PetName;
	}

	/**
	 * @return btnHuman
	 */
	public RadioButton getBtn_Human() {
		return btnHuman;
	}

	/**
	 * @param btn_Human
	 */
	public void setBtn_Human(RadioButton btn_Human) {
		this.btnHuman = btn_Human;
	}

	/**
	 * @return btnComputer
	 */
	public RadioButton getBtn_Computer() {
		return btnComputer;
	}

	/**
	 * @param btn_Computer
	 */
	public void setBtn_Computer(RadioButton btn_Computer) {
		this.btnComputer = btn_Computer;
	}

	/**
	 * @return btnPower
	 */
	public RadioButton getBtn_Power() {
		return btnPower;
	}

	/**
	 * @param btn_Power
	 */
	public void setBtn_Power(RadioButton btn_Power) {
		this.btnPower = btn_Power;
	}

	/**
	 * @return btnSpeed
	 */
	public RadioButton getBtn_Speed() {
		return btnSpeed;
	}

	/**
	 * @param btn_Speed
	 */
	public void setBtn_Speed(RadioButton btn_Speed) {
		this.btnSpeed = btn_Speed;
	}

	/**
	 * @return btnIntelligence
	 */
	public RadioButton getBtn_Intelligence() {
		return btnIntelligence;
	}

	/**
	 * @param btn_Intelligence
	 */
	public void setBtn_Intelligence(RadioButton btn_Intelligence) {
		this.btnIntelligence = btn_Intelligence;
	}
	
	/**
	 * @return btnEnterInfo
	 */
	public Button getBtn_EnterInfo() {
		return btnEnterInfo;
	}
	
	/**
	 * @param lbl_PlayerNumber
	 */
	public void setLbl_PlayerNumber(Label lbl_PlayerNumber) {
		this.lblPlayerNumber = lbl_PlayerNumber;
	}
	
	/**
	 * @return playerNum
	 */
	public int getPlayerNum()
	{
		return this.playerNum;
	}
	
	/**
	 * @param playerNum
	 */
	public void setPlayerNum(int playerNum)
	{
		this.playerNum = playerNum;
	}
	
	/**
	 * Sets the presenter class for Battle settings view
	 * @param battleSettingsUI
	 */
	public void setPresenter(BattleSettingsPresenter battleSettingsPresenter)
	{
		settings = battleSettingsPresenter;
		
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
			if (e.getTarget() == btnEnterSettings)
			{
				try 
				{
					settings.inputSettings();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				txtPlayerName.setVisible(true);
				txtPetName.setVisible(true);
				btnPower.setVisible(true);
				btnSpeed.setVisible(true);
				btnIntelligence.setVisible(true);
				btnHuman.setVisible(true);
				btnComputer.setVisible(true);
				btnEnterInfo.setVisible(true);
				lblEnterPlayerName.setVisible(true);
				lblEnterPetName.setVisible(true);
				lblPlayerNum.setVisible(true);
				lblPlayerNum.setText("Player " + playerNum);
				txtPlayerName.setPromptText("Player " + playerNum);
				txtPetName.setPromptText("Pet " + playerNum);
				btnEnterSettings.setDisable(true);
				numFightsSpinner.setDisable(true);
				numPlayersSpinner.setDisable(true);
				seedSpinner.setDisable(true);
				battleHpSpinner.setDisable(true);
			}
			
			if (e.getTarget() == btnEnterInfo)
			{
				try 
				{
					settings.inputPlayableList();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				playerNum++;
				lblPlayerNum.setText("Player " + playerNum);
				setPlayerNum(playerNum);
				txtPlayerName.setText(null);
				txtPetName.setText(null);
				txtPlayerName.setPromptText("Player " + playerNum);
				txtPetName.setPromptText("Pet " + playerNum);
				btnPower.setSelected(true);
				btnHuman.setSelected(true);
			}
		}
	}

	
}
