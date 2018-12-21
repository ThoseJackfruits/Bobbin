package bobbin.menus;

import bobbin.items.BaseGameEntity;

import javax.validation.constraints.NotNull;

public class Menu extends BaseGameEntity {

    public Menu() {
        super();
    }

    public Menu(@NotNull String name, @NotNull String description) {
        super(name, description);
    }
}
