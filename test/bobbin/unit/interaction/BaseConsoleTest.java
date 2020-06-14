package bobbin.unit.interaction;

import bobbin.unit.BaseUnitTest;
import bobbin.usability.util.TestConsole;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseConsoleTest extends BaseUnitTest {

    protected final TestConsole console = new TestConsole();

    void assertPromptOutput(TestConsole console, String prompt) {
        final String consoleOutput = new String(
                Optional.of(console).orElse(this.console).getStream().toByteArray());
        assertTrue(consoleOutput.contains(prompt));
    }
}
