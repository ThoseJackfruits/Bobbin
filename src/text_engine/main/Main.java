package text_engine.main;

import java.io.BufferedReader;
import java.io.PrintWriter;

import text_engine.characters.PlayerCharacter;
import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;

public class Main {

    public static void main(String[] args) {

    }

    private void start(BufferedReader reader, PrintWriter writer, PlayerCharacter playerCharacter) {
        Interactive next = playerCharacter;

        while (true) {
            try {
                next.interact(playerCharacter, null, reader, writer);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
            }
            catch (ExitToException e) {
                System.out.print("Exiting.");
                break;
            }
        }
    }
}
