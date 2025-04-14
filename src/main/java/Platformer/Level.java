package Platformer;

import java.util.ArrayList;
import java.util.List;
import static Platformer.Constant.*;

//хранит данные уровня
public class Level {
    private final char[][] grid;
    private final GameObj[][] gameGrid;
    private List<GameObj> movables;
    //position of player
    private int playerPosX;
    private int playerPosY;

    public Level(char[][] tiles) {
        this.grid = tiles;
        gameGrid = new GameObj[tiles.length][tiles[0].length];
        movables = new ArrayList<GameObj>();
        getLevelReady();
    }

    public char[][] getGrid() {
        return grid;
    }

    public void getLevelReady(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 20; j++){
                if(grid[i][j] == '#'){
                    gameGrid[i][j] = new Ground("poldlyapidorov.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '@'){
                    playerPosX = j * 60;
                    playerPosY = i * 60;
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
                else  if(grid[i][j] == 'b'){
                    movables.add(new Box("box.png", j * SpriteSize, i * SpriteSize));
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
                else{
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
            }
        }
    }

    public GameObj[][] getGameGrid() {
        return gameGrid;
    }

    public List<GameObj> getMovables() {
        return movables;
    }

    public void addMovables(GameObj obj) {
        this.movables.add(obj);
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public int getPlayerPosY() {
        return playerPosY;
    }
}
