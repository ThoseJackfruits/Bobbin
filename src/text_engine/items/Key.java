package text_engine.items;

import java.util.Objects;
import java.util.Random;

import text_engine.boundaries.Door;

/**
 * Represents a key to one or more doors
 */
public class Key extends Item {

    private final int id;

    /**
     * Constructs a {@link Key} object with a random {@link #id}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     */
    public Key(String name, String description) {
        super(name, description);

        id = new Random().nextInt();
    }

    /**
     * Constructs a {@link Key} object with a set {@link #id}.
     *
     * @param name        The name of the object
     * @param description The description of the object
     * @param id          identifier for the key (decides what it fits)
     */
    public Key(String name, String description, int id) {
        // super call performs null checks
        super(name, description);

        this.id = id;
    }


    /**
     * Can this {@link Key} unlock the given {@link Door}?
     *
     * @param toOpen The {@link Door} to unlock.
     * @return Whether this {@link Key} can open the given {@link Door}
     */
    public boolean fits(Door toOpen) {
        return toOpen.fits(this);
    }

    /**
     * Unlock the given {@link Door}. Won't work if it's an incorrect {@link Key}.
     *
     * @param toOpen the {@link Door} to be opened
     * @return {@code true} if successfully unlocked, {@code false} otherwise
     */
    public boolean unlock(Door toOpen) {
        Objects.requireNonNull(toOpen);
        return toOpen.unlock(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Key other = (Key) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

