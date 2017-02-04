package bobbin.menus;

import com.sun.istack.internal.NotNull;

import bobbin.items.BaseGameEntity;

public class Menu extends BaseGameEntity {

    public Menu() {
        super();
    }

    public Menu(@NotNull String name, @NotNull String description) {
        super(name, description);
    }
}
