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
                if(transition.getFromState().getID().equals(currStateID) && transition.getSymbol() == null) // == as .equals() on null can't be done
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
    // Retursn the set of states that can be reached from the given state on having input the given (non-empty) input symbol
    public Set<String> next(String givenStateID, String symbol)
    {
        // Passed symbol cannot be null
        if(symbol == null)
        {
            System.err.println("Symbol for next() cannot be NULL! Stopping execution of next()..."); // throw error
            return null; // return out of function
        }

        Set<String> nextSet = closure(givenStateID); // Tracks States which are possible to be reached next using the symbol once
        Stack<String> statesToClose = new Stack<>(); // Tracks states which must be checked for epsilons (i.e. do a closure) after symbol

        statesToClose.push(givenStateID); // Push given State as this will also need to be checked for symbols again but not in nextSet
        
        // Push all epsilon reachable states from givenState to stack to be checked for symbol reachability
        for(String StateID : nextSet)
        {
            statesToClose.push(StateID);
        }
        
        Set<String> tmp = new HashSet<>(); // Hashset for adding all states found using symbol (saves time looping again through all found states for more epsilon transitions)
        while(!statesToClose.empty()) // Can't iterate through hashset and make changes to it
        {
            String currStateID = statesToClose.pop(); // Get State from stack

            for(Transition transition : getTransitions())
            {
                // Only gets transitions which are from the current state and are match the input symbol
                if(transition.getFromState().getID().equals(currStateID) && symbol.equals(transition.getSymbol()))
                {
                    String nextStateID = transition.getToState().getID();

                    if(!nextSet.contains(nextStateID))
                    {
                        nextSet.add(nextStateID);
                        tmp.add(nextStateID);
                    }
                }
            }
        }

        // Call closure on all states reached by the symbol after previous closure
        for(String StateID : tmp)
        {
            nextSet.addAll(closure(StateID)); // Merges nextSet and the result of the closure on StateID
        }

        return nextSet;
    }

    public boolean accepts(String inputString) {
        if (startState == null) {
            System.err.println("No Start State provided to this FSA!");
            return false;
        }

        Set<String> currStates = closure(getstartState().getID());
        currStates.add(getstartState().getID()); // Include start state in current states
        System.out.println("Closure of start state: " + currStates);

        for(char c : inputString.toCharArray())
        {
            String input = Character.toString(c); // input must be String to be passed to next()
            Set<String> nextStates = new HashSet<>(); // Tracks states that can be reached from that states in currStates -> resets every loop so no old states are left in it
            
            // Iterate over current states and get next states for each using input
            for(String state : currStates)
            {
                nextStates.addAll(next(state, input)); // add all states accessible from previous states using the current input to nextStates
            }

            currStates = nextStates; // currentStates becomes nextStates for next iteration
            if(currStates.isEmpty()) // if there are no states reachable -> impossible for any to be accepting so return false
            {
                return false;
            }

            System.out.println("Current States for input: " + input);
            System.out.println(currStates);
        }

        for(String state : currStates)
        {
            State checkState = getStates().get(state);
            System.out.println("Checking state for acceptance: " + checkState);
            
            if(checkState.getAccepting())
            {
                return true;
            }
        }

        return false;
    }

    // public boolean accepts(String inputString)
    // {
    //     // Error check for start state
    //     if(startState == null)
    //     {
    //         System.err.println("No Start State provided to this FSA! Cannot process input strings without Start State.");
    //         return false;
    //     }

    //     // 'Pop' the first input symbol to process next() on for the FSA's start state
    //     String input = inputString.substring(0, 1);
    //     inputString = inputString.substring(1);

    //     Set<String> currStates = next(getstartState().getID(), input);

    //     // TESTING -> TO BE REMOVED
    //     System.out.println("Current States for input: " + input);
    //     System.out.println(currStates);

    //     // Loop through remaining string + get each input individually
    //     for(char c : inputString.toCharArray())
    //     {
    //         input = Character.toString(c); // input must be String to be passed to next() 
    //         Set<String> nextStates = new HashSet<>(); // Tracks states that can be reached from that states in currStates -> resets every loop so no old states are left in it

    //         // Loop through the states accessible using the previous input
    //         for(String state : currStates)
    //         {
    //             nextStates.addAll(next(state, input)); // add all states accessible from previous states using the current input to nextStates
    //             nextStates.add(state);
    //             System.out.println("Next states: " + nextStates);
    //         }

    //         currStates = nextStates; // currentStates becomes nextStates for next iteration
    //         System.out.println("Current States for input: " + input);
    //         System.out.println(currStates);

    //         if(currStates.isEmpty()) // if there are no states reachable -> impossible for any to be accepting so return false
    //         {
    //             return false;
    //         }
    //     }

    //     System.out.println("Final Current States for string: ");
    //     System.out.println(currStates);

    //     State checkState = null;
        
    //     for(String state : currStates)
    //     {
    //         checkState = getStates().get(state);
    //         System.out.println("Checking state for acceptance: " + checkState);
            
    //         if(checkState.getAccepting())
    //         {
    //             return true;
    //         }
    //     } 

    //     return false;
    // }
}