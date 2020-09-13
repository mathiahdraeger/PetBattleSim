package TextBased.Events;

import Shared.Skills.Skills;


public final class AttackEvent extends BaseEvent
{
	private static final int DEFAULT_INDEX = -1;
	private static final double DEFAULT_DOUBLE = 0;
	private static final Skills DEFAULT_SKILL = Skills.ROCK_THROW;
	
	private final int attackingPlayableIndex;
	private final int victimPlayableIndex;
	private final Skills attackingSkillChoice;
	private final Skills predictedSkillEnum;
	private final double randomDamage;
	private final double conditionalDamage;
	
	private AttackEvent(AttackEventBuilder attackEventBuilder)
	{
		super(EventTypes.ATTACK);
		
		if(attackEventBuilder.attackingPlayableIndex != null)
			this.attackingPlayableIndex = attackEventBuilder.attackingPlayableIndex;
		else
			this.attackingPlayableIndex = DEFAULT_INDEX;
		
		if(attackEventBuilder.victimPlayableIndex != null)
			this.victimPlayableIndex = attackEventBuilder.victimPlayableIndex;
		else
			this.victimPlayableIndex = DEFAULT_INDEX;
		
		if(attackEventBuilder.attackingSkillChoice != null)
			this.attackingSkillChoice = attackEventBuilder.attackingSkillChoice;
		else 
			this.attackingSkillChoice = DEFAULT_SKILL;
		
		if(attackEventBuilder.predictedSkillEnum != null)
			this.predictedSkillEnum = attackEventBuilder.predictedSkillEnum;
		else 
			this.predictedSkillEnum = DEFAULT_SKILL;
		
		if(attackEventBuilder.randomDamage != null)
			this.randomDamage = attackEventBuilder.randomDamage;
		else
			this.randomDamage = DEFAULT_DOUBLE;
		
		if(attackEventBuilder.conditionalDamage != null)
			this.conditionalDamage = attackEventBuilder.conditionalDamage;
		else
			this.conditionalDamage = DEFAULT_DOUBLE;
	}

	/**
	 * @return the attackingPlayableIndex
	 */
	public int getAttackingPlayableIndex() {
		return attackingPlayableIndex;
	}

	/**
	 * @return the victimPlayableIndex
	 */
	public int getVictimPlayableIndex() {
		return victimPlayableIndex;
	}

	/**
	 * @return the attackingSkillChoice
	 */
	public Skills getAttackingSkillChoice() {
		return attackingSkillChoice;
	}

	/**
	 * @return the predictedSkillEnum
	 */
	public Skills getPredictedSkillEnum() {
		return predictedSkillEnum;
	}

	/**
	 * @return the randomDamage
	 */
	public double getRandomDamage() {
		return randomDamage;
	}

	/**
	 * @return the conditionalDamage
	 */
	public double getConditionalDamage() {
		return conditionalDamage;
	}

	public static class AttackEventBuilder
	{
		private Integer attackingPlayableIndex = null;
		private Integer victimPlayableIndex = null;
		private Skills attackingSkillChoice = null;
		private Skills predictedSkillEnum = null;
		private Double randomDamage = null;
		private Double conditionalDamage = null;
		
		public AttackEventBuilder()
		{
			
		}
		
		public AttackEventBuilder withAttackingPlayableIndex(int attackingPlayableIndex)
		{
			this.attackingPlayableIndex = attackingPlayableIndex;
			return this;
		}

		public AttackEventBuilder withVictimPlayableIndex(int victimPlayableIndex)
		{
			this.victimPlayableIndex = victimPlayableIndex;
			return this;
		}

		public AttackEventBuilder withAttackingSkillChoice(Skills attackingSkillChoice)
		{
			this.attackingSkillChoice = attackingSkillChoice;
			return this;
		}
		
		public AttackEventBuilder withPredictedSkillEnum(Skills predictedSkillEnum)
		{
			this.predictedSkillEnum = predictedSkillEnum;
			return this;
		}

		public AttackEventBuilder withRandomDamage(double randomDamage)
		{
			this.randomDamage = randomDamage;
			return this;
		}
		
		public AttackEventBuilder withConditionalDamage(double conditionalDamage)
		{
			this.conditionalDamage = conditionalDamage;
			return this;
		}
		
		public AttackEvent build()
		{
			return new AttackEvent(this);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttackEvent other = (AttackEvent) obj;
		if (attackingPlayableIndex != other.attackingPlayableIndex)
			return false;
		if (attackingSkillChoice != other.attackingSkillChoice)
			return false;
		if (Double.doubleToLongBits(conditionalDamage) != Double.doubleToLongBits(other.conditionalDamage))
			return false;
		if (predictedSkillEnum != other.predictedSkillEnum)
			return false;
		if (Double.doubleToLongBits(randomDamage) != Double.doubleToLongBits(other.randomDamage))
			return false;
		if (victimPlayableIndex != other.victimPlayableIndex)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AttackEvent [attackingPlayableIndex=" + attackingPlayableIndex + ", victimPlayableIndex="
				+ victimPlayableIndex + ", attackingSkillChoice=" + attackingSkillChoice + ", predictedSkillEnum="
				+ predictedSkillEnum + ", randomDamage=" + randomDamage + ", conditionalDamage=" + conditionalDamage
				+ "]";
	}

	@Override
	public int hashcode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + attackingPlayableIndex;
		result = prime * result + ((attackingSkillChoice == null) ? 0 : attackingSkillChoice.hashCode());
		long temp;
		temp = Double.doubleToLongBits(conditionalDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((predictedSkillEnum == null) ? 0 : predictedSkillEnum.hashCode());
		temp = Double.doubleToLongBits(randomDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + victimPlayableIndex;
		return result;
	}	
}
