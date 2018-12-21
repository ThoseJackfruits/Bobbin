package bobbin.items.combinations;

import bobbin.items.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

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
