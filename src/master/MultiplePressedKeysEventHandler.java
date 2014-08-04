package master;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by muhd7rosli on 8/5/2014.
 */
public class MultiplePressedKeysEventHandler implements EventHandler<KeyEvent> {

    private final Set<KeyCode> buffer = EnumSet.noneOf(KeyCode.class);
    private final MultiKeyEvent multiKeyEvent = new MultiKeyEvent();

    private final MultiKeyEventHandler multiKeyEventHandler;

    public MultiplePressedKeysEventHandler(final MultiKeyEventHandler handler) {
        this.multiKeyEventHandler = handler;
    }

    @Override
    public void handle(final KeyEvent event) {
        final KeyCode code = event.getCode();

        if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
            buffer.add(code);
            multiKeyEventHandler.handle(multiKeyEvent);
        } else if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
            buffer.remove(code);
        }
        event.consume();
    }

    public interface MultiKeyEventHandler {
        void handle(final MultiKeyEvent event);
    }

    public class MultiKeyEvent {
        public boolean isPressed(final KeyCode key) {
            return buffer.contains(key);
        }
    }

}
