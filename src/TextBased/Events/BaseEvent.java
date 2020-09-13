package TextBased.Events;

public abstract class BaseEvent 
{
	private EventTypes eventType;
	
	public BaseEvent(EventTypes eventType)
	{
		this.eventType = eventType;
	}
	
	public EventTypes getEventType()
	{
		return this.eventType;
	}
	
	public abstract int hashcode();
	public abstract boolean equals(Object obj);
	public abstract String toString();
}
