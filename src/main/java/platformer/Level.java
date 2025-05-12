package platformer;

import java.util.ArrayList;
import static platformer.Constant.*;

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
        this.movables = new ArrayList<GameObj>();
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
                else if(grid[i][j] == '^'){
                    gameGrid[i][j] = new Spikes("chleni.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '|'){
                    gameGrid[i][j] = new Stairs("stair.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '-'){
                    movables.add(new PlatformHor("platform.png", j * SpriteSize, i * SpriteSize));
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == '0'){
                    gameGrid[i][j] = new LevelEnd("scp_173_noBackground.png", j * SpriteSize, i * SpriteSize);
                    levelEndPosInGridRow = i;
                    levelEndPosInGridColumn = j;
                }
                else if(grid[i][j] == '@'){
                    playerPosX = j * SpriteSize;
                    playerPosY = i * SpriteSize;
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 'b'){
                    movables.add(new Box("box.png", j * SpriteSize, i * SpriteSize));
                    gameGrid[i][j] = new EmptyObj("empty.png", j * SpriteSize, i * SpriteSize);
                }
                else if(grid[i][j] == 'f'){
                    movables.add(new EnemyFly("MYPGqYP10O0.jpg", j * SpriteSize, i * SpriteSize));
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

    public ArrayList<GameObj> getMovables() {
        return movables;
    }

    public int getPlayerPosX() {return playerPosX;}

    public int getPlayerPosY() {return playerPosY;}

    public int getLevelEndPosInGridRow() {return levelEndPosInGridRow;}

    public int getLevelEndPosInGridColumn() {return levelEndPosInGridColumn;}
}
