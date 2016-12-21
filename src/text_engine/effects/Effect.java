package text_engine.effects;

import java.io.Serializable;
import java.util.function.Consumer;

public interface Effect<T> extends Consumer<T>, Serializable {

}
