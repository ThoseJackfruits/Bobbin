package bobbin.interaction.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SystemConsole implements Console {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final PrintWriter writer = new PrintWriter(System.out);

    @Override
    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public PrintWriter getWriter() {
        return this.writer;
    }
}
