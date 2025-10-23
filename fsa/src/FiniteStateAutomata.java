import java.util.*;

public class FiniteStateAutomata
{
    // FSA Attributes
    private HashMap<Integer, State> states; // HashMap of State id to the State object -> keys prevent duplicate States
    private Set<Transition> transitions; // HashSet so no duplicate transitions
    private State startState; // Only one start State allowed

    // Constructor
    public FiniteStateAutomata()
    {
        this.states = new HashMap<>();
        this.transitions = new HashSet<>();
        this.startState = null;
    }

    // Setters
    public void setStartState(State state)
    {
        this.startState = state;
    }

    // Getters
    public HashMap<Integer, State> getStates()
    {
        return this.states;
    }

    public Set<Transition> getTransitions()
    {
        return this.transitions;
    }
    
    public State getstartState()
    {
        return this.startState;
    }

    // FSA METHODS

    // Creates a new State and add it to set of States if it doesn't exist already
    public void addState(int id, boolean isAccepting, boolean isStart)
    {
        // If there is already a start state - kill program + throw error
        if(isStart && getstartState() != null)
        {
            System.err.println("This FSA already has a Start State! State " + id + " not added.");
            System.exit(1); // Exit as missing states can break the FSA structure -> Disagree, should just overwrite isStart to false
        }

        if(!getStates().containsKey(id)) // if the state is not already in FSA
        {
            State newState = new State(id, isAccepting, isStart);
            getStates().put(id, newState);

            if(isStart) // Will only pass for first start state - prevented afterwards above
            {
                setStartState(newState);
            }
        }
        else
        {
            System.err.println("State " + id + " already exists!");
        }
    }

    // Creates a Transition between 2 States -> Only valid if both states exist in the FSA
    public void addTransition(int fromState_id, String symbol, int toState_id)
    {
        if(!getStates().containsKey(fromState_id)) // If from state doesn't exist
        {
            System.err.println("From State " + fromState_id + " does not exist! Transition not created!");
            return; // Return to continue input without adding transition
        }

        getTransitions().add(new Transition(getStates().get(fromState_id), symbol, getStates().get(toState_id)));
    }

}