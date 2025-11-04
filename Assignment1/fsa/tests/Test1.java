public class Test1
{
    public static void main(String[] args) 
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("1", false, true);
        fsa.addState("2", false, false);
        fsa.addState("3", false, false);
        fsa.addState("4", false, false);
        fsa.addState("5", false, false);
        
        fsa.addTransition("1", "a", "2");
        fsa.addTransition("1", "b", "3");
        fsa.addTransition("3", "ε", "5");

        System.out.println("All current states: ");
        for(String stateKey : fsa.getStates().keySet())
        {
            System.out.println(fsa.getStates().get(stateKey));
        }

        System.out.println();

        System.out.println("All current transitions: ");
        for(Transition transition : fsa.getTransitions())
        {
            System.out.println(transition);
        }

        System.out.println();
        
        fsa.addState("2", false, false);
        fsa.addTransition("3", "ε", "5");
        fsa.addState("0", false, true);
    }
}