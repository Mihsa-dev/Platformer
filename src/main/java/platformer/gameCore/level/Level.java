package platformer.gameCore.level;

import platformer.gameCore.entities.*;

import java.util.ArrayList;
import static platformer.gameCore.utils.Constant.*;

//хранит данные уровня
public class Level {
    private final char[][] grid;
    private final GameObj[][] gameGrid;
    private ArrayList<GameObj> movables;
    //position of player
    private int playerPosX;
    private int playerPosY;
    //position of levelEnd
    private int levelEndPosInGridRow;
    private int levelEndPosInGridColumn;

    public Level(char[][] tiles) {
        this.grid = tiles;
        this.gameGrid = new GameObj[tiles.length][tiles[0].length];
        this.movables = new ArrayList<>();
        getLevelReady();
    }

    public char[][] getGrid() {
        return grid;
    }

    public void getLevelReady(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 20; j++){
                if(grid[i][j] == '#'){
                    gameGrid[i][j] = new Ground("sprites/poldlyapidorov.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '^'){
                    gameGrid[i][j] = new Spikes("sprites/chleni.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '|'){
                    gameGrid[i][j] = new Stairs("sprites/stair.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '-'){
                    movables.add(new PlatformHor("sprites/platform.png", j * SpriteSize, i * SpriteSize));
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '0'){
                    gameGrid[i][j] = new LevelEnd("sprites/door.png", j * SpriteSize, i * SpriteSize);
                    levelEndPosInGridRow = i;
                    levelEndPosInGridColumn = j;
                }
                else if(grid[i][j] == '@'){
                    playerPosX = j * SpriteSize;
                    playerPosY = i * SpriteSize;
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 'b'){
                    movables.add(new Box("sprites/box.png", j * SpriteSize, i * SpriteSize));
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 'f'){
                    movables.add(new EnemyFly(j * SpriteSize, i * SpriteSize, "sprites/bat_up.png", "sprites/bat_up_reverse.png", "sprites/bat_down.png", "sprites/bat_down_reverse.png"));
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 'p'){
                    movables.add(new EnemyPatrol(j * SpriteSize, i * SpriteSize, "sprites/patrol.png", "sprites/patrol_revers.png", "sprites/patrol_2.png", "sprites/patrol_2_revers.png"));
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 's'){
                    movables.add(new EnemyStalker(j * SpriteSize, i * SpriteSize, "sprites/alien.png", "sprites/alien.png", "sprites/alien.png", "sprites/alien.png"));
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
                else{
                    gameGrid[i][j] = new EmptyObj("sprites/empty.png", j * SpriteSize, i * SpriteSize);
                }
            }
        }
    }

    public GameObj[][] getGameGrid() {
        return gameGrid;
    }

    public ArrayList<GameObj> getMovables() {
        return movables;
    }

    public int getPlayerPosX() {return playerPosX;}

    public int getPlayerPosY() {return playerPosY;}

    public int getLevelEndPosInGridRow() {return levelEndPosInGridRow;}

    public int getLevelEndPosInGridColumn() {return levelEndPosInGridColumn;}
}
