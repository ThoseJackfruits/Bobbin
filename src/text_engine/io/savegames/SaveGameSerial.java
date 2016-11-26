package text_engine.io.savegames;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import text_engine.characters.Player;

public class SaveGameSerial extends SaveGame<Player> {

    private final File saveDir;
    private final File saveFile;

    /**
     * Construct a new savegame in the given subdirectory of the main directory.
     *
     * @param dirName subdirectory of main directory in which to save gamestates.
     */
    public SaveGameSerial(@NotNull String name, @NotNull String dirName)
            throws IOException, InterruptedException {
        super(name);
        File documents = getDocumentsFolder();

        File parent = new File(documents, "TextEngineSaves");
        saveDir = new File(parent, dirName);

        checkDir(documents);
        checkDir(parent);
        checkDir(saveDir);

        saveFile = new File(saveDir, name);
    }

    private static void checkDir(File toCheck) throws IOException {
        if (toCheck.exists()) {
            if (toCheck.isDirectory()) {
                if (!toCheck.canWrite() || !toCheck.canRead()) { // permissions issue
                    throw new IOException("Could not either read from or write to directory");
                }
            } else { // non-directory file exists at that location
                throw new IOException("File exists at directory location");
            }
        } else if (!toCheck.mkdir()) { // create save directory
            throw new IOException(String.format("Could not create directory %s\n(mkdir failed)\n",
                                                toCheck.toString()));
        }
    }

    private static File getDocumentsFolder() throws IOException, InterruptedException {
        String myDocuments = null;
        if (System.getProperty("os.name").contains("Windows")) {
            Process p = Runtime.getRuntime()
                               .exec("reg query \"HKCU\\Software\\Microsoft\\Windows"
                                     + "\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
            p.waitFor();

            InputStream in = p.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();

            myDocuments = new String(b);
            myDocuments = myDocuments.split("\\s\\s+")[4];
            return new File(myDocuments);
        } else {
            myDocuments = System.getProperty("user.home");
            return new File(myDocuments, "Documents");
        }
    }

    @Override // TODO
    public Player loadGameState() {
        FileInputStream fis;
        ObjectInputStream in;

        System.out.printf("Loading from %s\n", saveFile.toString());

        try {
            fis = new FileInputStream(new File(saveDir, getName()));
            in = new ObjectInputStream(fis);

            return (Player) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // TODO
    public void saveGameState(Player toSave) {
        FileOutputStream fos;
        ObjectOutputStream out;
        System.out.printf("Saving to %s\n", saveFile.toString());
        try {
            fos = new FileOutputStream(new File(saveDir, getName()));
            out = new ObjectOutputStream(fos);

            out.writeObject(toSave);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
