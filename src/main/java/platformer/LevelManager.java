package platformer;

import java.io.IOException;
import java.util.List;

//управляет уровнями
public class LevelManager {
    protected List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() throws IOException {
        this.levels = LevelLoader.loadAllLevels();
        this.currentLevelIndex = 0;
    }

    public void setCurrentLevelIndex(int index) {
        if (index >= 0 && index < levels.size()) {
            currentLevelIndex = index;
        }
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
            useAllStartCurrentLevel();

        }
    }

    public void previousLevel() {
        if (currentLevelIndex > 0) {
            currentLevelIndex--;
            useAllStartCurrentLevel();
        }
    }

    public void reloadLevel(){
        try {
            // Перезагружаем текущий уровень
            Level reloadedLevel = LevelLoader.loadLevelNumber(getCurrentLevelIndex() + 1);
            levels.set(getCurrentLevelIndex(), reloadedLevel);

            // Сбрасываем позицию игрока
            Platformer.player.setPositionX(reloadedLevel.getPlayerPosX());
            Platformer.player.setPositionY(reloadedLevel.getPlayerPosY());

        } catch (IOException e) {
            throw new RuntimeException("Failed to reload level", e);
        }
        useAllStartCurrentLevel();
    }

    private void useAllStartCurrentLevel(){
        // после перезагрузки уровня вызываем у всех объектов start
        for (GameObj[] arr : getCurrentLevel().getGameGrid()){
            for (GameObj obj : arr){
                obj.start();
            }
        }
        for (GameObj obj : getCurrentLevel().getMovables()){
            obj.start();
        }
        Platformer.player.start();
    }
}