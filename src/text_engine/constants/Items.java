package text_engine.constants;

import java.util.Arrays;
import java.util.Objects;

import text_engine.effects.GameCharacterEffect;
import text_engine.items.Item;

public class Items {
    public static final Item BED = new Item(Globals.messages.getString("Items.BED.name"),
                                            Globals.messages.getString("Items.BED.description"));

    public static final Item BLUEBERRY =
            new Item(Globals.messages.getString("Items.BLUEBERRY.name"),
                     Globals.messages.getString("Items.BLUEBERRY.description"))
            .addEffect(GameCharacterEffect.NULL);

    public static final Item FLOUR = new Item(Globals.messages.getString("Items.FLOUR.name"),
                                              Globals.messages.getString("Items.FLOUR.description"));

    public static final Item WATER = new Item(Globals.messages.getString("Items.WATER.name"),
                                              Globals.messages.getString("Items.WATER.description"));

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
    public static Item getCopyOf(Item item) {
        try {
            return (Item)item.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
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
        return Arrays.stream(items).map(Items::getCopyOf)
                     .filter(Objects::nonNull)
                     .toArray(Item[]::new);
    }
}
