import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Zhang Xinxuan
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test for constructor.
     */
    @Test
    public void constructorTestNoArugument() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExp = this.createFromArgsRef();
        assertEquals(m, mExp);
    }

    /**
     * Test for constructor.
     */
    @Test
    public void constructorTestOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd");
        assertEquals(m, mExp);
    }

    /**
     * Test for constructor.
     */
    @Test
    public void constructorTestMoreArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        assertEquals(m, mExp);
    }

    /**
     * Test for add method.
     */
    @Test
    public void addOneTestOnNoArugument() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd");
        m.add("ab", "cd");
        assertEquals(m, mExp);
    }

    /**
     * Test for add method.
     */
    @Test
    public void addOneTestOnOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        m.add("ef", "gh");
        assertEquals(m, mExp);
    }

    /**
     * Test for add method.
     */
    @Test
    public void addMoreTestOnOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh", "ij", "kl");
        m.add("ef", "gh");
        m.add("ij", "kl");
        assertEquals(m, mExp);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void removeOneTestOnOneArgument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef();
        m.remove("ab");
        assertEquals(m, mExp);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void removeOneTestOnTwoArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef("ef", "gh");
        m.remove("ab");
        assertEquals(m, mExp);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void removeTwoTestOnTwoArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef();
        m.remove("ab");
        m.remove("ef");
        assertEquals(m, mExp);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void removeAnyTestOnOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef();
        m.removeAny();
        assertEquals(m, mExp);
    }

    /**
     * Test for remove method.
     */
    @Test
    public void removeAnyTestOnTwoArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        Pair<String, String> element = m.removeAny();
        String key = element.key();
        assertEquals(true, mExp.hasKey(key));
        mExp.remove(key);
        assertEquals(m, mExp);
    }

    /**
     * Test for value method.
     */
    @Test
    public void valueOneTestOnOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd");
        String element = m.value("ab");
        String elementExp = "cd";
        assertEquals(m, mExp);
        assertEquals(element, elementExp);
    }

    /**
     * Test for value method.
     */
    @Test
    public void valueOneTestOnTwoArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        String element = m.value("ab");
        String elementExp = "cd";
        assertEquals(m, mExp);
        assertEquals(element, elementExp);
    }

    /**
     * Test for value method.
     */
    @Test
    public void valueMoreTestOnMoreArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh",
                "ij", "kl");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh", "ij", "kl");
        String element = m.value("ab");
        String elementExp = "cd";
        String element2 = m.value("ij");
        String element2Exp = "kl";
        assertEquals(m, mExp);
        assertEquals(element, elementExp);
        assertEquals(element2, element2Exp);
    }

    /**
     * Test for haskey method.
     */
    @Test
    public void hasKeyFalseOnEmpty() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExp = this.createFromArgsRef();
        boolean resExp = false;
        boolean res = m.hasKey("ab");
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    /**
     * Test for haskey method.
     */
    @Test
    public void hasKeyTrueOnOneArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd");
        boolean resExp = true;
        boolean res = m.hasKey("ab");
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    /**
     * Test for haskey method.
     */
    @Test
    public void hasKeyTrueOnTwoArugument() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd", "ef", "gh");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        boolean resExp = true;
        boolean res = m.hasKey("ab");
        boolean res2 = m.hasKey("ef");
        assertEquals(res, resExp);
        assertEquals(res2, resExp);
        assertEquals(m, mExp);
    }

    /**
     * Test for size method.
     */
    @Test
    public void sizeTestEmpty() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExp = this.createFromArgsRef();
        int resExp = 0;
        int res = m.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    /**
     * Test for size method.
     */
    @Test
    public void sizeTestOne() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd");
        int resExp = 1;
        int res = m.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    /**
     * Test for size method.
     */
    @Test
    public void sizeTestMore() {
        Map<String, String> m = this.createFromArgsTest("ab", "cd");
        Map<String, String> mExp = this.createFromArgsRef("ab", "cd", "ef",
                "gh");
        m.add("ef", "gh");
        int resExp = 2;
        int res = m.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

}
