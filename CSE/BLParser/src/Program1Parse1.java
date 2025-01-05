import components.map.Map;
import components.program.Program1;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Xinxuan Zhang
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        String res = "";
        tokens.dequeue();
        String startName = tokens.dequeue();
        res = startName;
        Set<String> primitive = new Set1L<>();
        primitive.add("move");
        primitive.add("skip");
        primitive.add("turnleft");
        primitive.add("turnright");
        primitive.add("turnback");
        Reporter.assertElseFatalError(!primitive.contains(startName),
                "Violation of: The name of Instruction should "
                        + "not be the primitve instruction name");
        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"),
                "Violation of: Syntax error, Expecting IS");
        body.parseBlock(tokens);
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"),
                "Violation of: Syntax error, Expecting END");
        String endName = tokens.dequeue();
        Reporter.assertElseFatalError(endName.equals(startName),
                "Violation of: The identifier at the start of the "
                        + "instruction should be same with the end of the instruction");
        return res;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        Reporter.assertElseFatalError(tokens.front().equals("PROGRAM"),
                "Violation of: Syntax error, Expecting PROGRAM");

        Map<String, Statement> context = this.newContext();
        tokens.dequeue();
        String startName = tokens.dequeue();
        this.setName(startName);
        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"),
                "Violation of: Syntax error, Expecting IS");
        while (tokens.front().equals("INSTRUCTION")) {
            Statement temp = this.newBody();
            String nameOfInstruction = parseInstruction(tokens, temp);
            context.add(nameOfInstruction, temp);
        }
        this.swapContext(context);
        Statement body = this.newBody();
        String begin = tokens.dequeue();
        Reporter.assertElseFatalError(begin.equals("BEGIN"),
                "Violation of: Syntax error, Expecting BEGIN");
        body.parseBlock(tokens);
        this.swapBody(body);
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"),
                "Violation of: Syntax error, Expecting END");
        String endName = tokens.dequeue();
        Reporter.assertElseFatalError(endName.equals(startName),
                "Violation of: The name at start of the program should be "
                        + "same as the name at end of program");
        String endSign = tokens.dequeue();
        Reporter.assertElseFatalError(endSign.equals("### END OF INPUT ###"),
                "Violation of: Syntax error, Expecting ### END OF INPUT ###");

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
//        /*
//         * Get input file name
//         */
//        out.print("Enter valid BL program file name: ");
//        String fileName = in.nextLine();
//        /*
//         * Parse input file
//         */
//        out.println("*** Parsing input file ***");
//        Program p = new Program1Parse1();
//        SimpleReader file = new SimpleReader1L(fileName);
//        Queue<String> tokens = Tokenizer.tokens(file);
//        file.close();
//        p.parse(tokens);
//        /*
//         * Pretty print the program
//         */
//        out.println("*** Pretty print of parsed program ***");
//        p.prettyPrint(out);
        boolean res = false;
        boolean q = !res;
        out.print(q);

        in.close();
        out.close();
    }

}
