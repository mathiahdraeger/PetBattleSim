package TextBased.Data;

public enum PlayerTypes {
    HUMAN,
    COMPUTER,
    SMARTAI;


    @Override
    public String toString()
    {
        switch (this)
        {
            case HUMAN:
                return "Human";
            case COMPUTER:
                return "Computer";
            case SMARTAI:
                return "Smart AI";
            default:
                return "choose valid type";
        }
    }
}
