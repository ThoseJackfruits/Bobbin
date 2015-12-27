import java.util.Objects;
import java.util.Random;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents a door from one Room to another
 */
public class Door {

    private final Room room1;
    private final Room room2;
    private boolean locked;
    private final long lock;

    /**
     * Constructs a Door object
     *
     * @param room1 The room on one side of the {@link Door}
     * @param room2 The room on the other side of the {@link Door}
     * @param locked Whether this {@link Door} is locked
     */
    Door(Room room1, Room room2, boolean locked) {
        Objects.requireNonNull(room1);
        Objects.requireNonNull(room2);

        this.room1 = room1;
        this.room2 = room2;
        this.locked = locked;

        lock = new Random().nextLong();
    }

    /**
     * Access the room on the other side of the {@link Door}
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
     * Generate a description of the {@link Door} based on the rooms it connects.
     *
     * @return The generated description.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Door between ")
          .append(room1.getName())
          .append(" and ")
          .append(room2.getName())
          .append(".");
        if (locked) {
            result.append(" This door is locked.");
        }

        return result.toString();
    }


    /**
     * Tries to unlock the {@link Door} with the given {@link Key}.
     *
     * @param key the key used to unlock the {@link Door}
     * @return {@code true} if successfully unlocked, {@code false} otherwise
     */
    public boolean unlock(Key key) {
        if (fits(key)) {
            locked = false;
            return true;
        }
        return false;
    }

    /**
     * Makes a key that fits this {@link Door};
     *
     * @param name name of the {@link Key}
     * @return key that fits this {@link Door}
     */
    public Key makeKey(String name, String description) {
        return new Key(name, description, Long.hashCode(lock));
    }

    public boolean fits(Key key) {
        return key.hashCode() == Long.hashCode(lock);
    }
}
