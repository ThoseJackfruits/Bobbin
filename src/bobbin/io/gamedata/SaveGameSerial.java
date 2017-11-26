package bobbin.io.gamedata;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import bobbin.characters.PlayerCharacter;
import bobbin.io.settings.Settings;

public class SaveGameSerial extends PersistedGameDataSerial<PlayerCharacter> {

    public SaveGameSerial(@NotNull String filename) throws IOException, InterruptedException {
        super(filename);
    }

    public SaveGameSerial(@NotNull String filename, @NotNull String extension)
            throws IOException, InterruptedException {
        super(filename, extension);
    }

    @Override
    public void saveData(PlayerCharacter data) {
        super.saveData(data);
        Settings.getSettings().put(Settings.Keys.CURRENT_SAVE, getName());
        try {
            Settings.getSettings().store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveGameSerial loadActiveSave() {
        try {
            return new SaveGameSerial((String) Settings.getSettings().get(Settings.Keys.CURRENT_SAVE));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasActiveSave() {
        String currentSave = (String) Settings.getSettings().get(Settings.Keys.CURRENT_SAVE);
        return currentSave != null && !currentSave.isEmpty();
    }
}
