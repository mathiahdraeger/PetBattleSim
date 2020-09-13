package Shared.Skills;

/**
 * 
 * @author kruge
 *
 */
public class ScissorsPoke extends Skill
{
	private static final int RECHARGE_TIME = 1;

	public ScissorsPoke()
	{
		super(RECHARGE_TIME);
		setRechargeTime(0);
		setSkillName(Skills.SCISSORS_POKE.toString());
	}
}
