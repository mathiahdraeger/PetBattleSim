package Shared.Main;
import TextBased.BattleStructure.Battle;
import TextBased.BattleStructure.BattleSettings;
import UIBased.View.MainFrame;

/**
 * Runs the main for the game. Calls BattleSettings and Battle classes and declares the names
 * of the winning pet and player.
 */
public class MainGame
{
    public static void main(String[] args) throws Exception 
	{
    	if(args.length < 1)//Text
    	{
    		BattleSettings settings = new BattleSettings();
    	    Battle currentBattle = new Battle(settings);
    	    currentBattle.battle();
        	String winners = currentBattle.crownWinner();
        	
        	System.out.println(winners + "WIN!");
    	}
    	else //UI
    		MainFrame.main(args);
    }  
}
