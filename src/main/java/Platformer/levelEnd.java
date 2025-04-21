package Platformer;

import java.util.ArrayList;

public class levelEnd extends GameObj{

    LevelManager levelManager;

    public levelEnd(String spriteName, int x, int y) {
        super(spriteName, x, y);
        solid = true;
        movable = false;
    }

    @Override
    public void Collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if (prev instanceof Player){
            levelManager.nextLevel();
        }
    }

    public void setLevelManager(LevelManager levelManager){
        this.levelManager = levelManager;
    }
}
