package text_engine.boundaries;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import text_engine.items.Item;

/**
 * Represents a room.
 */
public class Room implements Serializable {

    public static final int CONTENT_LIMIT = 10;
  /*
  INVARIANTS:
  - `exits` can hold only one copy of a given door (all Doors in `exits` must be unique)
   */

    private final String name;
    private final ArrayList<Item> contents = new ArrayList<>();
    private final Set<Door> exits = new HashSet<>();

    /**
     * Constructs a {@link Room}, with an initial set of exits.
     *
     * @param name  The name of the room
     * @param exits The initial exits for the room
     */
    public Room(@NotNull String name, Door... exits) {
        this(name);

        Collections.addAll(this.exits, exits);
    }

    /**
     * Constructs a {@link Room}, with an initial set of items.
     *
     * @param name     The name of the room
     * @param items The initial items in the room
     * @throws IllegalArgumentException {@code items} is larger than {@value CONTENT_LIMIT}.
     */
    public Room(@NotNull String name, Item... items) {
        this(name);

        if (items.length > CONTENT_LIMIT) {
            throw new IllegalArgumentException(String.format("Rooms can have a maximum of %d items.",
                                                             CONTENT_LIMIT));
        }

        Collections.addAll(this.contents, items);
    }

    /**
     * Constructs a {@link Room}, only a name (no contents or doors).
     *
     * @param name the name of the room
     */
    public Room(@NotNull String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    /**
     * Adds new {@link Door}s as exits to this {@link Room}.
     *
     * @param exits {@link Door}s to be added
     * @return doors which have been successfully added to the room.
     */
    Door[] addExits(Door... exits) {
        Door[] newDoors = Arrays.stream(exits)
                                .filter((door) -> !this.exits.contains(door)).toArray(Door[]::new);
        Collections.addAll(this.exits, newDoors);
        return newDoors;
    }

    public Door[] getExits() {
        return exits.toArray(new Door[exits.size()]);
    }

    /**
     * @param door the {@link Door} through which to get the next {@link Room}.
     * @return {@link Room} on the other side of the given {@link Door}.
     */
    public Room getRoomThroughDoor(Door door) {
        if (exits.contains(door)) {
            return door.getOtherRoom(this);
        }

        throw new IllegalArgumentException(
                String.format("This room (%s) is not connected to the given door, (%s).",
                              toString(), door.toString()));
    }

    /**
     * Adds new {@link Item}s as contents to this room.
     *
     * @param contents {@link Item}s to be added
     * @throws IllegalArgumentException if the number of new, unique contents + current contents are
     *                                  more than the {@link #CONTENT_LIMIT}.
     */
    public void addContents(Item... contents) {
        Item[] cleanedContents = Arrays.stream(contents)
                                       .filter((item) -> !this.contents.contains(item))
                                       .toArray(Item[]::new);

        if (cleanedContents.length + this.contents.size() > CONTENT_LIMIT) {
            throw new IllegalArgumentException(String.format("Rooms can have a maximum of %d items.",
                                                             CONTENT_LIMIT));
        }

        Collections.addAll(this.contents, cleanedContents);
    }

    /**
     * Examine the room, collecting all available objects into a string.
     *
     * @return A concatenation of all items' descriptions and door locations
     */
    public String examine() {
        String result = "";
        for (Item item : this.contents) {
            result += item.toString() + "\n";
        }
        for (Door door : this.exits) {
            result += door.toString() + "\n";
        }
        return result;
    }

    /**
     * Get the name of this {@link Room}.
     *
     * @return The name of this {@link Room}
     */
    public String getName() {
        return this.name;
    }

    /**
     * Remove a door from this room. Must be called when a {@link Door} changes its {@link Room}s.
     *
     * @param toRemove The {@link Door} to remove.
     */
    void removeExit(Door toRemove) {
        exits.remove(toRemove);
    }

    /**
     * Check if free travel is permitted between this {@link Room} and the given {@link Room}.
     *
     * @param other The {@link Room} to check
     * @return Whether the two {@link Room}s are connected
     */
    public boolean canMoveTo(Room other) {
        for (Door door : exits) {
            if (door.getOtherRoom(this).equals(other) && !door.isLocked()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Room room = (Room) obj;
        return getName().equals(room.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
