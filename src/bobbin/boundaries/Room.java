package bobbin.boundaries;

import bobbin.characters.GameCharacter;
import bobbin.characters.NonPlayerCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.ExitToException;
import bobbin.interaction.actions.ActionList;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a room.
 */
public class Room extends BaseGameEntity implements Serializable {

    public static class ExitToRoomException extends ExitToException {

    }

    public static final int CONTENT_LIMIT = 10;

    private final List<Item> items;
    private final Set<Door> doors;
    private final Set<NonPlayerCharacter> nonPlayerCharacters = new HashSet<>();

    /**
     * Base constructor. Constructs a {@link Room}, with an initial set of items and doors.
     *
     * @param name        The name of the room
     * @param description The description of the room
     * @param items       The initial items in the room
     * @param doors       The initial doors for the room
     */
    public Room(
            @NotNull String name, @NotNull String description,
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
        return doors.toArray(new Door[ doors.size() ]);
    }

    public boolean addNPC(NonPlayerCharacter nonPlayerCharacter) {
        return nonPlayerCharacters.add(nonPlayerCharacter);
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
     * Removes an {@link Item} from this room.
     *
     * @param item {@link Item} to be taken
     **/
    public Item takeItem(Item item) {
        if (!this.items.contains(item)) {
            throw new IllegalArgumentException(String.format("%s was not found in %s", item, this));
        }

        if (!this.items.remove(item)) {
            throw new IllegalArgumentException(String.format("%s could not be removed from %s",
                                                             item, this));
        }

        return item;
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
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = new ActionList();
        actions.add(Actions.BACK(actor));

        for (Door door : doors) {
            actions.add(Actions.OPEN_DOOR(door));
        }

        for (Item item : items) {
            actions.add(Actions.ITEM(item));
        }

        for (NonPlayerCharacter npc : nonPlayerCharacters) {
            actions.add(Actions.CONVERSE(npc));
        }

        return actions;
    }

    @Override
    public int
    respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from,
            BufferedReader reader, PrintWriter writer) throws ExitToException {
        try {
            return super.respondToInteraction(actor, from, reader, writer);
        } catch (ExitToRoomException e) {
            return GoTo.THIS;
        }
    }
}
