package TextBased.Calculators;
import TextBased.Data.Playable;
import TextBased.Data.Referee;
import Shared.Skills.Skills;

/**
 * Calculator used for calculating damages inflicted by a pet of type Intelligence
 * @author kruge
 *
 */
public class IntelligenceCalculator extends Calculator
{
	private final int INTELLIGENCE_SMALL_CONDITIONAL = 2;
	private final int INTELLIGENCE_BIG_CONDITIONAL = 3;
	
	public IntelligenceCalculator(Referee ref)
	{
		super(ref);	}
	
	@Override
	protected double getRockDamage()
	{
		double conditionalDamage = 0;
		Playable victim = getRef().getNextPlayable(getIndex());
		if(victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
			conditionalDamage += INTELLIGENCE_BIG_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		return conditionalDamage;
	}
	
	@Override
	protected double getScissorsDamage()
	{
		double conditionalDamage = 0;
		Playable victim = getRef().getNextPlayable(getIndex());
		if(victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
			conditionalDamage += INTELLIGENCE_BIG_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		return conditionalDamage;
	}
	
	@Override
	protected double getPaperDamage()
	{
		double conditionalDamage = 0;
		Playable victim = getRef().getNextPlayable(getIndex());
		if(victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
			conditionalDamage += INTELLIGENCE_BIG_CONDITIONAL;
		if(victim.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
			conditionalDamage += INTELLIGENCE_SMALL_CONDITIONAL;
		return conditionalDamage;
	}
}
