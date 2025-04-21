package Platformer;

import java.util.ArrayList;

public class levelEnd extends GameObj{
    public levelEnd(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = true;
    }

    @Override
    public void Collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if (prev instanceof Player){
            GameObj.levelManager.nextLevel();
            prev.setPositionX(GameObj.levelManager.getCurrentLevel().getPlayerPosX());
            prev.setPositionY(GameObj.levelManager.getCurrentLevel().getPlayerPosY());
        }
    }
}
