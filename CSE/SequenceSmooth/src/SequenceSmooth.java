import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Put your name here
 *
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
        Sequence<Integer> tempS2 = new Sequence1L<>();
        if (s1.length() == 1) {
            s2.transferFrom(tempS2);
        } else {
            for (int i = 0; i < s1.length() - 1; i++) {
                int a = s1.entry(i);
                int b = s1.entry(i + 1);
                int res = (a + b) / 2;
                tempS2.add(i, res);
            }
            s2.transferFrom(tempS2);
        }

    }

    public static void smoothRe(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
        s2.clear();
        int i = 0;
        if (s1.length() != 1) {
            int fir = s1.remove(0);
            int sec = s1.remove(0);
            s1.add(0, sec);
            smoothRe(s1, s2);
            s2.add(i, (fir + sec) / 2);
            i++;
            s1.add(0, fir);

        }

    }

    public static Sequence<Integer> smoothRe1(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
        Sequence<Integer> res = new Sequence1L<>();
        if (s1.length() != 1) {
            int fir = s1.remove(0);
            int sec = s1.entry(0);
            res = smoothRe1(s1);
            res.add(0, (fir + sec) / 2);
        }
        return res;

    }

    public static int average(int j, int k) {
        int res;
        if (j + k < Integer.MAX_VALUE) {
            res = (j + k) / 2;
        } else {
            j = j / 2;
            k = k / 2;
            res = (j + k);
        }
        return res;
    }

    public static void main(String[] args) {
        int i = average(Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.print(i);
    }

}