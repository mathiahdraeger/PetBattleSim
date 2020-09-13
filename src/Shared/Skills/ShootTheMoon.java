package Shared.Skills;
/**
 * Skill instance class for shoot the moon
 * @author kruge
 *
 */
public class ShootTheMoon extends Skill
{
	private static final int RECHARGE_TIME = 6;

	public ShootTheMoon()
	{
		super(RECHARGE_TIME);
		setRechargeTime(0);
		setSkillName(Skills.SHOOT_THE_MOON.toString());
	}
}
