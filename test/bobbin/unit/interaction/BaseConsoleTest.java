package bobbin.unit.interaction;

import bobbin.unit.BaseUnitTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseConsoleTest extends BaseUnitTest {

    protected final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    protected final PrintWriter writer = new PrintWriter(baos);

    void assertPromptOutput(String prompt) {
        final String consoleOutput = new String(baos.toByteArray());
        assertTrue(consoleOutput.contains(prompt));
    }
}
