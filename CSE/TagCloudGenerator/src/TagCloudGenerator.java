import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

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
    private static final int NewMax = 48;
    /**
     * the new minimum of the range.
     */
    private static final int NewMin = 11;

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
    public static void outputHead(SimpleWriter out, String fileName, int num) {
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
            implements Comparator<Map.Pair<String, Integer>> {
        /**
         * Method compare two integer.
         */
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            int res = o2.value().compareTo(o1.value());
            return res;
        }

    }

    /**
     * Comparator that used to compare the pair in alphabetical order.
     */
    public static class KeyOrder
            implements Comparator<Map.Pair<String, Integer>> {
        /**
         * Method compare two string.
         */
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            int res = o1.key().compareToIgnoreCase(o2.key());
            return res;
        }

    }

    /**
     * Check the character is word or is a separator, and pass the word into the
     * Map.
     *
     * @param oneLine
     *            A line of string
     * @param wordExist
     *            Map contains all the words
     * @update words, wordExist
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
    public static void nextWordOrSeparator(String oneLine,
            Map<String, Integer> wordExist) {
        //Create the separators set and add all the probably separators
        Set<Character> separators = new Set1L<>();
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
        String word = "";
        int i = 0;
        char pos = oneLine.charAt(i);
        //Constantly checking whether the character is in the separators set until the end
        while (i < oneLine.length()) {
            //If it has been contained, move the i to the position of next letter
            if (separators.contains(pos)) {
                while (separators.contains(pos) && i < oneLine.length()) {
                    pos = oneLine.charAt(i);
                    if (separators.contains(pos)) {
                        i++;
                    }
                }
            }
            //If it's not contained, moving forward to get all the characters of the word
            if (!separators.contains(pos)) {
                while (!separators.contains(pos) && i < oneLine.length()) {
                    pos = oneLine.charAt(i);
                    if (!separators.contains(pos)) {
                        word += pos;
                    }
                    i++;
                }
                //If the map didn't contain the word, put it in the map and queue
                //and clear the word
                if (!wordExist.hasKey(word)) {
                    wordExist.add(word, 1);
                    word = "";
                } else {
                    //If map contains word, add the count of that word and clear word.
                    int num = wordExist.value(word);
                    num += 1;
                    wordExist.replaceValue(word, num);
                    word = "";
                }
            }
        }
    }

    /**
     * Transfer word from Map to SortingMachine with decreasing order, and then
     * transfer to the SortingMachine with alphabetical order.
     *
     * @param words
     *            Map with all the words and counts
     * @param num
     *            Number of words need to be transfered
     * @update value
     * @clear words
     * @ensure |words| = 0 and |value| + |key| = |#value|
     * @return The sortingMachine with N words
     *
     */
    public static SortingMachine<Map.Pair<String, Integer>> transferWord(
            Map<String, Integer> words, int num) {

        Comparator<Pair<String, Integer>> valueOrder = new ValueOrder();
        Comparator<Pair<String, Integer>> keyOrder = new KeyOrder();
        SortingMachine<Pair<String, Integer>> value = new SortingMachine1L<>(
                valueOrder);
        SortingMachine<Pair<String, Integer>> key = new SortingMachine1L<>(
                keyOrder);

        while (words.size() != 0) {
            Map.Pair<String, Integer> temp = words.removeAny();
            value.add(temp);
        }

        value.changeToExtractionMode();
        int count = 0;
        while (count < num) {
            Pair<String, Integer> temp = value.removeFirst();
            if (count == 0) {
                max = temp.value();
            }
            if (count == num - 1) {
                min = temp.value();
            }
            key.add(temp);
            count++;
        }
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
        res = NewMax - NewMin;
        res = res * (value - min);
        res = res / (max - min);
        res += NewMin;
        return res;

    }

    /**
     * Print the body of the HTML.
     *
     * @param key
     *            The SortingMachine contains the words.
     * @param out
     *            The output stream.
     * @clear key
     * @ensure |key| = 0
     */
    public static void printBody(SortingMachine<Map.Pair<String, Integer>> key,
            SimpleWriter out) {
        key.changeToExtractionMode();
        while (key.size() != 0) {
            Map.Pair<String, Integer> temp = key.removeFirst();
            String word = temp.key();
            int count = temp.value();
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
    public static void printFoot(SimpleWriter out) {
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
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //Ask for the name of input file and output file, and print head.
        out.println("Please enter the name of input file:");
        String fileName = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(fileName);
        out.println("Please enter the name of output file:");
        String outputName = in.nextLine();
        out.println(
                "Please enter the number of the tag cloud you want to show:");
        int num = in.nextInteger();
        while (num <= 0) {
            out.println("Please enter positive number");
            num = in.nextInteger();
        }
        //Output head.
        SimpleWriter fileOut = new SimpleWriter1L(outputName);
        outputHead(fileOut, fileName, num);
        //Initialize all the Comparator, SortingMachine and Map.
        SortingMachine<Pair<String, Integer>> key;
        Map<String, Integer> words = new Map1L<>();
        //Put all the words in the file into map.
        while (!fileIn.atEOS()) {
            String oneLine = fileIn.nextLine();
            oneLine = oneLine.toLowerCase();
            if (oneLine.length() != 0) {
                nextWordOrSeparator(oneLine, words);
            }
        }
        //Transfer words into SortingMachine.
        key = transferWord(words, num);
        //Print the body.
        printBody(key, fileOut);
        //Print the foot.
        printFoot(fileOut);

        //Close all the SimpleWriter and SimpleReader.
        fileIn.close();
        fileOut.close();
        in.close();
        out.close();
    }

}
