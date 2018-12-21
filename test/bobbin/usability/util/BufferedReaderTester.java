package bobbin.usability.util;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BufferedReaderTester extends BufferedReader {
    private final Map<Integer, List<Consumer<String>>> testActions;
    private final PrintWriter writer;
    private final ByteArrayOutputStream baos;
    private final String[] testInputs;
    private int lineNumber = 0;

    public BufferedReaderTester(
            BufferedUserInput in, ByteArrayOutputStream baos,
            PrintWriter writer, StringReader stringReader) {
        super(stringReader);
        this.baos = baos;
        this.writer = writer;
        testActions = in.testActions;
        testInputs = new String[ in.inputs.size() ];
        in.inputs.toArray(testInputs);
    }

    @Override
    public String readLine() throws IOException {
        testActions.getOrDefault(lineNumber, Collections.singletonList(s -> {
        }))
                   .forEach(stringConsumer -> stringConsumer.accept(baos.toString()));

        // Include inputs in the program outputs to make the output look like
        // it would were a user actually typing the commands into a terminal.
        writer.println(testInputs[ lineNumber ]);
        String input = testInputs[ lineNumber ];
        lineNumber++;
        return input;
    }
}
