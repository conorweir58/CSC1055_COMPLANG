public class Test5
{
    public static void main(String[] args) 
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        // Add states (0-10). Only state 10 is accepting
        fsa.addState("0", false, true);  // start state
        fsa.addState("1", false, false);
        fsa.addState("2", false, false);
        fsa.addState("3", false, false);
        fsa.addState("4", false, false);
        fsa.addState("5", false, false);
        fsa.addState("6", false, false);
        fsa.addState("7", false, false);
        fsa.addState("8", false, false);
        fsa.addState("9", false, false);
        fsa.addState("10", true, false); // accepting state

        // Add transitions
        fsa.addTransition("0", null, "1"); // epsilon transitions represented as null
        fsa.addTransition("0", null, "7");
        fsa.addTransition("1", null, "2");
        fsa.addTransition("1", null, "4");
        fsa.addTransition("2", "a", "3");
        fsa.addTransition("4", "b", "5");
        fsa.addTransition("3", null, "6");
        fsa.addTransition("5", null, "6");
        fsa.addTransition("6", null, "1");
        fsa.addTransition("6", null, "7");
        fsa.addTransition("7", "a", "8");
        fsa.addTransition("8", "b", "9");
        fsa.addTransition("9", "b", "10");

        System.out.println(fsa.accepts("aabb"));
    }
}
