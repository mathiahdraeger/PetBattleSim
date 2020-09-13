package TextBased.Calculators;
import TextBased.Data.Referee;
import Shared.Skills.Skills;

/**
 * Calculator used for calculating damages inflicted by a pet of type Speed
 * @author kruge
 *
 */
public class SpeedCalculator extends Calculator
{
	private final double SPEED_UPPER_THRESHOLD = 0.75;
	private final double SPEED_LOWER_THRESHOLD = 0.25;	
	private final int SPEED_CONDITIONAL = 10;
	private double victimHpPercent;
	private Skills victimSkill;

	public SpeedCalculator(Referee ref)
	{
		super(ref);
	}

	@Override
	protected double getRockDamage() 
	{
		victimHpPercent = getRef().getPlayable(getIndex()).calculateHpPercent();
		victimSkill = getRef().getSkill(getIndex());
		double conditionalDamage = 0;
		if (victimHpPercent >= SPEED_UPPER_THRESHOLD)
		{
			if (victimSkill == Skills.SCISSORS_POKE || victimSkill == Skills.PAPER_CUT)
				conditionalDamage = SPEED_CONDITIONAL;
		}
		return conditionalDamage;
	}

	@Override
	protected double getScissorsDamage() 
	{
		victimHpPercent = getRef().getPlayable(getIndex()).calculateHpPercent();
		victimSkill = getRef().getSkill(getIndex());
		double conditionalDamage = 0;
		if (victimHpPercent >= SPEED_LOWER_THRESHOLD && victimHpPercent < SPEED_UPPER_THRESHOLD)
		{
			if (victimSkill == Skills.ROCK_THROW || victimSkill == Skills.PAPER_CUT)
				conditionalDamage = SPEED_CONDITIONAL;
		}
		return conditionalDamage;
	}

	@Override
	protected double getPaperDamage() 
	{
		victimHpPercent = getRef().getPlayable(getIndex()).calculateHpPercent();
		victimSkill = getRef().getSkill(getIndex());
		double conditionalDamage = 0;
		if (victimHpPercent < SPEED_LOWER_THRESHOLD) 
		{
			if (victimSkill == Skills.SCISSORS_POKE || victimSkill == Skills.ROCK_THROW)
				conditionalDamage = SPEED_CONDITIONAL;
		}
		return conditionalDamage;
	}
}

