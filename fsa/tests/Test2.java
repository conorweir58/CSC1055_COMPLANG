public class Test2
{
    public static void main(String[] args) 
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("1", false, true);
        fsa.addState("2", false, false);
        fsa.addState("3", false, false);
        fsa.addState("4", false, false);
        fsa.addState("5", false, false);
        fsa.addState("6", false, false);
        fsa.addState("7", false, false);

        // Normal transitions
        fsa.addTransition("1", "a", "2");
        fsa.addTransition("1", "b", "3");
        fsa.addTransition("2", "c", "4");
        fsa.addTransition("3", "d", "5");
        fsa.addTransition("4", "e", "6");

        // null transitions
        fsa.addTransition("3", null, "5");
        fsa.addTransition("5", null, "6");
        fsa.addTransition("6", null, "7");

        // ε transitions
        fsa.addTransition("7", "ε", "5");
        fsa.addTransition("2", "ε", "3");
        fsa.addTransition("4", "ε", "2");

        // Print all states
        System.out.println("All current states: ");
        for (String stateKey : fsa.getStates().keySet())
        {
            System.out.println(fsa.getStates().get(stateKey));
        }

        System.out.println();

        // Print all transitions
        System.out.println("All current transitions: ");
        for (Transition transition : fsa.getTransitions())
        {
            System.out.println(transition);
        }

        System.out.println();

        // Test closure from different starting points
        System.out.println("ε-Closure(3): " + fsa.closure("3"));  // should include 3,5,6,7
        System.out.println("ε-Closure(5): " + fsa.closure("5"));  // should include 5,6,7
        System.out.println("ε-Closure(6): " + fsa.closure("6"));  // should include 6,7,5
        System.out.println("ε-Closure(2): " + fsa.closure("2"));  // should include 2,3,5,6,7
        System.out.println("ε-Closure(1): " + fsa.closure("1"));  // should just be {1} (no ε transitions)
    }
}
