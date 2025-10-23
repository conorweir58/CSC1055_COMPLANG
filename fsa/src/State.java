public class State 
{
    // State Attributes
    private final String id; // String as id can be anything - Number or letter in FSAs
    private boolean isAccepting;
    private boolean isStart;

    // Constructor
    public State(String id, boolean isAccepting, boolean isStart)
    {
        this.id = id;
        this.isAccepting = isAccepting;
        this.isStart = isStart;
    }

    // Setters
    public void setAccepting(boolean isAccepting)
    {
        this.isAccepting = isAccepting;
    }
    
    public void setStart(boolean isAccepting)
    {
        this.isAccepting = isAccepting;
    }

    // Getters
    public String getID()
    {
        return this.id;
    }

    public boolean getAccepting()
    {
        return this.isAccepting;
    }

    public boolean getStart()
    {
        return this.isStart;
    }

    @Override
    public String toString()
    {
        return("State ID: " + getID() + " -> Accepting: " + getAccepting() + " StartState: " + getStart());
    }
}
