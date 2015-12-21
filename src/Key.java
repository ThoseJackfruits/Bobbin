import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Jack on 7/21/2015.
 *
 * Reperesents a key to one or more doors
 */
public class Key extends Item {

    private final ArrayList<Door> doors;

    /**
     * Constructs a Key object
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    Key(String name, String description, ArrayList<Door> doors) {
        super(name, description, new HashMap<String, Item>());
        this.doors = doors;
    }

    /**
     * Can this key open the given door?
     *
     * @param toOpen The door in question
     * @return Whether this key can open the given door
     */
    public boolean canOpen(Door toOpen) {
        return this.doors.contains(toOpen);
    }

    @Override
    public boolean compatible(Item other) {
        // Keys cannot combine
        return false;
    }


    /**
     * Unlock the given door. Won't work if it's an incorrect key. (duh)
     *
     * @param toOpen the door to be opened
     */
    public void unlock(Door toOpen) {
        Objects.requireNonNull(toOpen);
        if (!doors.contains(toOpen)) {
            throw new IllegalArgumentException("This key does not unlock the given door.");
        }
        toOpen.unlock();
    }
}
