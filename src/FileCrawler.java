import java.nio.file.Path;
import java.util.*;

public class FileCrawler {
    private final Map<Path, List<Path>> dependencies;
    Map<Path, Integer> used;
    List<Path> resultList = new ArrayList<>();
    private Path lastInCycle;
    Boolean needToAdd = false;

    FileCrawler(Map<Path, List<Path>> dependencies) {
        this.dependencies = dependencies;
        used = new TreeMap<>();
    }

    /**
     * Данный метод осуцщетсвляет обход файлов, в соответсвии с зависимостями.
     * В resultList он записывает результат упорядочивания файлов.
     * @param path Путь до фалйа
     * @return True - если циклы не обнаружены, False иначе
     */
    Boolean dfs(Path path) {
        used.put(path, 1);
        for (Path dep : dependencies.get(path)) {
            if (used.get(dep) == null) {
                if (!dfs(dep)) {
                    if (needToAdd) {
                        resultList.add(path);
                    }
                    if (path == lastInCycle) {
                        needToAdd = false;
                    }
                    return false;
                }
            } else if (used.get(dep) == 1) {
                needToAdd = true;
                lastInCycle = dep;
                resultList.clear();
                resultList.add(path);
                return false;
            }
        }
        resultList.add(path);
        used.put(path, 2);
        return true;
    }
}
