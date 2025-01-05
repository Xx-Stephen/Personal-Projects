import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Put a short phrase describing the program here.
 *
 * @author Xinxuan Zhang
 *
 */
public final class TagCloudGenerator {
    /**
     * Default Constructor.
     */
    private TagCloudGenerator() {

    }

    /**
     * The max value in the counts of the value.
     */
    private static int max = 0;
    /**
     * The minimum value in the counts of the value.
     */
    private static int min = 0;
    /**
     * The new max of the range.
     */
    private static final int NEWMAX = 48;
    /**
     * the new minimum of the range.
     */
    private static final int NEWMIN = 11;

    /**
     * Print the head of the HTML file.
     *
     * @param out
     *            The output stream for HTML.
     * @param fileName
     *            The input file name
     * @param num
     *            The number of words should be printed
     */
    public static void outputHead(PrintWriter out, String fileName, int num) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + num + " words in " + fileName + "</title>");
        out.println(
                "<link href=\"data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + num + " words in " + fileName + "</h2>");
        out.println("<hr>");
        out.println("<body>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * Comparator that used to compare the pair by decreasing order.
     */
    public static class ValueOrder
            implements Comparator<Map.Entry<String, Integer>> {

        /**
         * Method compare two integer.
         */
        @Override
        public int compare(Entry<String, Integer> o1,
                Entry<String, Integer> o2) {
            int res = o2.getValue().compareTo(o1.getValue());

            return res;
        }

    }

    /**
     * Comparator that used to compare the pair in alphabetical order.
     */
    public static class KeyOrder
            implements Comparator<Map.Entry<String, Integer>> {
        /**
         * Method compare two string.
         */

        @Override
        public int compare(Entry<String, Integer> o1,
                Entry<String, Integer> o2) {
            int res = o1.getKey().compareToIgnoreCase(o2.getKey());

            return res;
        }

    }

    /**
     * The method used to generate the Set that contains all the separators.
     *
     * @return Set with separators
     */
    public static Set<Character> separatorGenerator() {
        Set<Character> separators = new HashSet<>();
        separators.add(' ');
        separators.add('\\');
        separators.add('\t');
        separators.add('\n');
        separators.add('\r');
        separators.add(',');
        separators.add('-');
        separators.add('.');
        separators.add('!');
        separators.add('?');
        separators.add('[');
        separators.add(']');
        separators.add('\'');
        separators.add(';');
        separators.add(':');
        separators.add('/');
        separators.add('(');
        separators.add(')');
        separators.add('*');
        separators.add('&');
        separators.add('^');
        separators.add('%');
        separators.add('$');
        separators.add('#');
        separators.add('@');
        return separators;

    }

    /**
     * Check the character is word or is a separator, and pass the word into the
     * Map.
     *
     * @param oneLine
     *            A line of string
     * @param wordExist
     *            Map contains all the words
     * @param position
     *            The current position in the line
     * @update words, wordExist
     * @return String that is word or separator
     * @ensures
     *
     *          <pre>
     * {@code nextWord =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     *          </pre>
     */
    public static String nextWordOrSeparator(String oneLine,
            Map<String, Integer> wordExist, int position) {
        Set<Character> separators = separatorGenerator();
        String res = "";
        char pos = oneLine.charAt(position);
        int endPosition = position;
        endPosition += 1;
        while (endPosition < oneLine.length() && separators.contains(
                oneLine.charAt(endPosition)) == separators.contains(pos)) {
            endPosition += 1;
        }
        res = oneLine.substring(position, endPosition);
        return res;
    }

    /**
     * Transfer word from Map to List with decreasing order, and then transfer
     * to the List with alphabetical order.
     *
     * @param words
     *            Map with all the words and counts
     * @param num
     *            Number of words need to be transfered
     * @update value
     * @clear words
     * @ensure |words| = 0 and |value| + |key| = |#value|
     * @return The List with N words
     *
     */
    public static List<Map.Entry<String, Integer>> transferWord(
            Map<String, Integer> words, int num) {

        Comparator<Map.Entry<String, Integer>> valueOrder = new ValueOrder();

        List<Map.Entry<String, Integer>> value;
        value = new ArrayList<Map.Entry<String, Integer>>();
        List<Map.Entry<String, Integer>> key;
        key = new ArrayList<Map.Entry<String, Integer>>();

        Set<Map.Entry<String, Integer>> entry = words.entrySet();
        Iterator<Map.Entry<String, Integer>> mapIterator = entry.iterator();

        while (mapIterator.hasNext()) {
            Map.Entry<String, Integer> temp = mapIterator.next();
            mapIterator.remove();
            value.add(temp);
        }

        value.sort(valueOrder);
        if (value.size() != 0) {
            max = value.get(0).getValue();
            min = value.get(num - 1).getValue();
        }
        while (value.size() > 0 && num > key.size()) {
            Map.Entry<String, Integer> temp = value.get(0);
            value.remove(0);
            key.add(temp);
        }
        Comparator<Map.Entry<String, Integer>> keyOrder = new KeyOrder();
        key.sort(keyOrder);
        return key;
    }

    /**
     * The method uses linear Conversion to get the font size.
     *
     * @param value
     *            The old value
     * @param max
     *            The max of the old range;
     * @param min
     *            The minimum of the old range
     * @return The new value after conversion
     */
    public static int linearConversion(int max, int min, int value) {
        int res = 0;
        res = NEWMAX - NEWMIN;
        res = res * (value - min);
        if (max - min != 0) {
            res = res / (max - min);
        } else {
            res = 0;
        }
        res += NEWMIN;
        return res;

    }

    /**
     * Print the body of the HTML.
     *
     * @param key
     *            The List contains the words.
     * @param out
     *            The output stream.
     * @clear key
     * @ensure |key| = 0
     */
    public static void printBody(List<Map.Entry<String, Integer>> key,
            PrintWriter out) {

        for (Map.Entry<String, Integer> temp : key) {
            String word = temp.getKey();
            int count = temp.getValue();
            int font = linearConversion(max, min, count);
            out.println("<span style=\"cursor:default\" class=\"f" + font
                    + "\" title=\"count: " + count + "\">" + word + "</span>");
        }
    }

    /**
     * Print the foot of the HTML.
     *
     * @param out
     *            The output stream
     */
    public static void printFoot(PrintWriter out) {
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        //Ask for the name of input file and output file, and words number.
        System.out.println("Please enter the name of input file:");
        String fileName = "";
        BufferedReader fileIn = null;
        while (fileIn == null) {
            try {
                fileName = in.readLine();
                fileIn = new BufferedReader(new FileReader(fileName));
            } catch (IOException e) {
                System.err.print("Error: Can't read the input file name");
            }
        }

        System.out.println("Please enter the name of output file:");
        String outputName = "";
        PrintWriter fileOut = null;
        while (fileOut == null) {
            try {
                outputName = in.readLine();
                fileOut = new PrintWriter(
                        new BufferedWriter(new FileWriter(outputName)));
            } catch (IOException e) {
                System.err.print("Error: The output file name is not valid");
            }
        }
        System.out.println(
                "Please enter the number of the tag cloud you want to show:");
        int numOfWord = 0;
        while (numOfWord <= 0) {
            try {
                String num = in.readLine();
                numOfWord = Integer.parseInt(num);
            } catch (IOException e) {
                System.err.println("Error: Invalid input number");
            } catch (NumberFormatException e) {
                System.err.println("Error: The format of number is invalid");
            }
        }

        //Output head.

        outputHead(fileOut, fileName, numOfWord);

        //Initialize the Map.
        Map<String, Integer> words = new HashMap<>();

        //Put all the words in the file into map.
        String oneLine = "";
        Set<Character> separators = separatorGenerator();
        try {
            oneLine = fileIn.readLine();
        } catch (IOException e) {
            System.err.print("Error: Cannot read the file.");
        }

        while (oneLine != null) {
            oneLine = oneLine.toLowerCase();
            int pos = 0;
            while (pos < oneLine.length() && oneLine.length() != 0) {
                String word = nextWordOrSeparator(oneLine, words, pos);
                if (!separators.contains(word.charAt(0))) {
                    if (!words.containsKey(word)) {
                        words.put(word, 1);
                    } else {
                        int num = words.get(word);
                        int newNum = num + 1;
                        words.replace(word, num, newNum);
                    }
                }
                pos += word.length();
            }
            try {
                oneLine = fileIn.readLine();
            } catch (IOException e) {
                System.err.print("Error: Cannot read the file.");
            }
        }
        //Transfer words into the list.
        List<Map.Entry<String, Integer>> key;
        key = transferWord(words, numOfWord);
        //Print the body.
        printBody(key, fileOut);
        //Print the foot.
        printFoot(fileOut);

        //Close all the input and output stream.
        try {
            fileIn.close();
        } catch (IOException e) {
            System.err.print("Error: The fileIn can not be closed");
        }
        fileOut.close();
    }

}
