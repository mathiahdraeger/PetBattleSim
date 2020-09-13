package TextBased.Data;
import java.util.Observable;
import java.util.Observer;

import Shared.Skills.Skills;
 


@SuppressWarnings("deprecation")
public interface Playable extends Observer
{
	public Skills chooseSkill();// Returns the skill chosen on the current turn
	public String getPlayerName(); // Returns the player name associated with this playable
	public String getPetName(); // Returns the pet name associated with this playable
	public PlayerTypes getPlayerType(); // Returns the player type associated with this playable.
	public PetTypes getPetType(); // Returns the pet type associated with this playable object
	public double getCurrentHp(); // Returns the current hp
	public void updateHp(double hp); // Subtracts hp from the current hp
	public void resetHp(); // Resets the hp back to the starting hp
	public void setCurrentHp(double currentHp); // Sets the hp
	public boolean isAwake(); // Returns true if the pet is awake
	public Skills getSkillPrediction(); // Returns the enumeration for the skill prediction.
	public int getSkillRechargeTime(Skills skill); // Returns the current recharge time for the specified skill
	public double calculateHpPercent(); // Returns the current percent of hp the playable has
	public void reset(); // Resets the playable’s data to start another fight
	public void decrementRechargeTimes();
	public void setRechargeTime(Skills skill, int rechargeTime);
	public void update(Observable o, Object arg);

}
