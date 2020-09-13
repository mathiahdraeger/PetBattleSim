package TextBased.Calculators;

import TextBased.Data.Referee;
import Shared.Skills.Skills;


/**
 * This class is the parent class of all other calculator classes.
 * It is used to calculate damage.
 * @author kruge
 *
 */
public abstract class Calculator implements calculatable
{
	private final int MAX_RANDOM_DAMAGE = 5;
	private final double SHOOT_THE_MOON_CONDITIONAL = 20;
	
	private Referee ref;
	private int index;
	private double randomDamage;
	
	public Calculator(Referee ref)
	{
		this.ref = ref;
	}
	
	/**
	 * This method will return the damage dealt
	 */
	public double calculateDamage(int index)
	{
		this.index = index;
		Skills playerSkill = ref.getSkill(index);
		calculateRandomDamage();
		double conditionalDamage = 0;
		
		if(playerSkill == Skills.ROCK_THROW)
			conditionalDamage = getRockDamage();
		else if(playerSkill == Skills.SCISSORS_POKE)
			conditionalDamage = getScissorsDamage();
		else if(playerSkill == Skills.PAPER_CUT)
			conditionalDamage = getPaperDamage();
		else if(playerSkill == Skills.SHOOT_THE_MOON)
			conditionalDamage = getShootTheMoon();
		else if(playerSkill == Skills.REVERSAL_OF_FORTUNE)
		{
			conditionalDamage = getReversalOfFortune();
			randomDamage += conditionalDamage;
		}		
		ref.buildAttackEvent(randomDamage, conditionalDamage, index);
		return randomDamage + conditionalDamage;
	}
	
	/**
	 * updates randomDamge with the new randomDamage
	 */
	private void calculateRandomDamage() 
	{	
		randomDamage = ref.getRNG().nextDouble() * MAX_RANDOM_DAMAGE;
	}

	/**
	 * returns the conditional damage dealt by Shoot the Moon
	 * @return 0 if the skill was predicted wrong, 20 if predicted correctly
	 */
	private double getShootTheMoon()
	{
		double conditionalDamage = 0;
		if(ref.getPlayable(index).getSkillPrediction() == ref.getSkill(ref.getNextIndex(index)))
			conditionalDamage = SHOOT_THE_MOON_CONDITIONAL;
		return conditionalDamage;
	}
	
	/**
	 * returns the randomDamageDifference of the current pet
	 * @return the conditional damage ReversalOfFortune will do
	 */
	private double getReversalOfFortune()
	{
		return ref.getRandomDamageDifference(index);
	}
	
	public double getRandomDamage()
	{
		return randomDamage;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public Referee getRef()
	{
		return ref;
	}
	
	/**
	 * returns conditional damage according to the requirements
	 */
	protected abstract double getRockDamage();
	protected abstract double getScissorsDamage();
	protected abstract double getPaperDamage();
}
