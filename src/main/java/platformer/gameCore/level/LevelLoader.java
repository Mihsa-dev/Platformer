package platformer.gameCore.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//загружает уровни из файлов
public class LevelLoader {
    private static final String LEVELS_DIR = "levels/";

    public static Level loadLevel(String filename) throws IOException {
        Path path = Paths.get(LEVELS_DIR + filename);
        List<String> lines = Files.readAllLines(path);

        char[][] tiles = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            tiles[i] = lines.get(i).toCharArray();
        }
        return new Level(tiles);
    }

    public static Level loadLevelNumber(int number) throws IOException{
        String filename = "level_" + String.valueOf(number) + ".txt";
        return loadLevel(filename);
    }

    public static List<Level> loadAllLevels() throws IOException {
        List<Level> levels = new ArrayList<>();
        levels.add(loadLevel("level_1.txt"));
        levels.add(loadLevel("level_2.txt"));
        return levels;
    }
}
