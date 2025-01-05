import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public void constructorTest1() {
        Set<String> s = this.constructorTest();
        Set<String> sExp = this.constructorRef();
        assertEquals(s, sExp);
    }

    @Test
    public void constructorTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue");
        assertEquals(s, sExp);
    }

    @Test
    public void addTest1() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue", "green");
        s.add("green");
        assertEquals(s, sExp);
    }

    @Test
    public void addTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue", "green",
                "black");
        s.add("green");
        s.add("black");
        assertEquals(s, sExp);
    }

    @Test
    public void removeTest1() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("blue");
        s.remove("red");
        assertEquals(s, sExp);
    }

    @Test
    public void removeTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue", "green",
                "black");
        Set<String> sExp = this.createFromArgsRef("black");
        s.remove("red");
        s.remove("blue");
        s.remove("green");
        assertEquals(s, sExp);
    }

    @Test
    public void sizeTest1() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue");
        int i = s.size();
        int iExp = 2;
        assertEquals(s, sExp);
        assertEquals(i, iExp);
    }

    @Test
    public void sizeTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("blue");
        s.remove("red");
        int i = s.size();
        int iExp = 1;
        assertEquals(s, sExp);
        assertEquals(i, iExp);
    }

    @Test
    public void containsTest1() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue");
        boolean exp = true;
        boolean res = s.contains("red");
        assertEquals(s, sExp);
        assertEquals(res, exp);
    }

    @Test
    public void containsTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue");
        boolean exp = false;
        boolean res = s.contains("black");
        assertEquals(s, sExp);
        assertEquals(res, exp);
    }

    @Test
    public void removeAnyTest1() {
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExp = this.createFromArgsRef();
        String return1 = s.removeAny();
        String returnExp = "red";
        assertEquals(s, sExp);
        assertEquals(return1, returnExp);
    }

    @Test
    public void removeAnyTest2() {
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExp = this.createFromArgsRef("red", "blue");
        boolean iExp = true;
        String element = s.removeAny();
        boolean i = sExp.contains(element);
        sExp.remove(element);
        assertEquals(i, iExp);
        assertEquals(s, sExp);
    }

}
