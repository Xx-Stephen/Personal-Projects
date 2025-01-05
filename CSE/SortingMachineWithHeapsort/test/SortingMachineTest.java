import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Xinxuan Zhang
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "b");
        m.add("b");
        assertEquals(mExpected, m);
    }

    @Test
    public void testAddMoreNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "b", "c");
        m.add("b");
        m.add("c");
        assertEquals(mExpected, m);
    }

    @Test
    public void changeToExtractionModeTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        assertEquals(m, mExp);
    }

    @Test
    public void changeToExtractionModeOneTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a");
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        assertEquals(m, mExp);
    }

    @Test
    public void changeToExtractionModeMoreTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a",
                "b", "c");
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        assertEquals(m, mExp);
    }

    @Test
    public void removeFirstOneTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false, "a");
        String res = m.removeFirst();
        String expRes = mExp.removeFirst();
        assertEquals(res, expRes);
        assertEquals(m, mExp);
    }

    @Test
    public void removeFirstTwoTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false, "a",
                "b");
        String res = m.removeFirst();
        String expRes = mExp.removeFirst();
        assertEquals(res, expRes);
        String res2 = m.removeFirst();
        String expRes2 = mExp.removeFirst();
        assertEquals(res2, expRes2);
        assertEquals(m, mExp);
    }

    @Test
    public void removeFirstMoreTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "c",
                "v", "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false, "c",
                "v", "a");
        String res = m.removeFirst();
        String expRes = mExp.removeFirst();
        assertEquals(res, expRes);
        String res2 = m.removeFirst();
        String expRes2 = mExp.removeFirst();
        assertEquals(res2, expRes2);
        String res3 = m.removeFirst();
        String expRes3 = mExp.removeFirst();
        assertEquals(res3, expRes3);
        assertEquals(m, mExp);
    }

    @Test
    public void isInInsertionModeTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);
        boolean res = m.isInInsertionMode();
        boolean resExp = mExp.isInInsertionMode();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    @Test
    public void isInInsertionModeTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false);
        boolean res = m.isInInsertionMode();
        boolean resExp = mExp.isInInsertionMode();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    @Test
    public void isInInsertionModeTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a");
        boolean res = m.isInInsertionMode();
        boolean resExp = mExp.isInInsertionMode();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    @Test
    public void isInInsertionModeTest4() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false, "a");
        boolean res = m.isInInsertionMode();
        boolean resExp = mExp.isInInsertionMode();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    @Test
    public void isInInsertionModeTest5() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a");
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        boolean res = m.isInInsertionMode();
        boolean resExp = mExp.isInInsertionMode();
        assertEquals(res, resExp);
        assertEquals(m, mExp);
    }

    @Test
    public void orderTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false);
        Comparator<String> res = m.order();
        Comparator<String> resExp = m.order();
        assertEquals(res, resExp);
        assertEquals(res, ORDER);
        assertEquals(m, mExp);
    }

    @Test
    public void orderTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false, "a",
                "b");
        Comparator<String> res = m.order();
        Comparator<String> resExp = m.order();
        assertEquals(res, resExp);
        assertEquals(res, ORDER);
        assertEquals(m, mExp);
    }

    @Test
    public void orderTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);
        Comparator<String> res = m.order();
        Comparator<String> resExp = m.order();
        assertEquals(res, resExp);
        assertEquals(res, ORDER);
        assertEquals(m, mExp);
    }

    @Test
    public void orderTest4() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a",
                "b");
        Comparator<String> res = m.order();
        Comparator<String> resExp = m.order();
        assertEquals(res, resExp);
        assertEquals(res, ORDER);
        assertEquals(m, mExp);
    }

    @Test
    public void sizeEmptyTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);
        int res = m.size();
        int resExp = mExp.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);

    }

    @Test
    public void sizeOneTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a");
        int res = m.size();
        int resExp = mExp.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);

    }

    @Test
    public void sizeEmptyMoreTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "d");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "a",
                "b", "d");
        int res = m.size();
        int resExp = mExp.size();
        assertEquals(res, resExp);
        assertEquals(m, mExp);

    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

}
