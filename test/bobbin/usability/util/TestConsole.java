package bobbin.usability.util;

import bobbin.interaction.console.Console;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TestConsole implements Console {
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final PrintWriter writer = new PrintWriter(stream);
    private final BufferedReader reader;

    public ByteArrayOutputStream getStream() {
        return this.stream;
    }

    public TestConsole() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public TestConsole(BufferedUserInput input) {
        this.reader = input.build(this.stream, this.writer);
    }

    @Override
    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public PrintWriter getWriter() {
        return this.writer;
    }
}
