package text_engine.items.combinable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Combination extends HashSet<CombinableItem> {

    public Combination() {
    }

    public Combination(Collection<? extends CombinableItem> c) {
        super(c);
    }

    public Combination(CombinableItem... items) {
        super(Arrays.asList(items));
    }
}
