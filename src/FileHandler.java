import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileHandler {

    /**
     * Данный метод получает путь до файла и возвраащет его содержимое
     * @param path Путь до файла
     * @return Содержимое файла
     * @throws IOException Ошибка во время считывания файла
     */
    static String getText(Path path) throws IOException {
        List<String> text;
        text = Files.readAllLines(path);
        return String.join("\n", text);
    }

    /**
     * Данный метод возвращает список файлов, от которых зависит данный
     * @param path Путь до файла
     * @return Список зависимотей - файлы, от которых зависит данный
     * @throws IOException Ошибка вовремя считывания файла
     */
    static List<Path> getDependencies(Path path) throws IOException {
        List<Path> dependencies = new ArrayList<Path>();
        List<String> text;
        text = Files.readAllLines(path);
        for (String s : text) {
            var parsedText = s.split(" ");
            for (int i = 0; i < parsedText.length - 1; i++) {
                if (Objects.equals(parsedText[i], "require")) {
                    if (!Files.isRegularFile(Path.of(parsedText[i + 1]))) {
                        System.out.println("Incorrect file path: " + parsedText[i + 1]);
                    } else {
                        dependencies.add(Path.of(parsedText[i + 1]));
                    }
                }
            }
        }
        return dependencies;
    }
}
