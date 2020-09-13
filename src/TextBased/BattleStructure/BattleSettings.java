package TextBased.BattleStructure;

import java.util.Scanner;

import TextBased.Data.AI;
import TextBased.Data.Human;
import TextBased.Data.Pet;
import TextBased.Data.PetTypes;
import TextBased.Data.PlayerTypes;
import TextBased.Data.Referee;
import TextBased.Data.SmartAI;

/**
 * This class sets all of the pre-battle settings for the game.
 */
public class BattleSettings 
{
    public static final Scanner in = new Scanner(System.in);
	
    private double battleHP;
    private int numPlayers;
	private int numFights;
	private Referee ref;

    public BattleSettings()
    {
		try 
		{
			ref = new Referee();
			inputSettings();		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
    }
 
    /**
     * Gets input from the user for the battle settings
     */
    public void inputSettings()
    {
    	inputBattleHP();
    	ref.setBattleHp(battleHP);
        inputSeed();
        inputNumFights();
    	inputNumPlayers();
    	inputPlayableList();
    }
    
	/** 
	 * inputHP asks the user for the amount of HP every pet will have
     * for the battle.  It checks if the input is valid and keeps asking
     * for input until it is valid.
     */
    private void inputBattleHP()
    {
    	System.out.println("Enter the amount of HP each pet will have for this battle.");
    	boolean valid = false;
    	while(!valid)
    	{
    		try
            {
                double battleHP = Double.parseDouble(in.nextLine());
                if(battleHP <= 0)
                    throw new Exception("Not postive");
                setBattleHP(battleHP);
                valid = true;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid number.");
            }
    	}
    }
    
    /**
     * This method prompts the user to enter a value
     * of type long for the seed of the random number
     * generator
     */
    public void inputSeed()
    {
    	boolean validSeed = false;
    	while(!validSeed)
    	{
    		try
        	{
    			System.out.println("Enter a seed number.");
    			long seed = Long.parseLong(in.nextLine());
    			if (seed >= 0)
    			{
    				try
    				{
    					ref.setSeed(seed);
    					validSeed = true;
    				}
    				catch(Exception e)
    				{
    					System.out.println(e + " Unable to set seed");
    				}
       			}
    			else
    				throw new Exception("Negative seeds are invalid. Enter valid seed.");   			
        	}
        	catch(Exception e)
        	{
        		System.out.println("Invalid seed " + e);
        	}
    	}
    }
    
    /** 
     * This method prompts the user to enter the number of fights that the battle
 	 * should consist of. If an invalid integer is entered, it handles the exception.  
     */
    private void inputNumFights() 
    {
    	boolean validInt = false;
    	while(!validInt)
    	{
    		try
    		{
    			System.out.println("Input an integer for the number of fights.");
    			int numFights = Integer.parseInt(in.nextLine());
    			if(numFights <= 0)
    				throw new Exception ("negative");
    			setNumFights(numFights);
    			validInt = true;
    		}
    		catch (Exception e)
    		{
    			System.out.println("Invalid integer");
    		}
    	}
    }
    
    /**
     * Prompts user to input the number of players for the battle
     * and checks that it is valid
     */
    public void inputNumPlayers()
    {
    	boolean validInt = false;
    	while(!validInt)
    	{
    		try
    		{
    			System.out.println("Input an integer for the number of players.");
    			int numPlayers = Integer.parseInt(in.nextLine());
    			if(numPlayers <= 1)
    				throw new Exception ("not enough");
    			setNumPlayers(numPlayers);
    			validInt = true;
    		}
    		catch (Exception e)
    		{
    			System.out.println("Invalid integer");
    		}
    	}
    }
    
	/**
	 * inputPlayers will loop over the all players and
	 * ask for all the information required to set up a player:
	 * Player name, pet name, pet type, (later, AI or human)
     */
    private void inputPlayableList()
    {
    	for(int i = 0; i < numPlayers; i++)
    	{
    		Pet pet = inputPet();    		
    		ref.addPlayable(pet);
    	}
    }
    
    /**
     * One iteration of the loop of asking user for input
     * regarding each player and that player's pet.
     */
    private Pet inputPet() 
    {
    	String playerName = inputPlayerName();
		System.out.println("Enter " + playerName + "'s pet's name.");
		String petName = in.nextLine();
		PetTypes petType = inputPetType(petName);
		PlayerTypes playerType = inputPlayerType(playerName);
		Pet pet = null;
		if(playerType == PlayerTypes.HUMAN)
		    pet = new Human(playerName, petName, petType, battleHP);
		else if(playerType == PlayerTypes.COMPUTER)
			pet = new AI(playerName, petName, petType, battleHP);
		else if(playerType == PlayerTypes.SMARTAI)
			pet = new SmartAI(playerName, petName, petType, battleHP);
		return pet;

	}
    
    /**
     * Prompts the user to enter a player name. If the name is being used by another player
     * already, the user is prompted to enter a different name
     * @return
     */
    private String inputPlayerName()
    {
		String playerName = "";
		System.out.println("Enter next Player's name.");
		playerName = in.nextLine();
		return playerName;
    }
    

    private PetTypes inputPetType(String petName)
    {
		PetTypes petType = null;
        boolean validType = false;
		while(!validType)
		{
			try
			{
				System.out.println("Enter " + petName 
						+ "'s type. 1 for Power, 2 for Speed, or 3 for Intelligence.");
				int type = Integer.parseInt(in.nextLine());
				if(type == 1)
					petType = PetTypes.POWER;
				else if(type == 2)
					petType = PetTypes.SPEED;
				else if(type == 3)
					petType = PetTypes.INTELLIGENCE;
				if(type <= 3 && type >= 1)
					validType = true;
				else
					throw new Exception("invalid type");
			}
			catch (Exception e)
			{
				System.out.println("Invalid type.");
			}
		}
		return petType;
    }
    
    /**
     * Gets input from user and sets the type of the player.
     * @param playerName used in the prompt
     * @return the chosen player type
     */
    private PlayerTypes inputPlayerType(String playerName)
    {
    	PlayerTypes playerType = null;
        boolean validType = false;
		while(!validType)
		{
			try
			{
				System.out.println("Enter " + playerName 
						+ "'s player type. 1 for Human, 2 for Computer, or 3 for Smart AI.");
				int type = Integer.parseInt(in.nextLine());
				playerType = PlayerTypes.values()[type - 1];
				if(0 <= type && type <= PlayerTypes.values().length)
					validType = true;
				else
					throw new Exception("invalid type");
			}
			catch (Exception e)
			{
				System.out.println("Invalid type.");
			}
		}
		return playerType;
    }

    public void setNumPlayers(int numPlayers)
    {
		this.numPlayers = numPlayers;
	} 
    
    public void setNumFights(int numFights) 
    {
		this.numFights = numFights;
	}
	
    private void setBattleHP(double battleHP)
    {
    	this.battleHP = battleHP;
	}
 
	public double getBattleHP() 
	{
		return battleHP;
	}

	public int getNumFights() 
	{
		return numFights;
	}

	public int getNumPlayers() 
	{
		return numPlayers;
	}

	public Referee getRef()
	{
		return ref;
	}
}

