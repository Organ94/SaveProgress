import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String PATH = "D://Alexey/JavaCore/Games/savegames";

    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(100, 5, 1, 1.0);
        GameProgress game2 = new GameProgress(90, 4, 4, 5.7);
        GameProgress game3 = new GameProgress(71, 2, 7, 15.2);

        saveGame(PATH + "/savegame1.dat", game1);
        saveGame(PATH + "/savegame1.1.dat", game1);
        saveGame(PATH + "/savegame2.dat", game2);
        saveGame(PATH + "/savegame2.1.dat", game2);
        saveGame(PATH + "/savegame3.dat", game3);
        saveGame(PATH + "/savegame3.1.dat", game3);

        writeZip(PATH + "/savegame.zip", Arrays.asList(PATH + "/savegame1.dat",
                PATH + "/savegame2.dat",
                PATH + "/savegame3.dat"));

        removeNotZip(PATH);
    }

    private static void saveGame(String path, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeZip(String path, List<String> pathFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String file : pathFile) {
                File files = new File(file);
                try (FileInputStream fis = new FileInputStream(files)) {
                    ZipEntry entry = new ZipEntry(files.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeNotZip(String path) {
        Arrays.stream(new File(path).listFiles())
                .filter(iten -> !iten.getName().endsWith("zip"))
                .forEach(File::delete);
    }
}
