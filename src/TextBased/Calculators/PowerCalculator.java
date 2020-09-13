package TextBased.Calculators;
import TextBased.Data.Referee;
import Shared.Skills.Skills;

/**
 * Calculator used for calculating damages inflicted by a pet of type power
 * @author kruge
 *
 */
public class PowerCalculator extends Calculator
{
	private final int POWER_MULTIPLIER = 5;
	private Skills victimSkill;

	public PowerCalculator(Referee ref)
	{
		super(ref);
	}
	
	@Override
	protected double getRockDamage() 
	{
		double conditionalDamage = 0;
		victimSkill = getRef().getSkill(getRef().getNextIndex(getIndex()));
		if(victimSkill == Skills.SCISSORS_POKE)
			conditionalDamage = getRandomDamage() * POWER_MULTIPLIER;
		return conditionalDamage;
	}

	@Override
	protected double getScissorsDamage() 
	{
		double conditionalDamage = 0;
		victimSkill = getRef().getSkill(getRef().getNextIndex(getIndex()));
		if(victimSkill == Skills.PAPER_CUT)
			conditionalDamage = getRandomDamage() * POWER_MULTIPLIER;
		return conditionalDamage;
	}

	@Override
	protected double getPaperDamage() 
	{
		double conditionalDamage = 0;
		victimSkill = getRef().getSkill(getRef().getNextIndex(getIndex()));
		if(victimSkill == Skills.ROCK_THROW)
			conditionalDamage = getRandomDamage() * POWER_MULTIPLIER;
		return conditionalDamage;
	}
}
