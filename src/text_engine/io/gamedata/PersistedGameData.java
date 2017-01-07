package text_engine.io.gamedata;

import com.sun.istack.internal.NotNull;

import java.util.Date;

import text_engine.items.BaseGameEntity;

public abstract class PersistedGameData<T> extends BaseGameEntity {

    public PersistedGameData(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    /**
     * Creates a new {@link PersistedGameData} with the given name and the current time and date in
     * the description.
     *
     * @param name how the {@link PersistedGameData} will be referred to or saved as, depending on the
     *             method of persistence
     */
    public PersistedGameData(@NotNull String name) {
        super(name, new Date().toString());
    }

    /**
     * Creates a new {@link PersistedGameData} with no name and the current time and date in the
     * description.
     */
    public PersistedGameData() {
        this("");
    }

    public abstract T loadData();

    public abstract void saveData(T data);
}
