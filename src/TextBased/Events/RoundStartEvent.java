package TextBased.Events;

public class RoundStartEvent extends BaseEvent
{
	private int roundNumber;
	
	private RoundStartEvent(RoundStartEventBuilder builder)
	{
		super(EventTypes.ROUND_START);
		
		if(builder.roundNumber != null)
			this.roundNumber = builder.roundNumber;
	}
	
	public int getRoundNumber()
	{
		return this.roundNumber;
	}

	public static class RoundStartEventBuilder
	{
		private Integer roundNumber;
		
		public RoundStartEventBuilder()
		{
	
		}
		
		public RoundStartEventBuilder withRoundNumber(int roundNumber)
		{
			this.roundNumber = roundNumber;
			return this;
		}
		
		public RoundStartEvent build()
		{
			return new RoundStartEvent(this);
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
		RoundStartEvent other = (RoundStartEvent) obj;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoundStartEvent [roundNumber=" + roundNumber + "]";
	}

	@Override
	public int hashcode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + roundNumber;
		return result;
	}
}
