import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Xinxuan Zhang
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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
    public void constructorEmptyTest() {
        Set<String> s = this.createFromArgsRef();
        Set<String> sExp = this.createFromArgsTest();
        assertEquals(s, sExp);
    }

    @Test
    public void constructorOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a");
        assertEquals(s, sExp);
    }

    @Test
    public void constructorMoreTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest("a", "b", "c");
        assertEquals(s, sExp);
    }

    @Test
    public void addOneOnEmptyTest() {
        Set<String> s = this.createFromArgsRef();
        Set<String> sExp = this.createFromArgsTest("a");
        s.add("a");
        assertEquals(s, sExp);
    }

    @Test
    public void addOneOnNoneEmptyTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a", "b");
        s.add("b");
        assertEquals(s, sExp);
    }

    @Test
    public void addMoreOnNoneEmptyTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a", "b", "c");
        s.add("b");
        s.add("c");
        assertEquals(s, sExp);
    }

    @Test
    public void removeOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest();
        String res = s.remove("a");
        String resExp = "a";
        assertEquals(s, sExp);
        assertEquals(res, resExp);

    }

    @Test
    public void removeTwoTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest("c");
        String res = s.remove("a");
        String resExp = "a";
        String res2 = s.remove("b");
        String res2Exp = "b";
        assertEquals(s, sExp);
        assertEquals(res, resExp);
        assertEquals(res2, res2Exp);

    }

    @Test
    public void removeMoreTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest();
        String res = s.remove("a");
        String resExp = "a";
        String res2 = s.remove("b");
        String res2Exp = "b";
        String res3 = s.remove("c");
        String res3Exp = "c";
        assertEquals(s, sExp);
        assertEquals(res, resExp);
        assertEquals(res2, res2Exp);
        assertEquals(res3, res3Exp);

    }

    @Test
    public void removeAnyOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest();
        String res = s.removeAny();
        String resExp = "a";
        assertEquals(s, sExp);
        assertEquals(res, resExp);
    }

    @Test
    public void removeAnyTwoTest() {
        Set<String> s = this.createFromArgsRef("a", "b");
        Set<String> sExp = this.createFromArgsTest("a", "b");
        String element = s.removeAny();
        boolean res = sExp.contains(element);
        sExp.remove(element);
        assertEquals(s, sExp);
        assertEquals(true, res);
    }

    @Test
    public void removeAnyMoreTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest("a", "b", "c");
        String element = s.removeAny();
        boolean res = sExp.contains(element);
        sExp.remove(element);
        assertEquals(s, sExp);
        assertEquals(true, res);
    }

    @Test
    public void containsFalseOnEmptyTest() {
        Set<String> s = this.createFromArgsRef();
        Set<String> sExp = this.createFromArgsTest();
        boolean res = s.contains("a");
        assertEquals(s, sExp);
        assertEquals(false, res);
    }

    @Test
    public void containsFalseOnOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a");
        boolean res = s.contains("b");
        assertEquals(s, sExp);
        assertEquals(false, res);
    }

    @Test
    public void containsTrueOnOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a");
        boolean res = s.contains("a");
        assertEquals(s, sExp);
        assertEquals(true, res);
    }

    @Test
    public void containsTrueOnMoreTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest("a", "b", "c");
        boolean res = s.contains("b");
        assertEquals(s, sExp);
        assertEquals(true, res);
    }

    @Test
    public void sizeZeroTest() {
        Set<String> s = this.createFromArgsRef();
        Set<String> sExp = this.createFromArgsTest();
        int res = s.size();
        assertEquals(s, sExp);
        assertEquals(0, res);
    }

    @Test
    public void sizeOneTest() {
        Set<String> s = this.createFromArgsRef("a");
        Set<String> sExp = this.createFromArgsTest("a");
        int res = s.size();
        assertEquals(s, sExp);
        assertEquals(1, res);
    }

    @Test
    public void sizeMoreTest() {
        Set<String> s = this.createFromArgsRef("a", "b", "c");
        Set<String> sExp = this.createFromArgsTest("a", "b", "c");
        int res = s.size();
        int resExp = 3;
        assertEquals(s, sExp);
        assertEquals(resExp, res);
    }

}
