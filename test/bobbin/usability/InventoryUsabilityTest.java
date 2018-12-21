package bobbin.usability;

import bobbin.usability.util.BaseUsabilityTest;
import bobbin.usability.util.BufferedUserInput;
import org.junit.Test;

import java.io.BufferedReader;

public class InventoryUsabilityTest extends BaseUsabilityTest {

    @Test(timeout = 1000)
    public void openInventoryAndInteractWithItem() {
        final BufferedReader reader = new BufferedUserInput()
                .appendLine("new")
                .appendLine("inventory")
                .addTestAction(System.out::println)
                .appendLine("2")
                .appendLine("back")
                .appendLine("back")
                .appendLine("main menu")
                .build(baos, writer);
        run(reader);
    }
}
