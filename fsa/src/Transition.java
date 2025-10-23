public class Transition
{
    // Transition Attributes
    private final String symbol;
    private final State fromState;
    private final State toState;

    // Constructor
    public Transition(State fromState, String symbol, State toState)
    {
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

    @Override
    public String toString()
    {
        return("Transition " + getSymbol() + " from " + getFromState().getID() + " to " + getToState().getID());
    }
}