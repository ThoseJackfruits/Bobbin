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
    public static void printUnorderedList(PrintWriter writer, Collection<Interactive> collection) {
        for (Interactive gameEntity : collection) {
            writer.append(Prompts.UNORDERED_LIST_PREFIX)
                  .println(gameEntity.toString());
            gameEntity.setSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing a list with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param list   for the player to choose from
     */
    public static <T extends Interactive> void printOrderedList(PrintWriter writer, List<T> list) {
        int i = 1;
        for (Interactive gameEntity : list) {
            writer.append(ORDERED_LIST_PREFIX(i))
                  .println(gameEntity.toString());
            ++i;
            gameEntity.setSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing an array with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param array  for the player to choose from
     */
    public static void printOrderedArray(PrintWriter writer, Interactive[] array) {
        printOrderedList(writer, Arrays.asList(array));
    }

    public static void print(PrintWriter writer, Interactive gameEntity) {
        writer.printf(gameEntity.toString()).flush();
        gameEntity.setSeen();
    }
}
