package Platformer;

import java.util.ArrayList;
import java.util.List;
import static Platformer.Constant.*;

//хранит данные уровня
public class Level {
    private final char[][] grid;
    private final List<GameObj> gameGrid;

    //position of player
    private int playerPosX;
    private int playerPosY;

    public Level(char[][] tiles) {
        this.grid = tiles;
        gameGrid = new ArrayList<>();
        getLevelReady();
    }

    public char[][] getGrid() {
        return grid;
    }

    public void getLevelReady(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 20; j++){
                if(grid[i][j] == '#'){
                    gameGrid.add(new Ground("poldlyapidorov.png", j * SpriteSize, i * SpriteSize));
                }
                if(grid[i][j] == '@'){
                    playerPosX = j * 60;
                    playerPosY = i * 60;
                }
            }
        }
    }

    public List<GameObj> getGameGrid() {
        return gameGrid;
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public int getPlayerPosY() {
        return playerPosY;
    }
}
