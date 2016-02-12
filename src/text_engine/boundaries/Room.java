package text_engine.boundaries;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import text_engine.items.Item;

/**
 * Created by Jack on 7/6/2015.
 *
 * Represents a room.
 */
public class Room {

  public static final int CONTENT_LIMIT = 10;
  /*
  INVARIANTS:
  - `exits` can hold only one copy of a given door (all Doors in `exits` must be unique)
   */

  private final String name;
  private final ArrayList<Item> contents = new ArrayList<>();
  private final ArrayList<Door> exits = new ArrayList<>();

  /**
   * Constructs a {@link Room}, with an initial set of {@param exits}.
   *
   * @param name  The name of the room
   * @param exits The initial exits for the room
   * @throws IllegalArgumentException If the text_engine.items.Item[] is too large ({@code #size() >
   *                                  10})
   */
  public Room(@NotNull String name, Door... exits) {
    this(name);

    Collections.addAll(this.exits, exits);
  }

  /**
   * Constructs a {@link Room}, with an initial set of {@param contents}.
   *
   * @param name     The name of the room
   * @param contents The initial contents of the room
   * @throws IllegalArgumentException If the text_engine.items.Item[] is too large ({@code #size() >
   *                                  10})
   */
  public Room(@NotNull String name, Item... contents) {
    this(name);

    if (contents.length > CONTENT_LIMIT) {
      throw new IllegalArgumentException(String.format("Rooms can have a maximum of %d items.",
                                                       CONTENT_LIMIT));
    }

    Collections.addAll(this.contents, contents);
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
   * Adds new {@link Door}s as exits to {@link this} {@link Room}.
   *
   * @param exits {@link Door}s to be added
   */
  public void addExits(Door... exits) {
    Arrays.stream(exits)
          .filter((door) -> !this.exits.contains(door))
          .map(this.exits::add);
  }

  /**
   * Adds a newly generated door to {@link this} {@link Room}.
   *
   * @param locked whether the door to be added is locked
   * @return generated door, which is now attached to {@link this} {@link Room}.
   */
  public Door addExit(boolean locked) {
    Door toAdd = new Door(locked);
    toAdd.setRoom1(this);
    addExits(toAdd);
    return toAdd;
  }

  /**
   * Adds new {@link Item}s as contents to {@link this} room.
   *
   * @param contents {@link Item}s to be added
   * @throws IllegalArgumentException if the number of new, unique contents + current contents are
   *                                  more than the {@link #CONTENT_LIMIT}.
   */
  public void addContents(Item... contents) {
    Item[] cleanedContents = Arrays.stream(contents)
                                   .filter((item) -> !this.contents.contains(item))
                                   .toArray(Item[]::new);

    if (cleanedContents.length + this.contents.size() > 10) {
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
    for (Item i : this.contents) {
      result += i.toString() + "\n";
    }
    for (Door d : this.exits) {
      result += d.toString() + "\n";
    }
    return result;
  }

  /**
   * Get the name of {@link this} {@link Room}.
   *
   * @return The name of {@link this} {@link Room}
   */
  public String getName() {
    return this.name;
  }

  /**
   * Remove a door from this room. Must be called when a {@link Door} changes its
   * {@link Room}s.
   *
   * @param toRemove The {@link Door} to remove.
   */
  public void removeDoor(Door toRemove) {

    exits.remove(exits.indexOf(toRemove));

  }

  /**
   * Is this {@link Room} connected to the given one?
   *
   * @param other The {@link Room} to check
   * @return Whether the two {@link Room}s are connected
   */
  public boolean canMoveTo(Room other) {

    for (Door d : exits) {

      if (d.getOtherRoom(this).equals(other) && !d.isLocked()) {
        return true;
      }

    }

    return false;

  }

}
