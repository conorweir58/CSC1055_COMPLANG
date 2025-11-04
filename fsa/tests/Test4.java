public class Test4
{
    public static void main(String[] args) 
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("1", false, true);
        fsa.addState("2", false, false);
        fsa.addState("3", false, false);
        fsa.addState("4", false, false);
        fsa.addState("5", true, false);
        fsa.addState("6", false, false);
        fsa.addState("7", false, false);
        fsa.addState("8", false, false);
        fsa.addState("9", false, false);
        fsa.addState("10", true, false);


        // Normal transitions
        fsa.addTransition("1", "a", "2");
        fsa.addTransition("1", "b", "3");
        fsa.addTransition("2", "a", "4");
        fsa.addTransition("4", "a", "5");
        fsa.addTransition("5", "a", "3");
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
        
        // Extra transitions
        fsa.addTransition("7", "a", "10");
        fsa.addTransition("10", "a", "8");
        fsa.addTransition("8", null, "9");

        System.out.println(fsa.accepts("ad"));
        System.out.println("------------------------");
        System.out.println(fsa.accepts("adaa"));
        System.out.println("------------------------");
        System.out.println(fsa.accepts("eeee"));


        System.out.println("------------------------");
        boolean isDeterministic = fsa.deterministic();
        System.out.println("Is deterministic: " + isDeterministic);
        System.out.println("------------------------");
        fsa.toDFA();
        isDeterministic = fsa.deterministic();
        System.out.println("Is deterministic after conversion to DFA: " + isDeterministic);
    }
}
