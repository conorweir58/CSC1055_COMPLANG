public class Transition
{
    // Transition Attributes
    private final char symbol;
    private final State fromState;
    private final State toState;

    // Constructor
    public Transition(State fromState, char symbol, State toState)
    {
        this.symbol = symbol;
        this.fromState = fromState;
        this.toState = toState;
    }

    // Getters
    public char getSymbol()
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
}