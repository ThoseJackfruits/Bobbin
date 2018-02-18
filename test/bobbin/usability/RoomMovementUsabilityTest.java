package bobbin.usability;

import bobbin.usability.util.BaseUsabilityTest;
import bobbin.usability.util.BufferedUserInput;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;

public class RoomMovementUsabilityTest extends BaseUsabilityTest {

    @Test(timeout = 1000)
    public void moveToAnotherRoomAndLookAround() throws Exception {
        final BufferedReader reader = new BufferedUserInput()
                .appendLine("new")
                .appendLine("look")
                .addTestAction(System.out::println)
                .appendLine("open")
                .appendLine("back")
                .appendLine("main menu")
                .build(baos, writer);
        run(reader);
        Assert.assertEquals(room2, gameCharacter.getLocation());
    }
}
