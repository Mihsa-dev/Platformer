package Platformer;

import java.util.List;

public class EmptyObj extends GameObj{
    public EmptyObj(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = false;
    }
    @Override
    public void Collide(GameObj[][] gameGrid, List<GameObj> movables, int k){
        return;
    }
}
