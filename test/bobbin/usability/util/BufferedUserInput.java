package bobbin.usability.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BufferedUserInput {

    final Deque<String> inputs = new ArrayDeque<>();

    final Map<Integer, List<Consumer<String>>> testActions = new HashMap<>();

    /**
     * Initialise a new {@link BufferedUserInput}
     */
    public BufferedUserInput() {
    }

    /**
     * Append a string to the user input.
     *
     * @param s {@link String} to be appended
     * @return {@code this}
     */
    private BufferedUserInput append(String s) {
        inputs.add(inputs.isEmpty() ? s : inputs.removeLast().concat(s));
        return this;
    }

    /**
     * Append an {@code int} to the user input.
     *
     * @param i {@code int} to be appended
     * @return {@code this}
     */
    private BufferedUserInput append(int i) {
        return append(Integer.toString(i));
    }

    /**
     * Append a platform-dependent newline character sequence to the user input.
     *
     * @return {@code this}
     */
    public BufferedUserInput newLine() {
        inputs.add("");
        return this;
    }

    /**
     * Append a string followed by a platform-dependent newline character sequence.
     *
     * @param s {@link String} to be appended
     * @return {@code this}
     */
    public BufferedUserInput appendLine(String s) {
        return append(s).newLine();
    }

    /**
     * Append an {@code int} followed by a platform-dependent newline character sequence.
     *
     * @param i {@code int} to be appended
     * @return {@code this}
     */
    public BufferedUserInput appendLine(int i) {
        return append(i).newLine();
    }

    /**
     * Append a {@link Collection} of user inputs.
     *
     * @param list user inputs to append
     * @return {@code this}
     */
    public BufferedUserInput appendAllOnNewLines(Collection<String> list) {
        for (String s : list) {
            appendLine(s);
        }
        return this;
    }

    /**
     * Append an array of user inputs.
     *
     * @param list user inputs to append
     * @return {@code this}
     */
    public BufferedUserInput appendAllOnNewLines(String... list) {
        return appendAllOnNewLines(Arrays.asList(list));
    }

    /**
     * Add a test action, to be performed before the next line of input is
     * read. This implies that any test actions added after the last line of
     * input will never be run. This is considered acceptable because at that
     * point, control has been given back to whatever function is running the
     * tests, where the desired test actions can be performed.
     * <p>
     * This allows for testing of program output during individual steps of
     * mocked user input, which makes for much easier and more powerful
     * testing, as well as better insight into why a particular usability test
     * is failing. For example, passing in {@code System.out::println} will
     * print all of the output up to that point.
     *
     * @param outputConsumer a consumer for running tests on the output of the program
     * @return {@code this}
     */
    public BufferedUserInput addTestAction(Consumer<String> outputConsumer) {
        int key = inputs.size() - 1;
        testActions.putIfAbsent(key, new ArrayList<>());
        testActions.get(key).add(outputConsumer);
        return this;
    }

    /**
     * Build a {@link BufferedReader} from {@code this}.
     *
     * @return {@link BufferedReader} whose contents include what has been added to {@code this}
     */
    public BufferedReaderTester build(ByteArrayOutputStream stream, PrintWriter writer) {
        final String newLine = System.lineSeparator();
        return new BufferedReaderTester(this, stream, writer, new StringReader(
                inputs.stream().collect(Collectors.joining(newLine, "", newLine))));
    }
}
