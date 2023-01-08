import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("Root/");
        DirectoriesWalker dw = new DirectoriesWalker();
        dw.bypass(path);

        FileCrawler fc = new FileCrawler(dw.dependencies);
        for (Path file : dw.fileNames) {
            if (fc.used.get(file) == null) {
                if (!fc.dfs(file)) {
                    System.out.println("Error! Cycle in program!!!");
                    for (Path p : fc.resultList) {
                        System.out.println(p);
                    }
                    return;
                }
            }
        }

        System.out.println("Sorted files:");
        for (Path p : fc.resultList) {
            System.out.println(p);
        }

        try {
            PrintWriter writer = new PrintWriter("result.txt", StandardCharsets.UTF_8);
            for (Path p : fc.resultList) {
                writer.println(dw.getText(p));
            }
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}