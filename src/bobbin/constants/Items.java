package bobbin.constants;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import bobbin.effects.GameCharacterEffect;
import bobbin.io.settings.Settings;
import bobbin.items.Item;

public class Items {
    public static final Item BED = new Item(Settings.MESSAGES_BUNDLE.getString("Items.BED.name"),
                                            Settings.MESSAGES_BUNDLE.getString("Items.BED.description"));

    public static final Item BLUEBERRY =
            new Item(Settings.MESSAGES_BUNDLE.getString("Items.BLUEBERRY.name"),
                     Settings.MESSAGES_BUNDLE.getString("Items.BLUEBERRY.description"))
            .addEffect(GameCharacterEffect.NULL);

    public static final Item FLOUR = new Item(Settings.MESSAGES_BUNDLE.getString("Items.FLOUR.name"),
                                              Settings.MESSAGES_BUNDLE.getString("Items.FLOUR.description"));

    public static final Item WATER = new Item(Settings.MESSAGES_BUNDLE.getString("Items.WATER.name"),
                                              Settings.MESSAGES_BUNDLE.getString("Items.WATER.description"));

    /**
     * Get a copy of the given {@link Item}.
     *
     * The primary purpose of this method is to make it very easy to get new instances of specific
     * objects in constants classes, while keeping those constants as variables that can be statically
     * referenced at any time.
     *
     * @param item the {@link Item} to get a copy of.
     * @return copy of {@code item}.
     */
    public static Optional<Item> getCopyOf(Item item) {
        try {
            return Optional.of((Item)item.clone());
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Get copies of the given {@link Item}s.
     *
     * The primary purpose of this method is to make it very easy to get new instances of specific
     * objects in constants classes, while keeping those constants as variables that can be statically
     * referenced at any time.
     *
     * @param items the {@link Item}s to get copies of.
     * @return list of copies of {@code item}.
     */
    public static Item[] getCopiesOf(Item... items) {
        return Arrays.stream(items)
                .map(Items::getCopyOf)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Item[]::new);
    }
}
