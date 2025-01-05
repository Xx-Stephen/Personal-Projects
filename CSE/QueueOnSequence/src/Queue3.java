import java.util.Iterator;

import components.list.List;
import components.list.List1L;
import components.queue.Queue;
import components.queue.QueueSecondary;

/**
 * {@code Queue} represented as a {@code Sequence} of entries, with
 * implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @correspondence this = $this.entries
 */
public class Queue3<T> extends QueueSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Entries included in {@code this}.
     */
    private List<T> entries;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.entries = new List1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Queue3() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Queue<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Queue<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Queue3<?> : ""
                + "Violation of: source is of dynamic type Queue3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Queue3<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Queue3<T> localSource = (Queue3<T>) source;
        this.entries = localSource.entries;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";

        this.entries.moveToFinish();
        this.entries.addRightFront(x);
        this.entries.moveToStart();

    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";

        // TODO - fill in body

        // This line added just to make the component compilable.
        return null;
    }

    @Override
    public final int length() {

        int res = this.entries.rightLength();
        return res;
    }

    @Override
    public final T front() {
        T res = this.entries.rightFront();
        return res;
    }

    @Override
    public final Iterator<T> iterator() {
        return this.entries.iterator();
    }

}
