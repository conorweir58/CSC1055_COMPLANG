import java.util.Objects;

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

    // Setters - Encapsulation
    public void setAccepting(boolean isAccepting)
    {
        this.isAccepting = isAccepting;
    }
    
    public void setStart(boolean isAccepting)
    {
        this.isAccepting = isAccepting;
    }

    // Getters - Encapsulation
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

    // For preventing duplicate states in hashsets/maps
    @Override // Polymorphism
    public boolean equals(Object o)
    {
        if(this == o) // If same object
        {
            return true;
        }
        if(o == null || getClass() != o.getClass()) // if compared object null or of different class
        {
            return false;
        }

        State that = (State) o;
        return getID() == that.getID();
    }

    // For preventing duplicate states in hashsets/maps
    @Override // Polymorphism
    public int hashCode()
    {
        return Objects.hash(getID());
    }

    // For debugging
    @Override
    public String toString()
    {
        return("State ID: " + getID() + " -> Accepting: " + getAccepting() + " StartState: " + getStart());
    }
}
