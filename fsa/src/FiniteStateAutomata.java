import java.util.*;

public class FiniteStateAutomata
{
    // FSA Attributes
    private Set<State> states; // Hashset so no duplicate States
    private Set<Transition> transitions; // No duplicate transitions
    private Set<State> acceptanceStates; // No duplicate acceptance states
    private State startState; // Only one start State allowed

    // Constructor
    public FiniteStateAutomata()
    {
        this.states = new HashSet<>();
        this.transitions = new HashSet<>();
        this.acceptanceStates = new HashSet<>();
        // this.startState = null;
    }

    // Setters
    public void setStartState(State state)
    {
        this.startState = state;
    }

    // Getters
    public Set<State> getStates()
    {
        return this.states;
    }

    public Set<Transition> getTransitions()
    {
        return this.transitions;
    }
    
    public Set<State> getAcceptanceStates()
    {
        return this.acceptanceStates;
    }

    public State getstartStates()
    {
        return this.startState;
    }

    // FSA Methods

    // Creates a new State and add it to set of States if it doesn't exist already
    public void addState(int id, boolean isAccepting, boolean isStart)
    {
        this.states.add(new State(id, isAccepting, isStart));
    }

    // Creates a Transition between 2 States -> Only valid if both states exist in the FSA
    public void addTransition(State fromState, char symbol, State toState)
    {
        if(!states.contains(fromState))
        {
            System.err.println("From State does not exist");
        }
        else if(!states.contains(toState))
        {
            System.err.println("To State does not exist");
        }
        else
        {
            this.transitions.add(new Transition(fromState, symbol, toState));
        }
    }
}