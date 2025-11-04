public class Test6
{
    public static void main(String[] args)
    {
        test6_2();
    }

    public static void test6_1()
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        // States
        fsa.addState("A", false, true);   // start
        fsa.addState("B", false, false);
        fsa.addState("C", true, false);   // accepting

        // Transitions: A -a-> B -b-> C
        fsa.addTransition("A", "a", "B");
        fsa.addTransition("B", "b", "C");

        // Expected: only "ab" is accepted
        System.out.println(fsa.accepts("ab"));   // true
        System.out.println("---------------------------------------");
        System.out.println(fsa.accepts("a"));    // false
        System.out.println("---------------------------------------");
        System.out.println(fsa.accepts("abb"));  // false
    }

    public static void test6_2()
    {
        FiniteStateAutomata fsa = new FiniteStateAutomata();

        fsa.addState("0", false, true);  // start
        fsa.addState("1", true, false);  // accepting
        fsa.addState("2", true, false);  // accepting

        // 0 can go to 1 on 'a' or to 2 on 'b'
        fsa.addTransition("0", "a", "1");
        fsa.addTransition("0", "b", "2");

        // Accepts: "a", "b"
        System.out.println(fsa.accepts("a"));  // true
        System.out.println("---------------------------------------");
        System.out.println(fsa.accepts("b"));  // true
        System.out.println("---------------------------------------");
        System.out.println(fsa.accepts("ab")); // false
    }
}
