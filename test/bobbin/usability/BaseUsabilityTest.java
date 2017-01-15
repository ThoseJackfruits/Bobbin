package bobbin.usability;

import java.io.BufferedReader;

import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.unit.interaction.BaseConsoleTest;

public class BaseUsabilityTest extends BaseConsoleTest {

    protected void run(BufferedReader reader) {
        Interactive next = playerCharacter;

        while (true) {
            try {
                next.interact(playerCharacter, null, reader, writer);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
            }
            catch (ExitToException e) {
                System.out.print("Test completed.");
                break;
            }
        }
    }
}
