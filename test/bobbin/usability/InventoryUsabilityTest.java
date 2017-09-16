package bobbin.usability;

import org.junit.Test;

import java.io.BufferedReader;

public class InventoryUsabilityTest extends BaseUsabilityTest {

    @Test(timeout = 1000)
    public void openInventoryAndInteractWithItem() throws Exception {
        final BufferedReader reader = new BufferedUserInput("new")
                .appendLine("inventory")
                .appendLine("1")
                .appendLine("back")
                .appendLine("back")
                .appendLine("main menu").build();
        run(reader);
    }
}
