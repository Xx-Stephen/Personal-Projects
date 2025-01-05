import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Zhang Xinxuan
 *
 */
public final class WordCounts {
    /**
     * Default Constructor.
     */
    private WordCounts() {

    }

    /**
     * Output the head content of required html.
     *
     * @param out
     *            The output stream.
     * @param fileName
     *            The input file name that used to construct title.
     */
    public static void printHead(SimpleWriter out, String fileName) {
        out.println("<html>");
        out.println("<head>");
        out.print("<title>Words Counted in " + fileName + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Words Counted in " + fileName + "</h2>");
        out.println("<hr />");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
    }

    /**
     * This method is going to put all the word that exist in the document in
     * Queue and Map.
     *
     * @param oneLine
     *            One string that contains one line of the document
     * @param words
     *            Queue that used to store all the words exist in the document
     * @param wordExist
     *            Map that contains all the words and how much times those word
     *            appeared
     */
    public static void nextWordOrSeparator(String oneLine, Queue<String> words,
            Map<String, Integer> wordExist) {
        //Create the separators set and add all the probably separators
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        separators.add('.');
        separators.add('?');
        separators.add('/');
        separators.add('-');
        separators.add('_');
        separators.add('！');
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
                    words.enqueue(word);
                    word = "";
                } else {
                    //If map contains word, add the count of that word and clear word.
                    int count = wordExist.value(word);
                    count++;
                    wordExist.replaceValue(word, count);
                    word = "";
                }
            }
        }
    }

    /**
     * The comparator that compares the order of string.
     */
    public static class AlphabeticalOrder implements Comparator<String> {
        /**
         * String Comparator.
         */
        @Override
        public int compare(String o1, String o2) {
            String s1 = o1.toLowerCase();
            String s2 = o2.toLowerCase();
            int res = 2;
            if (s1.compareTo(s2) > 0) {
                res = 1;
            } else if (s1.compareTo(s2) < 0) {
                res = -1;
            } else {
                res = 0;
            }
            return res;
        }

    }

    /**
     * Output the table that contains name and counts.
     *
     * @param out
     *            The output stream.
     * @param words
     *            Queue that used to store all the words exist in the document
     * @param wordMap
     *            Map that contains all the words and how much times those word
     *            appeared
     */
    public static void outTable(SimpleWriter out, Queue<String> words,
            Map<String, Integer> wordMap) {
        Queue<String> temp = words.newInstance();
        temp.transferFrom(words);
        //Check all the words in the Queue and Map, and form one line table to that word
        while (temp.length() != 0) {
            String word = temp.dequeue();
            int count = wordMap.value(word);
            out.println("<tr>");
            out.println("<td>" + word + "</td>");
            out.println("<td>" + count + "</td>");
            out.println("</tr>");
            words.enqueue(word);
        }
    }

    /**
     * Output the foot html content.
     *
     * @param out
     *            Output the foot content
     */
    public static void outFoot(SimpleWriter out) {
        out.println("</table>");
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
        //Get input file name and output file name and initialize the
        //simplewirter and simeplereader
        out.println("Please enter the input file name:");
        String file = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(file);
        out.println("Please enter the output file name:");
        String outFile = in.nextLine();
        SimpleWriter fileOut = new SimpleWriter1L(outFile);
        //Print the head of html file
        printHead(fileOut, file);
        //Initialize the word Queue and wordMap
        Queue<String> words = new Queue1L<>();
        Map<String, Integer> wordMap = new Map1L<>();
        //Get all the words in the Queue and Map
        while (!fileIn.atEOS()) {
            String oneLine = fileIn.nextLine();
            //The oneLine should not be empty, otherwise the program will go wrong
            if (!oneLine.equals("")) {
                nextWordOrSeparator(oneLine, words, wordMap);
            }
        }
        //Initialize the comparator and use sort to make queue in alphabetic order
        Comparator<String> alphabet = new AlphabeticalOrder();
        words.sort(alphabet);
        //Output the entire table of words
        outTable(fileOut, words, wordMap);
        //Output the foot content of html file
        outFoot(fileOut);

        //Close all the input and output stream
        fileIn.close();
        fileOut.close();
        in.close();
        out.close();
    }

}
