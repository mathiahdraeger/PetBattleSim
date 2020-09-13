package TextBased.Calculators;
/**
 * This interface has a single method calculateDamage, 
 * which will return a double value for the damage.
 * This is for classes to implement, which will perform the damage calculations.
 * @author kruge
 *
 */
public interface calculatable 
{
	public double calculateDamage(int index);
}
