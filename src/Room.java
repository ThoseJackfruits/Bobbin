import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents a room.
 */
public class Room {

    private final String name;
    private final ArrayList<Item> contents;
    private final Door[] exits;

    /**
     * Constructs a Room object
     *
     * @param name The name of the room
     * @param contents The items in the room
     * @throws IllegalArgumentException If the Item[] is too large
     */
    Room(String name, ArrayList<Item> contents, Door[] exits) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(contents);
        Objects.requireNonNull(exits);

        if (contents.size() > 10) {
            throw new IllegalArgumentException("Too many items");
        }

        this.name = name;
        this.contents = contents;
        this.exits = exits;
    }

    /**
     * Examine the room
     *
     * @return A concatenation of all items' descriptions and door locations
     */
    public String examine() {
        String result = "";
        for (Item i: this.contents) {
            result += i.toString() + "\n";
        }
        for (Door d: this.exits) {
            result += d.toString() + "\n";
        }
        return result;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return this.name;
    }

}
