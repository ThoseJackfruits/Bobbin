package text_engine.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import text_engine.boundaries.Door;
import text_engine.boundaries.Room;
import text_engine.characters.PlayerCharacter;
import text_engine.constants.Items;
import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        start(reader, writer, stockGame());
    }

    private static void start(BufferedReader reader, PrintWriter writer,
                              PlayerCharacter playerCharacter) {
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

    private static PlayerCharacter stockGame() {
        Room startingRoom = new Room("Starting Room", "The room you start in",
                                     Items.getCopiesOf(Items.BLUEBERRY, Items.BED));
        Room otherRoom = new Room("Another Room", "Not the room you start in",
                                  Items.getCopiesOf(Items.FLOUR));

        Door door = new Door("A door", "Door between starting room and other room", true,
                             startingRoom, otherRoom);

        return new PlayerCharacter("The Mighty Whitey", "It's you.", startingRoom,
                                    Items.getCopyOf(Items.WATER),
                                    door.makeKey("Key to a door", "Might open something"));
    }
}
