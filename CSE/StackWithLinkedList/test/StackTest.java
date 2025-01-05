import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    @Test
    public void constructorTest1() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsRef();
        assertEquals(s, sExp);
    }

    @Test
    public void constructorTest2() {
        Stack<String> s = this.createFromArgsTest("a");
        Stack<String> sExp = this.createFromArgsRef("a");
        assertEquals(s, sExp);
    }

    @Test
    public void constructorTest3() {
        Stack<String> s = this.createFromArgsTest("a", "b");
        Stack<String> sExp = this.createFromArgsRef("a", "b");
        assertEquals(s, sExp);
    }

    @Test
    public void pushTest1() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsTest("a");
        s.push("a");
        assertEquals(s, sExp);
    }

    @Test
    public void pushTest2() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExp = this.createFromArgsTest("a", "b");
        s.push("a");
        s.push("b");
        assertEquals(s, sExp);
    }

    @Test
    public void pushTes31() {
        Stack<String> s = this.createFromArgsTest("a");
        Stack<String> sExp = this.createFromArgsTest("a", "b", "c");
        s.push("b");
        s.push("c");
        assertEquals(s, sExp);
    }

}
