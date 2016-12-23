package text_engine.interaction;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import text_engine.constants.Prompts;

/**
 * Primary method of printing to the player console.
 */
public class Printers {

    /**
     * Protect constructor, since it is a static only class
     */
    protected Printers() {
    }

    private static String ORDERED_LIST_PREFIX(int i) {
        return String.format(Prompts.ORDERED_LIST_PREFIX, i);
    }

    /**
     * Standard method of printing multiple items where order is not important.
     *
     * Should <i>not</i> be used when the player needs to make a selection from the list. Use {@link
     * #printOrderedList(PrintWriter, List)} instead.
     *
     * @param writer     to print prompt to
     * @param collection for the player to choose from
     */
    public static void printUnorderedList(PrintWriter writer, Collection collection) {
        for (Object o : collection) {
            writer.append(Prompts.UNORDERED_LIST_PREFIX)
                  .println(o.toString());
        }
        writer.flush();
    }

    /**
     * Standard method of printing a list with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param list   for the player to choose from
     */
    public static void printOrderedList(PrintWriter writer, List list) {
        int i = 1;
        for (Object o : list) {
            writer.append(ORDERED_LIST_PREFIX(i))
                  .println(o.toString());
            ++i;
        }
        writer.flush();
    }

    /**
     * Standard method of printing an array with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param array  for the player to choose from
     */
    public static void printOrderedArray(PrintWriter writer, Object[] array) {
        printOrderedList(writer, Arrays.asList(array));
    }

    public static void print(PrintWriter writer, Object o) {
        writer.printf(o.toString()).flush();
    }
}
