package bobbin.usability;

import java.io.BufferedReader;

import bobbin.interaction.ExitToException;
import bobbin.interaction.BaseInteractive;
import bobbin.unit.interaction.BaseConsoleTest;

public class BaseUsabilityTest extends BaseConsoleTest {

    protected void run(BufferedReader reader) {
        BaseInteractive next = playerCharacter;

        while (true) {
            try {
                next.interact(playerCharacter, null, reader, writer);
            }
            catch (BaseInteractive.ResetStackException e) {
                next = e.then;
            }
            catch (ExitToException e) {
                System.out.print("Test completed.");
                break;
            }
        }
    }
}
