package text_engine.io.gamedata;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import text_engine.characters.PlayerCharacter;

import java.io.*;

public class SaveGameJSON extends SaveGameSerial {

    private Gson gson = new Gson();

    public SaveGameJSON(@NotNull String filename) throws IOException, InterruptedException {
        super(filename, "json");
    }

    @Override
    public PlayerCharacter loadData() {
        try {
            ObjectInputStream in = new ObjectInputStream(getInputStream());
            return new Gson().fromJson(in.readUTF(), PlayerCharacter.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveData(PlayerCharacter data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(getOutputStream());
            out.writeUTF(gson.toJson(data, PlayerCharacter.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
