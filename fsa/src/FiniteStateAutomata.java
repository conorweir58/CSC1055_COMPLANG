import java.util.*;

public class FiniteStateAutomata
{
    // FSA Attributes
    private HashMap<String, State> states; // HashMap of State id to the State object -> keys prevent duplicate States
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
    public HashMap<String, State> getStates()
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
    public void addState(String id, boolean isAccepting, boolean isStart)
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
    public void addTransition(String fromStateID, String symbol, String toStateID)
    {
        if(!getStates().containsKey(fromStateID)) // If from state doesn't exist
        {
            System.err.println("From State " + fromStateID + " does not exist! Transition not created!");
            return; // Return to continue input without adding transition
        }

        getTransitions().add(new Transition(getStates().get(fromStateID), symbol, getStates().get(toStateID)));
    }

    // Returns set of states that can be reached from the given state by following empty/epsilon transitions
    public Set<String> closure(String givenStateID)
    {
        Set<String> closureSet = new HashSet<>(); // Tracks States which are possible to be reached by closure
        Stack<String> statesToClose = new Stack<>(); // Tracks states which must be checked for epsilons

        statesToClose.push(givenStateID); // Push initial state to stack to begin loop

        while(!statesToClose.isEmpty())
        {
            String currStateID = statesToClose.pop(); // Get State from stack

            for(Transition transition : getTransitions())
            {
                // Only gets transitions which are from the current state and are an epsilon transition
                if(transition.getFromState().getID().equals(currStateID) && transition.getSymbol() == null)
                {
                    String nextStateID = transition.getToState().getID();

                    if(!closureSet.contains(nextStateID)) // If the next State has not yet been visited
                    {
                        closureSet.add(nextStateID); // Add next state to closure output set
                        statesToClose.push(nextStateID); // Push the next state to be checked for further epsilon transitions
                    }
                }
            }
        }

        return closureSet;
    }

    public Set<String> next(String givenStateID, String symbol)
    {
        Set<String> nextSet = closure(givenStateID); // Tracks States which are possible to be reached next
        Stack<String> statesToNext = new Stack<>(); // Tracks states which must be checked to be "nexted" (checked if they can be reached)

        statesToNext.push(givenStateID);
        
        for(String id : nextSet)
        {
            statesToNext.push(id);
        }

        while(!statesToNext.empty())
        {
            String currStateID = statesToNext.pop(); // Get State from stack

            for(Transition transition : getTransitions())
            {
                if(transition.getFromState().getID().equals(currStateID) && (transition.getSymbol() == symbol || transition.getSymbol() == null)) // "==" as .equals() throws error on nulls
                {
                    String nextStateID = transition.getToState().getID();

                    if(!nextSet.contains(nextStateID))
                    {
                        nextSet.add(nextStateID);
                        statesToNext.push(nextStateID);
                    }
                }
            }
        }

        return nextSet;
    }
}