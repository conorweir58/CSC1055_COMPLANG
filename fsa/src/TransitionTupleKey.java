// Custom key class for tracking transitions in FiniteStateAutomata deterministic check -> No built-in tuple in Java
// Based off of https://www.baeldung.com/java-custom-class-map-key

import java.util.Objects;

public class TransitionTupleKey
{
    private final String fromStateID;
    private final String symbol;

    public TransitionTupleKey(String fromStateID, String symbol)
    {
        this.fromStateID = fromStateID;
        this.symbol = symbol;
    }

    public String getFromStateID()
    {
        return this.fromStateID;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    // Polymorphism
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

        TransitionTupleKey that = (TransitionTupleKey) o;
        return getFromStateID() == that.getFromStateID() && getSymbol() == that.getSymbol();
    }

    // Polymorphism
    @Override
    public int hashCode()
    {
        return Objects.hash(getFromStateID(), getSymbol());
    }

    // For debugging
    @Override
    public String toString()
    {
        return("TransitionTupleKey FromStateID: " + getFromStateID() + " Symbol: " + getSymbol());
    }
}