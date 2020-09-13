package TextBased.Events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import TextBased.Data.PetTypes;
import TextBased.Data.PlayerTypes;
import Shared.Skills.Skills;

public class PlayerEventInfo 
{
	private static final PetTypes DEFAULT_PET_TYPE = PetTypes.POWER;
	private static final String DEFAULT_PET_NAME = "Greg";
	private static final PlayerTypes DEFAULT_PLAYER_TYPE = PlayerTypes.COMPUTER;
	private static final double DEFAULT_DOUBLE = 0;
	private static final Set<Skills> DEFAULT_SKILL_SET 
			= new HashSet<>(Arrays.asList(Skills.ROCK_THROW, 
			Skills.SCISSORS_POKE, Skills.PAPER_CUT, 
			Skills.SHOOT_THE_MOON, Skills.REVERSAL_OF_FORTUNE));
			
	private final PetTypes petType;
	private final PlayerTypes playerType;
	private final Set<Skills> skillSet;
	private final double startingHp;
	private final String petName;
	
	private PlayerEventInfo(PlayerEventInfoBuilder builder)
	{
		if(builder.petType != null)
			this.petType = builder.petType;
		else
			this.petType = DEFAULT_PET_TYPE;
		
		if(builder.playerType != null)
			this.playerType = builder.playerType;
		else
			this.playerType = DEFAULT_PLAYER_TYPE;
			
		if(builder.skillSet != null)
			this.skillSet = builder.skillSet;
		else 
			this.skillSet = DEFAULT_SKILL_SET;
		
		if(builder.startingHp != null)
			this.startingHp = builder.startingHp;
		else
			this.startingHp = DEFAULT_DOUBLE;
		
		if(builder.petName != null)
			this.petName = builder.petName;
		else
			this.petName = DEFAULT_PET_NAME;
	}

	/**
	 * @return the petType
	 */
	public PetTypes getPetType() {
		return petType;
	}

	/**
	 * @return the playerType
	 */
	public PlayerTypes getPlayerType() {
		return playerType;
	}

	/**
	 * @return the skillSet
	 */
	public Set<Skills> getSkillSet() {
		return skillSet;
	}

	/**
	 * @return the startingHp
	 */
	public double getStartingHp() {
		return startingHp;
	}

	public Object getPetName() {
		return petName;
	}
	
	
	public static class PlayerEventInfoBuilder
	{
		private String petName;
		private PetTypes petType;
		private PlayerTypes playerType;
		private Set<Skills> skillSet;
		private Double startingHp;
		
		public PlayerEventInfoBuilder()
		{
			
		}
		
		public PlayerEventInfoBuilder withPetType(PetTypes petType)
		{
			this.petType = petType;
			return this;
		}
		
		public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType)
		{
			this.playerType = playerType;
			return this;
		}
		
		public PlayerEventInfoBuilder withSkillSet(Set<Skills> skillSet)
		{
			this.skillSet = skillSet;
			return this;
		}
		
		public PlayerEventInfoBuilder withStartingHp(double startingHp)
		{
			this.startingHp = startingHp;
			return this;
		}
		
		public PlayerEventInfoBuilder withPetName(String petName)
		{
			this.petName = petName;
			return this;
		}
		
		public PlayerEventInfo build()
		{
			return new PlayerEventInfo(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((playerType == null) ? 0 : playerType.hashCode());
		result = prime * result + ((skillSet == null) ? 0 : skillSet.hashCode());
		long temp;
		temp = Double.doubleToLongBits(startingHp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
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
		PlayerEventInfo other = (PlayerEventInfo) obj;
		if (petType != other.petType)
			return false;
		if (playerType != other.playerType)
			return false;
		if (skillSet == null) {
			if (other.skillSet != null)
				return false;
		} else if (!skillSet.equals(other.skillSet))
			return false;
		if (Double.doubleToLongBits(startingHp) != Double.doubleToLongBits(other.startingHp))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PlayerEventInfo [petType=" + petType + ", playerType=" + playerType + ", skillSet=" + skillSet
				+ ", startingHp=" + startingHp + "]";
	}
}
