public class State {
    // Class Attributes
    private final int id;
    private boolean isAccepting;
    private boolean isStart;

    // Constructor
    public State(int id, boolean isAccepting, boolean isStart)
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
    public int getID()
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
}
