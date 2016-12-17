package text_engine.interaction;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

/**
 * Primary method of printing to the player console.
 */
public class Printers {

    /**
     * Protect constructor, since it is a static only class
     */
    protected Printers() {
    }

    private static final String UNORDERED_LIST_PREFIX = "- ";
    private static final String ORDERED_LIST_PREFIX = "%d. ";

    private static String ORDERED_LIST_PREFIX(int i) {
        return String.format(ORDERED_LIST_PREFIX, i);
    }

    public static void printUnorderedList(PrintWriter writer, Collection collection) {
        for (Object o : collection) {
            writer.append(UNORDERED_LIST_PREFIX)
                  .println(o.toString());
        }
        writer.flush();
    }

    public static void printOrderedList(PrintWriter writer, List list) {
        int i = 1;
        for (Object o : list) {
            writer.append(ORDERED_LIST_PREFIX(i))
                  .println(o.toString());
            ++i;
        }
        writer.flush();
    }

    public static void print(PrintWriter writer, Object o) {
        writer.printf(o.toString()).flush();
    }
}
