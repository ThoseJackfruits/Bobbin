package bobbin.interaction.console;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface Console {
    BufferedReader getReader();
    PrintWriter getWriter();
}
