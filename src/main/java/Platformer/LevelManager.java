package Platformer;

import java.io.IOException;
import java.util.List;

//управляет уровнями
public class LevelManager {
    private List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() throws IOException {
        this.levels = LevelLoader.loadAllLevels();
        this.currentLevelIndex = 0;
    }

    public void setСurrentLevelIndex(int index) {
        if (index >= 0 && index < levels.size()) {
            currentLevelIndex = index;
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
        }
    }

    public void previousLevel() {
        if (currentLevelIndex > 0) {
            currentLevelIndex--;
        }
    }
}