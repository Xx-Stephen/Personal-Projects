import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public final class SequenceSmoothTest {

    /**
     * Constructs and returns a sequence of the integers provided as arguments.
     *
     * @param args
     *            0 or more integer arguments
     * @return the sequence of the given arguments
     * @ensures createFromArgs= [the sequence of integers in args]
     */
    private Sequence<Integer> createFromArgs(Integer... args) {
        Sequence<Integer> s = new Sequence1L<Integer>();
        for (Integer x : args) {
            s.add(s.length(), x);
        }
        return s;
    }

    /**
     * Test smooth with s1 = <2, 4, 6> and s2 = <-5, 12>.
     */
    @Test
    public void test1() {
        SimpleReader out = new SimpleReader1L();
        /*
         * Set up variables and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(2, 4, 6);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(2, 4, 6);
        Sequence<Integer> seq2 = this.createFromArgs(-5, 12);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(3, 5);
        SequenceSmooth.smoothRe(seq1, seq2);
        /*
         * Assert that values of variables match expectations
         */
        System.out.println(seq1);
        System.out.print(seq2);
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);

    }

    /**
     * Test smooth with s1 = <7> and s2 = <13, 17, 11>.
     */
    @Test
    public void test2() {
        /*
         * Set up variables and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(7);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(7);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs();
        SequenceSmooth.smoothRe(seq1, seq2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    @Test
    public void test3() {
        Sequence<Integer> seq1 = this.createFromArgs(5, 3, 7, 9);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(5, 3, 7, 9);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(4, 5, 8);
        SequenceSmooth.smoothRe(seq1, seq2);
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    @Test
    public void test4() {
        Sequence<Integer> seq1 = this.createFromArgs(5);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(5);
        Sequence<Integer> seq2 = this.createFromArgs();
        Sequence<Integer> expectedSeq2 = this.createFromArgs();
        SequenceSmooth.smoothRe(seq1, seq2);
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    @Test
    public void test5() {
        Sequence<Integer> seq1 = this.createFromArgs(1073741825, -1073741825);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(1073741825,
                -1073741825);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(0);
        SequenceSmooth.smoothRe(seq1, seq2);
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    @Test
    public void test7() {
        Sequence<Integer> seq1 = this.createFromArgs(-1073741823, 1073741824);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(-1073741823,
                1073741824);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(0);
        SequenceSmooth.smoothRe(seq1, seq2);
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

}