package text_engine.boundaries;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

import text_engine.items.Key;

/**
 * Represents a door from one {@link Door} to another
 */
public class Door implements Serializable {

  private final long lock;
  private Room room1;
  private Room room2;
  private boolean locked;

  /**
   * Constructs a {@link Door} between the 2 provided rooms.
   *
   * @param locked whether this {@link Door} is locked
   * @param room1  the room on one side of the {@link Door}
   * @param room2  the room on the other side of the {@link Door}
   */
  public Door(boolean locked, Room room1, Room room2) {
    this(locked);
    Objects.requireNonNull(room1);
    Objects.requireNonNull(room2);

    room1.addExits(this);
    this.room1 = room1;

    room2.addExits(this);
    this.room2 = room2;
  }

  /**
   * Construct a new {@link Door} without any room associations.
   *
   * @param locked whether this {@link Door} is locked
   */
  public Door(boolean locked) {
    this.locked = locked;

    lock = new Random().nextLong();
  }


  /**
   * Set the first {@link Room} that this door is connected to.
   *
   * @param room1 the room on one side of the {@link Door}
   */
  public void setRoom1(@NotNull Room room1) {
    Objects.requireNonNull(room1);
    this.room1.removeExit(this);
    room1.addExits(this);
    this.room1 = room1;
  }


  /**
   * Set the first {@link Room} that this door is connected to.
   *
   * @param room2 the room on the other side of the {@link Door}
   */
  public void setRoom2(@NotNull Room room2) {
    Objects.requireNonNull(room2);
    this.room2.removeExit(this);
    room2.addExits(this);
    this.room2 = room2;
  }

  /**
   * Set both {@link Room}s that this door is connected to.
   *
   * @param room1 the room on one side of the {@link Door}
   * @param room2 the room on the other side of the {@link Door}
   */
  public void setRooms(@NotNull Room room1, @NotNull Room room2) {
    setRoom1(room1);
    setRoom2(room2);
  }

  /**
   * Access the room on the other side of the {@link Door}
   *
   * @param from the room you're coming from
   * @return the room on the other side
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
      result.append(" (locked)");
    }

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
   * @param key        the key used to lock the {@link Door}
   * @param toBeLocked whether the door is to be locked or unlocked
   * @return {@code true} if successfully lock, {@code false} otherwise
   */
  public boolean setLocked(boolean toBeLocked, Key key) {
    if (fits(key)) {
      this.locked = toBeLocked;
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

  public boolean isLocked() {
    return locked;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Door door = (Door) obj;
    return toString().equals(door.toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
