package bobbin.unit.interaction;

import bobbin.menus.MainMenu;
import bobbin.unit.BaseUnitTest;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class BaseConsoleTest extends BaseUnitTest {

    protected final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    protected final PrintWriter writer = new PrintWriter(baos);

    MainMenu mainMenu = new MainMenu();

    void assertPromptOutput(String prompt) {
        final String consoleOutput = new String(baos.toByteArray());
        Assert.assertTrue(consoleOutput.contains(prompt));
    }
}
