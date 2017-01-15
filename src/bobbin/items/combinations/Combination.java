package bobbin.items.combinations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import bobbin.items.Item;

public class Combination extends HashSet<Item> {

    public Combination() {
    }

    public Combination(Collection<? extends Item> c) {
        super(c);
    }

    public Combination(Item... items) {
        super(Arrays.asList(items));
    }
}
