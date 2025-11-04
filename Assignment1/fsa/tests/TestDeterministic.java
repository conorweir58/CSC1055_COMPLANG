public class TestDeterministic
{
    public static void main(String[] args)
    {
        System.out.println("---- Test 1: Deterministic FSA ----");
        testDeterministicTrue();

        System.out.println("\n---- Test 2: Non-Deterministic FSA ----");
        testDeterministicFalse();
    }

    // States: A(start) -> B -> C
    // Transitions: A -a-> B, B -b-> C
    public static void testDeterministicTrue()
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("A", false, true);   // start
        fsa.addState("B", false, false);
        fsa.addState("C", true, false);   // accepting

        fsa.addTransition("A", "a", "B");
        fsa.addTransition("B", "b", "C");

        boolean result = fsa.deterministic();
        System.out.println("Expected: true");
        System.out.println("Result:   " + result);
    }

    // State A has *two* transitions for the same symbol 'a'
    // Transitions: A -a-> B and A -a-> C
    public static void testDeterministicFalse()
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("A", false, true);   // start
        fsa.addState("B", true, false);
        fsa.addState("C", true, false);

        fsa.addTransition("A", "a", "B");
        fsa.addTransition("A", "a", "C"); // duplicate symbol from same state -> nondeterministic

        boolean result = fsa.deterministic();
        System.out.println("Expected: false");
        System.out.println("Result:   " + result);

        fsa.toDFA();
        System.out.println("After conversion to DFA:");
        result = fsa.deterministic();
        System.out.println("Expected: true");
        System.out.println("Result:   " + result);
    }
}
