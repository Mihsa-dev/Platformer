package Platformer;

import java.io.IOException;
import java.util.ArrayList;

import static Platformer.Constant.SpriteSize;

public class Spikes extends GameObj{

    public Spikes(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = true;
    }

    @Override
    public void Collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k) {
        if (prev instanceof Player) {
            if ((Math.min(
                    prev.getPositionX() + SpriteSize - this.getPositionX(),
                    this.getPositionX() + SpriteSize - prev.getPositionX()
                        ) > 5)
                &&
                (Math.min(
                        prev.getPositionY() + SpriteSize - this.getPositionY(),
                        this.getPositionY() + SpriteSize - prev.getPositionY()
                ) > 5)
            ) {
                try {
                    // Перезагружаем текущий уровень
                    Level reloadedLevel = LevelLoader.loadLevelNumber(GameObj.levelManager.getCurrentLevelIndex() + 1);
                    GameObj.levelManager.levels.set(GameObj.levelManager.getCurrentLevelIndex(), reloadedLevel);

                    // Сбрасываем позицию игрока
                    player.setPositionX(reloadedLevel.getPlayerPosX());
                    player.setPositionY(reloadedLevel.getPlayerPosY());

                    // Очищаем и перезагружаем движущиеся объекты
                    movables.clear();
                    movables.addAll(reloadedLevel.getMovables());

                    for (int y = 0; y < gameGrid.length; y++) {
                        for (int x = 0; x < gameGrid[0].length; x++) {
                            gameGrid[y][x] = reloadedLevel.getGameGrid()[y][x];
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to reload level", e);
                }
            }
        }
        else{
            super.Collide(prev, player, gameGrid, movables, k);
        }
    }
}
