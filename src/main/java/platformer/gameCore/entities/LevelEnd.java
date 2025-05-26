package platformer.gameCore.entities;

import java.util.ArrayList;

public class LevelEnd extends GameObj{
    public LevelEnd(String spriteName, int x, int y) {
        super(x, y, spriteName);
        movable = false;
        solid = false;
    }

    @Override
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if (prev instanceof Player){
            GameObj.levelManager.nextLevel();
            prev.setPositionX(GameObj.levelManager.getCurrentLevel().getPlayerPosX());
            prev.setPositionY(GameObj.levelManager.getCurrentLevel().getPlayerPosY());
        }
    }
}
