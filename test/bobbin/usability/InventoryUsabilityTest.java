package bobbin.usability;

import bobbin.usability.util.BaseUsabilityTest;
import bobbin.usability.util.BufferedUserInput;
import bobbin.usability.util.TestConsole;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

class InventoryUsabilityTest extends BaseUsabilityTest {

    @Test
    void openInventoryAndInteractWithItem() {
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendLine("new")
                .appendLine("inventory")
                .appendLine("2")
                .appendLine("back")
                .appendLine("back")
                .appendLine("main menu"));

        assertTimeout(
                Duration.ofSeconds(1),
                () -> run(console, playerCharacter));
    }
}
