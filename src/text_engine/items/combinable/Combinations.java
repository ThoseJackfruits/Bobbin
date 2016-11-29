package text_engine.items.combinable;

import java.util.HashMap;

import text_engine.items.Item;

public class Combinations extends HashMap<Combination, Item> {

    public Item get(CombinableItem... items) {
        return super.get(new Combination(items));
    }
}
