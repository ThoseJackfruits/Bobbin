package bobbin.items.combinations;

import java.util.Collection;
import java.util.HashMap;

import bobbin.items.Item;

public class Combinations extends HashMap<Combination, Item> {
    public Item get(Item... items) {
        return super.get(new Combination(items));
    }

    public Item get(Collection<Item> items) {
        return super.get(new Combination(items));
    }
}
