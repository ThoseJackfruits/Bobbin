import java.util.Objects;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents a door from one Room to another
 */
public class Door {

    private final Room room1;
    private final Room room2;
    private boolean locked;

    /**
     * Constructs a Door object
     *
     * @param room1 The room on one side of the door
     * @param room2 The room on the other side of the door
     * @param locked Whether this door is locked
     */
    Door(Room room1, Room room2, boolean locked) {
        Objects.requireNonNull(room1);
        Objects.requireNonNull(room2);
        Objects.requireNonNull(locked);

        this.room1 = room1;
        this.room2 = room2;
        this.locked = locked;
    }

    /**
     * Access the room on the other side of the door
     *
     * @param from The room you're coming from
     * @return The room on the other side
     */
    public Room getOtherRoom(Room from) {
        Objects.requireNonNull(from);
        if (!locked) {
            if (from.equals(this.room1)) {
                return this.room2;
            } else if (from.equals(this.room2)) {
                return this.room1;
            } else {
                throw new IllegalArgumentException("Given room is not connected to this door");
            }
        } else {
            throw new IllegalStateException("Door is locked.");
        }
    }

    /**
     * Generate a description of the door based on the rooms it connects.
     *
     * @return The generated description.
     */
    @Override
    public String toString() {
        String result = "Door between " + this.room1.getName() + " and " + this.room2.getName() + ".";
        if (locked) {
            result += " This door is locked.";
        }
        return result;
    }


    /**
     * Unlock this door.
     */
    public void unlock() {
        this.locked = false;
    }

}
