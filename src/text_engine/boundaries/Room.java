package text_engine.boundaries;

import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import text_engine.characters.GameCharacter;
import text_engine.characters.PlayerCharacter;
import text_engine.constants.Actions;
import text_engine.interaction.ExitToException;
import text_engine.interaction.actions.ActionList;
import text_engine.items.BaseGameEntity;
import text_engine.items.Item;

/**
 * Represents a room.
 */
public class Room extends BaseGameEntity implements Serializable {

    public class ExitToRoomException extends ExitToException {

    }

    public static final int CONTENT_LIMIT = 10;

    private final List<Item> items;
    private final Set<Door> doors;

    /**
     * Base constructor. Constructs a {@link Room}, with an initial set of items and doors.
     *
     * @param name        The name of the room
     * @param description The description of the room
     * @param items       The initial items in the room
     * @param doors       The initial doors for the room
     */
    public Room(@NotNull String name, @NotNull String description,
                @NotNull Collection<Item> items, @NotNull Collection<Door> doors)
            throws IllegalArgumentException {
        super(name, description);

        this.items = new ArrayList<>(items.size());
        for (Item i : items) {
            addItem(i);
        }

        this.doors = new HashSet<>(doors);
    }

    /**
     * Constructs a {@link Room}, with an initial set of doors.
     *
     * @param name  The name of the room
     * @param doors The initial doors for the room
     */
    public Room(@NotNull String name, @NotNull String description, Door... doors) {
        this(name, description, Collections.emptyList(), Arrays.asList(doors));
    }

    /**
     * Constructs a {@link Room}, with an initial set of items.
     *
     * @param name  The name of the room
     * @param items The initial items in the room
     * @throws IllegalArgumentException {@code items} is larger than {@value CONTENT_LIMIT}.
     */
    public Room(@NotNull String name, @NotNull String description, Item... items) {
        this(name, description, Arrays.asList(items), Collections.emptyList());
    }

    /**
     * Constructs a {@link Room}, only a name (no items or doors).
     *
     * @param name the name of the room
     */
    public Room(@NotNull String name, @NotNull String description) {
        this(name, description, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Adds new {@link Door}s as doors to this {@link Room}.
     *
     * @param doors {@link Door}s to be added
     * @return doors which have been successfully added to the room.
     */
    Door[] addDoors(Door... doors) {
        return Arrays.stream(doors).filter(this.doors::add).toArray(Door[]::new);
    }

    public Door[] getDoors() {
        return doors.toArray(new Door[doors.size()]);
    }

    /**
     * @param door the {@link Door} through which to get the next {@link Room}.
     * @return {@link Room} on the other side of the given {@link Door}.
     */
    public Room getRoomThroughDoor(Door door) throws IllegalArgumentException {
        if (doors.contains(door)) {
            return door.getOtherRoom(this);
        }

        throw new IllegalArgumentException(
                String.format("This room (%s) is not connected to the given door, (%s).",
                              toString(), door.toString()));
    }

    /**
     * Adds a new {@link Item} to this room.
     *
     * @param item {@link Item} to be added
     * @return whether or not the item was added
     * @throws IllegalStateException {@link #CONTENT_LIMIT} would be surpassed by adding the item.
     */
    public boolean addItem(Item item) {
        if (this.items.contains(item)) {
            return false;
        }
        if (this.items.size() == CONTENT_LIMIT) {
            throw new IllegalStateException(
                    String.format("Room has hit limit of %d items.", CONTENT_LIMIT));
        }

        return this.items.add(item);
    }

    /**
     * Check if free travel is permitted between this {@link Room} and the given {@link Room}.
     *
     * @param other The {@link Room} to check
     * @return Whether the two {@link Room}s are connected
     */
    public boolean canMoveTo(Room other) {
        for (Door door : doors) {
            try {
                Room fetchedOtherRoom = door.getOtherRoom(this);
                if (fetchedOtherRoom.equals(other)) {
                    return true;
                }
            }
            catch (IllegalArgumentException | IllegalStateException ignored) {
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

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from, BufferedReader reader,
                                 PrintWriter writer) {
        ActionList actions = super.actions(actor, from, reader, writer);

        for (Door door : doors) {
            actions.add(Actions.OPEN_DOOR(door));
        }

        for (Item item : items) {
            actions.add(Actions.ITEM(item));
        }

        return actions;
    }

    @Override
    protected int
    respondToInteraction(PlayerCharacter actor, BaseGameEntity from,
                         BufferedReader reader, PrintWriter writer) throws ExitToException {
        return super.respondToInteraction(actor, from, reader, writer);
    }
}
