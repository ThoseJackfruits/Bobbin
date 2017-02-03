package bobbin.boundaries;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Random;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.items.BaseGameEntity;
import bobbin.items.Key;

/**
 * Represents a door from one {@link Door} to another
 */
public class Door extends BaseGameEntity {

    public class ExitToDoorException extends ExitToException {

    }

    private final long lock;
    private final Room room1;
    private final Room room2;
    private boolean locked;

    /**
     * Constructs a {@link Door} between the 2 provided rooms.
     *
     * @param name        The name of the door
     * @param description The description of the door
     * @param locked      whether this {@link Door} is locked
     * @param room1       the room on one side of the {@link Door}
     * @param room2       the room on the other side of the {@link Door}
     */
    public Door(String name, String description, boolean locked, Room room1, Room room2) {
        super(name, description);
        this.locked = locked;
        lock = new Random().nextLong();
        Objects.requireNonNull(room1);
        Objects.requireNonNull(room2);

        this.room1 = room1;
        this.room2 = room2;

        room1.addDoors(this);
        room2.addDoors(this);
    }

    /**
     * Constructs a {@link Door} between the 2 provided rooms, with no name or description
     *
     * @param locked whether this {@link Door} is locked
     * @param room1  the room on one side of the {@link Door}
     * @param room2  the room on the other side of the {@link Door}
     */
    public Door(boolean locked, Room room1, Room room2) {
        this("", "", locked, room1, room2);
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }

    /**
     * Access the room on the other side of the {@link Door}
     *
     * @param from the room you're coming from
     * @return the room on the other side
     * @throws IllegalStateException    door is locked
     * @throws IllegalArgumentException given room is not connected to this door
     */
    public Room getOtherRoom(Room from) throws IllegalArgumentException, IllegalStateException {
        Objects.requireNonNull(from);

        if (from != room1 && from != room2) {
            throw new IllegalArgumentException("Given room is not connected to this door");
        }

        if (locked) {
            throw new IllegalStateException("Door is locked.");
        }

        return from.equals(room1) ? room2 : room1;
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
                .append(room2.getName());

        if (locked) {
            result.append(" (locked)");
        }
        result.append(".");

        return result.toString();
    }


    /**
     * Tries to unlock this {@link Door} with the given {@link Key}.
     *
     * @param key the key used to unlock the {@link Door}
     * @return {@code true} if successfully unlocked, {@code false} otherwise
     */
    public boolean unlock(Key key) {
        return setLocked(false, key);
    }

    /**
     * Tries to lock this {@link Door} with the given {@link Key}.
     *
     * @param key the key used to lock the {@link Door}
     * @return {@code true} if successfully lock, {@code false} otherwise
     */
    public boolean lock(Key key) {
        return setLocked(true, key);
    }

    /**
     * Tries to change the lock of this {@link Door} with the given {@link Key}.
     *
     * @param key    the key used to lock the {@link Door}
     * @param locked whether the door is to be locked or unlocked
     * @return {@link #isLocked()}
     */
    public boolean setLocked(boolean locked, Key key) {
        if (locked != this.locked && fits(key)) {
            this.locked = locked;
        }
        return isLocked();
    }

    /**
     * Makes a key that fits this {@link Door};
     *
     * @param name        name of the {@link Key}
     * @param description description of the {@link Key}
     * @return key that fits this {@link Door}
     */
    public Key makeKey(String name, String description) {
        return new Key(name, description, Long.hashCode(lock));
    }

    public boolean fits(Key key) {
        return Objects.requireNonNull(key).hashCode() == Long.hashCode(lock);
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean equals(Object obj) {
        // Basing equality on hashCode(), which is a little dirty, but it's the
        // comparison to make in this situation.
        return (this == obj) || (obj != null && getClass() == obj.getClass()
                                 && hashCode() == obj.hashCode());
    }

    @Override
    public int hashCode() {
        return (room1 != null ? room1.hashCode() : 0)
               + (room2 != null ? room2.hashCode() : 0);
    }

    @Override
    protected int respondToInteraction(PlayerCharacter actor, BaseGameEntity from,
                                       BufferedReader reader, PrintWriter writer)
            throws ExitToException {
        try {
            getOtherRoom((Room) from).resetStackAndInteract();
        }
        catch (IllegalStateException e) {
            if (actor.getInventory().hasKeyThatMatches(item -> !unlock((Key) item))) {
                getOtherRoom((Room) from).resetStackAndInteract();
            }
        }
        return GoTo.PARENT;
    }
}
