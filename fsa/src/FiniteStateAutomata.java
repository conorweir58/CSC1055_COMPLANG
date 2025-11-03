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

    // Setters - Encapsulation
    public void setStates(HashMap<String, State> states)
    {
        this.states = states;
    }

    public void setTransitions(Set<Transition> transitions)
    {
        this.transitions = transitions;
    }

    public void setStartState(State state)
    {
        this.startState = state;
    }

    // Getters - Encapsulation
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
            throw new IllegalArgumentException("This FSA already has a Start State! State " + id + " not added.");
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
            return null; // return out of function but continue execution
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

        // if no startState - can't accept anything
        if (getstartState() == null)
        {
            System.err.println("No Start State provided to this FSA!");
            return false; // Accepts is impossible without start state so return false but continue execution
        }

        Set<String> currStates = closure(getstartState().getID());
        currStates.add(getstartState().getID()); // Include start state in current states

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
        }

        for(String state : currStates)
        {
            State checkState = getStates().get(state);
            
            if(checkState.getAccepting())
            {
                return true;
            }
        }

        return false;
    }

    public boolean deterministic()
    {
        if(getstartState() == null) // No start state -> cannot be deterministic
        {
            System.err.println("No Start State provided to this FSA! Cannot be considered deterministic without one.");
            return false;
        }

        if(getTransitions().isEmpty()) // No transitions means must be deterministic -> doesn't break any rules necessarily so it is true
        {
            System.err.println("This FSA has no transitions! It is considered deterministic by default.");
            return true;
        }

        HashMap<TransitionTupleKey, State> transitionMap = new HashMap<>(); // HashMap for tracking existing transitions of fromStates and symbols to toStates -> used to check for multiple transitions for same fromState and symbol

        for(Transition t : getTransitions())
        {
            if(t.getSymbol() == null) // If there is an epsilon transition -> not deterministic
            {
                return false;
            }
            
            TransitionTupleKey key = new TransitionTupleKey(t.getFromState().getID(), t.getSymbol()); // Key for tracking fromState and symbol
            if(transitionMap.containsKey(key))
            {
                return false; // Multiple transitions from same fromState with same symbol -> Not DFA
            }
            else
            {
                transitionMap.put(key, t.getToState()); // Add transition to map if doesnt already exist to prevent future duplicates
            }
        }

        return true;
    }

    // Private helper method for toDFA() -> checks if a given set of NFA states contains an acceptance state -> therefore it is also DFA accptance
    private boolean isDFAAcceptState(Set<String> nfaStates)
    {
        for(String stateID: nfaStates)
        {
            State s = getStates().get(stateID);

            if(s != null && s.getAccepting())
            {
                return true;
            }
        }
        return false;
    }

    // Convert FSA -> DFA in place
    public void toDFA()
    {
        // Error handling blocks
        if(deterministic())
        {
            System.out.println("This FSA is already deterministic! No conversion needed.");
            return;
        }
        if(getStates().isEmpty())
        {
            System.err.println("This FSA has no states! Cannot convert to DFA.");
            return;
        }
        if(getstartState() == null)
        {
            System.err.println("This FSA has no start state! All FSA's require a start State. Cannot convert to DFA.");
            return;
        }
    
        Set<String> inputSymbols = new HashSet<>(); // Set of all input symbols in the FSA (excluding epsilon)
        for(Transition t : getTransitions())
        {
            if(t.getSymbol() != null) // Ignore epsilon transitions
            {
                inputSymbols.add(t.getSymbol());
            }
        }

        // When coming back to this - helps to understand that DFA is set of NFA stateIds, and these are stored as comma seperated strings, but for processing we use the sets

        HashMap<String, State> dfaStates = new HashMap<>(); // Map of new state IDs to State objects
        Set<Transition> dfaTransitions = new HashSet<>(); // Set of transitions for the DFA
        Map<Set<String>, String> stateSetToIDMap = new HashMap<>(); // Map of sets of NFA states to DFA state IDs

        Set<String> initialDFAState = closure(getstartState().getID()); // Start state for DFA is closure of NFA start state -> removes all epsilon transitions
        initialDFAState.add(getstartState().getID());
        String startStateID = String.join(",", initialDFAState); // StateID for DFA is comma separated list of NFA states it represents - ensures it will work with all other FSA methods

        State dfaStartState = new State(startStateID, isDFAAcceptState(initialDFAState), true); // Start state for DFA
        dfaStates.put(startStateID, dfaStartState); // Add start state to DFA states
        stateSetToIDMap.put(initialDFAState, startStateID); // Map initial DFA state set to its String DFA ID

        Queue<Set<String>> unprocessedStates = new LinkedList<>(); // Queue of unprocessed state sets
        unprocessedStates.add(initialDFAState);

        while(!unprocessedStates.isEmpty())
        {
            Set<String> currDFAState = unprocessedStates.poll(); // get the next unprocessed DFA state set
            String currDFAStateID = stateSetToIDMap.get(currDFAState); // get the stateID of this DFA state set

            for(String symbol : inputSymbols)
            {
                Set<String> nextNFAStates = next(currDFAStateID, symbol); // Get next NFA states from all NFA states in the current DFA state set using the symbol
                
                for(String nfaStateID: currDFAState)
                {
                    nextNFAStates.addAll(next(nfaStateID, symbol)); // Add next states from each NFA state in the current DFA state set
                }

                if(!nextNFAStates.isEmpty())
                {
                    String nextDFAStateID = String.join(",", nextNFAStates);

                    if(!dfaStates.containsKey(nextDFAStateID)) // If this DFA state doesn't exist yet
                    {
                        // Create new DFA state and add to structures
                        State newDFAState = new State(nextDFAStateID, isDFAAcceptState(nextNFAStates), false);
                        dfaStates.put(nextDFAStateID, newDFAState);
                        stateSetToIDMap.put(nextNFAStates, nextDFAStateID);
                        unprocessedStates.add(nextNFAStates);
                    }

                    // Add transition from current DFA state to next DFA state on symbol
                    dfaTransitions.add(new Transition(dfaStates.get(currDFAStateID), symbol, dfaStates.get(nextDFAStateID)));
                }
            }
        }

        // Update FSA to be the new DFA
        setStates(dfaStates);
        setTransitions(dfaTransitions);
        setStartState(dfaStartState);
    }
}