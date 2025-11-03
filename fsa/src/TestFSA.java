import java.util.Set;
import java.util.HashSet;

public class TestFSA
{
    public static void main(String[] args)
    {
        printTestTitle();

        FiniteStateAutomata fsa = new FiniteStateAutomata();

        System.out.println("------- Testing Adding States ------");
        System.out.println();

        // Adding States
        fsa.addState("S0", false, true);
        fsa.addState("S1", false, false);
        fsa.addState("S2", false, false);
        fsa.addState("S3", false, false);
        fsa.addState("S4", false, false);
        fsa.addState("S5", false, false);
        fsa.addState("S6", false, false);
        fsa.addState("S7", false, false);
        fsa.addState("S8", false, false);
        fsa.addState("S9", false, false);
        fsa.addState("S10", true, false);

        // Prints current states in FSA
        System.out.println("Current States in FSA after adding States:");
        for(State state : fsa.getStates().values())
        {
            System.out.println(state);
        }

        System.out.println();
        System.out.println("-- Testing Adding Duplicate States --");
        System.out.println();

        // Add Duplicate States
        fsa.addState("S5", false, false); // Duplicate
        fsa.addState("S7", false, false); // Duplicate
        fsa.addState("S10", true, false); // Duplicate

        System.out.println();

        // Prints current states in FSA
        System.out.println("Current States in FSA after adding duplicate States:");
        for(State state : fsa.getStates().values())
        {
            System.out.println(state);
        }

        System.out.println();
        System.out.println("------ Testing Adding Transitions -----");
        System.out.println();

        // Adding Transitions
        fsa.addTransition("S0", null, "S1"); // epsilon transitions represented as null
        fsa.addTransition("S0", null, "S7");
        fsa.addTransition("S1", null, "S2");
        fsa.addTransition("S1", null, "S4");
        fsa.addTransition("S2", "a", "S3");
        fsa.addTransition("S4", "b", "S5");
        fsa.addTransition("S3", null, "S6");
        fsa.addTransition("S5", null, "S6");
        fsa.addTransition("S6", null, "S1");
        fsa.addTransition("S6", null, "S7");
        fsa.addTransition("S7", "a", "S8");
        fsa.addTransition("S8", "b", "S9");
        fsa.addTransition("S9", "b", "S10");

        System.out.println("Current Transitions in FSA after adding Transitions:");
        for(Transition transition : fsa.getTransitions())
        {
            System.out.println(transition);
        }

        System.out.println();
        System.out.println("-- Testing Adding Duplicate Transitions --");
        System.out.println();

        // Adding Duplicate Transitions
        fsa.addTransition("S2", "a", "S3");
        fsa.addTransition("S4", "b", "S5");
        fsa.addTransition("S6", null, "S7");

        System.out.println("Current Transitions in FSA after adding Transitions:");
        for(Transition transition : fsa.getTransitions())
        {
            System.out.println(transition);
        }

        System.out.println();
        System.out.println("------ Testing Closure on States -----");
        System.out.println();

        // Testing Closure
        Set<String> closureS3 = fsa.closure("S3");
        System.out.println("Closure of S3: " + closureS3); // (should include S1, S2, S4, S6, S7)

        Set<String> closureS0 = fsa.closure("S0");
        System.out.println("Closure of S0: " + closureS0); // (should include S1, S2, S4, S7)

        Set<String> closureS10 = fsa.closure("S10");
        System.out.println("Closure of S10: " + closureS10); // Edge Case (should be empty set)
        
        System.out.println();
        System.out.println("------ Testing Next on States -----");
        System.out.println();

        // Testing Next
        Set<String> nextS4b = fsa.next("S4", "b"); // (should include S1, S2, S4, S5, S7)
        System.out.println("Next from S4 on 'b': " + nextS4b);

        Set<String> next5a = fsa.next("S5", "a");
        System.out.println("Next from S5 on 'a': " + next5a); // (should include S1, S2, S3, S4, S6, S7, S8)

        Set<String> nextS10a = fsa.next("S10", "a");
        System.out.println("Next from S10 on 'a': " + nextS10a); // Edge Case (should be empty set)

        Set<String> nextS1null = fsa.next("S1", null);
        System.out.println("Next from S1 on null: " + nextS1null); // Error check - should be null

        System.out.println();
        System.out.println("------ Testing Next on States -----");
        System.out.println();
    }

    private static void printTestTitle()
    {
        System.out.println("------------------------------------");
        System.out.println("------ Test FSA Functionality ------");
        System.out.println("------------------------------------");
        System.out.println();

    }
}
