package text_engine.items.combinations;

import java.util.Collection;
import java.util.HashMap;

import text_engine.items.Item;

public class Combinations extends HashMap<Combination, Item> {
    public Item get(Item... items) {
        return super.get(new Combination(items));
    }

    public Item get(Collection<Item> items) {
        return super.get(new Combination(items));
    }
}
