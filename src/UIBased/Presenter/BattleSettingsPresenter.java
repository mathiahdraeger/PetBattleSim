package UIBased.Presenter;

import java.io.IOException;

import UIBased.Model.Data.*;
import UIBased.View.BattleSettingsController;
import UIBased.View.BattleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Handles the logic for inputing the battle settings
 */
public class BattleSettingsPresenter 
{
	private BattleSettingsController settings; 
	private Scene scene;
    private int numPlayers;
    private int currentNumPlayers;
    private Double battleHp;
	private Referee ref;

	/**
	 * BattleSettingsPresenter constructor
	 * @param scene
	 * @param ref
	 */
    public BattleSettingsPresenter(Scene scene, Referee ref)
    {
		try 
		{
			this.ref = ref;
			currentNumPlayers = 0;
			this.scene = scene;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
    }
	
	/**
	 * Gets input from the user for the battle settings
	 * @throws IOException 
	 */
    public void inputSettings()
    { 
    	inputBattleHP();
        inputSeed();
        inputNumFights();
    	inputNumPlayers();
    }
    
    
	/** 
	 * Gets the inputed HP from the BattleSettingsController class
     */
    private void inputBattleHP()
    {
    	this.battleHp = settings.getBattleHp();
    	ref.setBattleHp(battleHp);
    }
    
    /**
     * Gets the inputed seed from the BattleSettingsController class
     */
    public void inputSeed()
    {
    	ref.setSeed(settings.getSeed());
    }
    
    /** 
     *  Gets the inputed number of fights from the BattleSettingsController class
     */
    private void inputNumFights() 
    {
    	ref.setNumFights(settings.getNumFightsSpinner().getValue().intValue());
    }
    
    /**
     * Gets the inputed number of players from the BattleSettingsController class
     */
    public void inputNumPlayers()
    {
    	numPlayers = settings.getNumPlayersSpinner().getValue().intValue();
    }
    
	/**
	 * inputPlayers will loop over the all players and
	 * ask for all the information required to set up a player:
	 * Player name, pet name, pet type, (later, AI or human)
     */
    public void inputPlayableList() throws IOException
    {
    	if(currentNumPlayers < numPlayers)
    	{
    	    Pet pet = inputPet();    		
    	    ref.addPlayable(pet);
    	    currentNumPlayers++;
    	}
    	if(currentNumPlayers == (numPlayers))
    	// last player added, change scene
    	{
    		showBattleScene();		
    	}
    }
    
    /**
     * Loads the Battle scene and establishes its controller and presenter
     */
    private void showBattleScene()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UIBased/View/BattleScene.fxml"));	
		BorderPane root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		scene.setRoot(root);

		BattlePresenter battlePresenter = new BattlePresenter(scene, ref);
		BattleController battleController = fxmlLoader.getController();
		
		battleController.setPresenter(battlePresenter);
		battlePresenter.setView(battleController);
    }
    
    /**
     * One iteration of the loop of asking user for input
     * regarding each player and that player's pet.
     */
    private Pet inputPet() 
    {
    	String playerName = settings.getTxt_PlayerName().getText();
    	if(playerName == null || playerName.equals("") || playerName.equals(" "))
    	{
    		playerName = "Player " + settings.getPlayerNum();
    	}
    	String petName = settings.getTxt_PetName().getText();
    	if(petName == null || petName.equals("") || petName.equals(" "))
    	{
    		petName = "Pet " + settings.getPlayerNum();
    	}
    	PetTypes petType;
    	if(settings.getBtn_Power().isSelected())
    		petType = PetTypes.POWER;
    	else if(settings.getBtn_Speed().isSelected())
    		petType = PetTypes.SPEED;
    	else
    		petType = PetTypes.INTELLIGENCE;
    	Pet pet = null;
		if(settings.getBtn_Human().isSelected())
			pet = new Human(playerName, petName, petType, battleHp);
		else
			pet = new AI(playerName, petName, petType, battleHp);
		return pet;
	}
	
    public int getCurrentNumPlayers()
    {
    	return this.currentNumPlayers;
    }
    
    public void setNumPlayers(int numPlayers)
    {
		this.numPlayers = numPlayers;
	} 
 
	public double getBattleHp() 
	{
		return battleHp;
	}

	public int getNumPlayers() 
	{
		return numPlayers;
	}

	public Referee getRef()
	{
		return ref;
	}

	public void setView(BattleSettingsController settingsController)
	{
		settings = settingsController;
	}	
}