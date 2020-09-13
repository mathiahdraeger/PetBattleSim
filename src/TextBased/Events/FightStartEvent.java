package TextBased.Events;

import java.util.Arrays;
import java.util.List;

import TextBased.Events.PlayerEventInfo.PlayerEventInfoBuilder;

public class FightStartEvent extends BaseEvent
{
	private static final int DEFAULT_INT = 0;
	private static final PlayerEventInfoBuilder playerEventInfoBuilder 
			= new PlayerEventInfo.PlayerEventInfoBuilder();
	private static final List<PlayerEventInfo> DEFAULT_PLAYER_EVENT_INFO 
			= Arrays.asList(playerEventInfoBuilder.build());
	
	private final int fightNumber;
	private final List<PlayerEventInfo> playerEventInfo;
	
	private FightStartEvent(FightStartEventBuilder builder)
	{
		super(EventTypes.FIGHT_START);
		
		if(builder.fightNumber != null)
			this.fightNumber = builder.fightNumber;
		else
			this.fightNumber = DEFAULT_INT;
		
		if(builder.playerEventInfo != null)
			this.playerEventInfo = builder.playerEventInfo;
		else 
			this.playerEventInfo = DEFAULT_PLAYER_EVENT_INFO;
	}
	
	public int getFightNumber()
	{
		return fightNumber;
	}
	
	public List<PlayerEventInfo> getPlayerEventInfo()
	{
		return playerEventInfo;
	}
	
	public static class FightStartEventBuilder
	{
		private Integer	fightNumber;
		private List<PlayerEventInfo> playerEventInfo;
		
		public FightStartEventBuilder()
		{
			
		}
		
		public FightStartEventBuilder withFightNumber(Integer fightNumber)
		{
			this.fightNumber = fightNumber;
			return this;
		}
		
		public FightStartEventBuilder withPlayerEventInfo(List<PlayerEventInfo> playerEventInfo)
		{
			this.playerEventInfo = playerEventInfo;
			return this;
		}
		
		public FightStartEvent build()
		{
			return new FightStartEvent(this);
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
		FightStartEvent other = (FightStartEvent) obj;
		if (fightNumber != other.fightNumber)
			return false;
		if (!playerEventInfo.equals(other.getPlayerEventInfo()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FightStartEvent [fightNumber=" + fightNumber 
				+ " playerEventInfo=" + playerEventInfo + "]";
	}

	@Override
	public int hashcode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + fightNumber;
		//result = prime * result + playerEventInfo.hashCode();
		return result;
	}
}
