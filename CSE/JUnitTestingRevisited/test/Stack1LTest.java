import components.stack.Stack;
import components.stack.Stack1L;
import components.stack.Stack3;

/**
 * Customized JUnit test fixture for {@code Stack1L}.
 */
public class Stack1LTest extends StackTest {

    @Override
    protected final Stack<String> constructorTest() {

        Stack<String> res = new Stack1L<>();

        // This line added just to make the program compilable.
        return res;
    }

    @Override
    protected final Stack<String> constructorRef() {

        Stack<String> res = new Stack3<>();

        // This line added just to make the program compilable.
        return res;
    }

}