import java.util.Objects;

public class Transition
{
    // Transition Attributes
    private final String symbol;
    private final State fromState;
    private final State toState;

    // Constructor
    public Transition(State fromState, String symbol, State toState)
    {
        // Error prevention - if epsilon is passed (the empty transition) rather than null just convert to null
        if(symbol == "ε")
        {
            symbol = null;
        }

        this.symbol = symbol;
        this.fromState = fromState;
        this.toState = toState;
    }

    // Getters
    public String getSymbol()
    {
        return this.symbol;
    }

    public State getFromState()
    {
        return this.fromState;
    }

    public State getToState()
    {
        return this.toState;
    }

    // For preventing duplicate transitions in hashsets/maps
    @Override
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

        Transition that = (Transition) o;
        return getFromState() == that.getFromState() && getSymbol() == that.getSymbol() && getToState() == that.getToState();
    }

    // For preventing duplicate transitions in hashsets/maps
    @Override
    public int hashCode()
    {
        return Objects.hash(getFromState(), getSymbol(), getToState());
    }

    // For debugging
    @Override
    public String toString()
    {
        return("Transition " + getSymbol() + " from " + getFromState().getID() + " to " + getToState().getID());
    }
}