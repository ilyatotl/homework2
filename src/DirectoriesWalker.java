import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoriesWalker {

    Map<Path, List<Path>> dependencies = new HashMap<>();
    List<Path> fileNames = new ArrayList<>();
    private final Map<Path, String> text = new HashMap<Path, String>();

    String getText(Path path) {
        return text.get(path);
    }

    /**
     * Даннйы метод осуществляет обход директорий.
     * Если текщий путь - директория, запустить обход для всех ее поддиректорийю
     * Если текущий путь файл - вызвать метод processFile для него.
     * @param path Путь до файла
     */
    void bypass(Path path) {
        if (Files.isDirectory(path)) {
            try {
                DirectoryStream<Path> files = Files.newDirectoryStream(path);
                for (Path curDir : files) {
                    bypass(curDir);
                }
                files.close();
            } catch (IOException ex) {
                System.out.println("Error: " + ex + "during the passage of the directory: " + path);
            }
            return;
        }
        fileNames.add(path);
        processFile(path);
    }

    /**
     * Данный метод получает на вход путь до файла и обновляет информацию об этом файле
     * (зависимости и текст) в словарях.
     * @param path Путь до файла
     */
    void processFile(Path path) {
        try {
            text.put(path, FileHandler.getText(path));
            dependencies.put(path, FileHandler.getDependencies(path));
        } catch (IOException ex) {
            System.out.println("Error: " + ex + " during reading file: " + path);
        }
    }
}
