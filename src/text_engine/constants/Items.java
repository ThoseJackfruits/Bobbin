package text_engine.constants;

import text_engine.items.Item;

public class Items {
    public static final Item BED = new Item("Bed", "Something you can sleep in.");
    public static final Item BLUEBERRY = new Item("Blueberry", "A delicious dark berry.");
    public static final Item FLOUR = new Item("Flour", "Finely-ground flour, great for baking!");
    public static final Item SUGAR = new Item("Water", "Essential for survival.");
    public static final Item WATER = new Item("Water", "Essential for survival.");

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
        }
        return null;
    }
}
