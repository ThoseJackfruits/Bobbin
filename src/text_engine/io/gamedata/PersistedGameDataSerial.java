package text_engine.io.gamedata;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import text_engine.constants.Globals;

public class PersistedGameDataSerial<T> extends PersistedGameData<T> {

    /**
     * Construct a new savegame in the given subdirectory of the main directory.
     *
     * @param filename filename of the file
     * @throws IOException          if the file cannot be written to, for a variety of reasons
     * @throws InterruptedException if the base folder could not be fetched from the OS
     */
    public PersistedGameDataSerial(@NotNull String filename)
            throws IOException, InterruptedException {
        super(filename);
        File documents = getDocumentsFolder();
        checkDirectory(documents);

        directory = new File(documents, Globals.messages.getString("Persisted.baseFolder"));
        checkDirectory(directory);
    }

    private final File directory;

    private File file() {
        return new File(directory, getName());
    }

    @Override
    public void setName(@NotNull String name) {
        super.setName(name);
        // TODO change file name (delete old and create new, or move existing)
    }

    protected static void checkDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                if (!directory.canWrite() || !directory.canRead()) { // permissions issue
                    throw new IOException("Could not either read from or write to directory");
                }
            }
            else { // non-directory file exists at that location
                throw new IOException("File exists at directory location");
            }
        }
        else if (!directory.mkdir()) { // create save directory
            throw new IOException(String.format("Could not create directory %s\n(mkdir failed)\n",
                                                directory.toString()));
        }
    }

    protected static File getDocumentsFolder() throws IOException, InterruptedException {
        if (System.getProperty("os.name").contains("Windows")) {
            // I would love to find a better way to get "My Documents" on
            // Windows. According to some StackOverflow post,
            // System.getProperty("user.home") doesn't work like it does on
            // macOS & GNU/Linux or (more likely) the My Documents folder is
            // not really "in" the user's home directory because of the weird
            // way that Windows sets up My Documents and the like.

            // There may also be some more pleasant cross-platform thing that
            // Java provides to simply get at a standard "library" folder that
            // programs generally use to store persisted data. I know that
            // Windows is generally not great at this, because it doesn't
            // really have a standard on that folder. A lot of programs use
            // the folder they are installed to (looking at you, Steam) or,
            // pretty commonly, games will use the "My Documents" folder
            // (which is why I chose it in the first place).

            Process p = Runtime.getRuntime()
                               .exec("reg query \"HKCU\\Software\\Microsoft\\Windows"
                                     + "\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
            p.waitFor();

            InputStream in = p.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();

            String myDocuments = new String(b).split("\\s\\s+")[4];
            return new File(myDocuments);
        }
        else {
            // TODO i18n "Documents"? Does that change by language?
            return new File(System.getProperty("user.home"), "Documents");
        }
    }

    public FileOutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file());
    }

    public FileInputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file());
    }

    @Override
    public T loadData() {
        try {
            checkDirectory(directory);
            ObjectInputStream in = new ObjectInputStream(getInputStream());

            //noinspection unchecked
            T object = (T) in.readObject();
            in.close();
            return object;
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveData(T data) {
        try {
            checkDirectory(directory);
            ObjectOutputStream out = new ObjectOutputStream(getOutputStream());

            out.writeObject(data);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
