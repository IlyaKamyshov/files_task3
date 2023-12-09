import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        //1. Произвести распаковку архива в папке savegames.
        String archive = "/Users/kames/Desktop/games/savegames/archive.zip";
        String pathToUnarchive = "/Users/kames/Desktop/games/savegames/";

        opnenZip(archive, pathToUnarchive);

        //2. Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
        //3. Вывести в консоль состояние сохранненой игры.
        String save1 = "/Users/kames/Desktop/games/savegames/save1.dat";
        String save2 = "/Users/kames/Desktop/games/savegames/save2.dat";
        String save3 = "/Users/kames/Desktop/games/savegames/save3.dat";

        System.out.println(openProgress(save2));

    }

    public static void opnenZip(String archive, String pathToUnarchive) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                // получим название файла
                name = entry.getName();
                // распаковка
                FileOutputStream fout = new FileOutputStream(pathToUnarchive + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String save) {

        GameProgress gameProgress = null;

        // откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream(save); ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

}