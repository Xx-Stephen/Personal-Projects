import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Zhang Xinxuan
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    @Test
    public final void testEmptyConstructor() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExp = this.constructorRef();
        assertEquals(n, nExp);
    }

    @Test
    public final void testIntConstructor() {
        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExp = this.constructorRef(123);
        assertEquals(n, nExp);
    }

    @Test
    public final void testStringConstructor() {
        NaturalNumber n = this.constructorTest("123");
        NaturalNumber nExp = this.constructorRef("123");
        assertEquals(n, nExp);
    }

    @Test
    public final void testNaturalNumberConstructor() {
        NaturalNumber test = new NaturalNumber1L(123);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExp = this.constructorRef(test);
        assertEquals(n, nExp);
    }

    @Test
    public final void testDevidedBy10Empty() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExp = this.constructorRef();
        int res = n.divideBy10();
        int resExp = 0;
        assertEquals(n, nExp);
        assertEquals(res, resExp);
    }

    @Test
    public final void testDevidedBy10LeavingEmpty() {
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExp = this.constructorRef();
        int res = n.divideBy10();
        int resExp = 1;
        assertEquals(n, nExp);
        assertEquals(res, resExp);
    }

    @Test
    public final void testDevidedBy10LeavingNonEmptyOne() {
        NaturalNumber n = this.constructorTest(12);
        NaturalNumber nExp = this.constructorRef(1);
        int res = n.divideBy10();
        int resExp = 2;
        assertEquals(n, nExp);
        assertEquals(res, resExp);
    }

    @Test
    public final void testDevidedBy10LeavingNonEmptyMoreThanOne() {
        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExp = this.constructorRef(12);
        int res = n.divideBy10();
        int resExp = 3;
        assertEquals(n, nExp);
        assertEquals(res, resExp);
    }

    @Test
    public final void testIntMultiplyBy10AddZero() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExp = this.constructorRef();
        n.multiplyBy10(0);
        assertEquals(n, nExp);
    }

    @Test
    public final void testIntMultiplyBy10Empty() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExp = this.constructorRef(1);
        n.multiplyBy10(1);
        assertEquals(n, nExp);
    }

    @Test
    public final void testIntMultiplyBy10NonEmptyOne() {
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExp = this.constructorRef(12);
        n.multiplyBy10(2);
        assertEquals(n, nExp);
    }

    @Test
    public final void testIntMultiplyBy10NonEmptyMoreThanOne() {
        NaturalNumber n = this.constructorTest(12);
        NaturalNumber nExp = this.constructorRef(123);
        n.multiplyBy10(3);
        assertEquals(n, nExp);
    }

    @Test
    public final void testIsZeroTrue() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExp = this.constructorRef();
        boolean res = n.isZero();
        boolean resExp = true;
        assertEquals(res, resExp);
        assertEquals(n, nExp);
    }

    @Test
    public final void testIsZeroFalse() {
        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExp = this.constructorRef(123);
        boolean res = n.isZero();
        boolean resExp = false;
        assertEquals(res, resExp);
        assertEquals(n, nExp);
    }

}
