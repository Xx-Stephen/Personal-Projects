import components.statement.Statement;
import components.statement.Statement1;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                for(int i = 0; i < s.lengthOfBlock()           }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement temp = new Statement1();
                if (s.kind() == s.kind().IF) {
                    count++;
                }
                Condition C = s.disassembleIf(temp);
                count = count + countOfPrimitiveCalls(temp);
                s.assembleIf(C, temp);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement temp = new Statement1();
                Statement temp2 = new Statement1();
                if (s.kind() == s.kind().IF_ELSE) {
                    count++;
                }
                Condition C = s.disassembleIfElse(temp, temp2);
                count = count + countOfPrimitiveCalls(temp);
                count = count + countOfPrimitiveCalls(temp2);
                s.assembleIfElse(C, temp, temp2);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement temp = new Statement1();
                if (s.kind() == s.kind().WHILE) {
                    count++;
                }
                Condition C = s.disassembleWhile(temp);
                count = count + countOfPrimitiveCalls(temp);
                s.assembleWhile(C, temp);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                if (s.kind() == s.kind().CALL) {
                    count++;
                }

                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

}
