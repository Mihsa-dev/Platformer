package platformer;

import java.util.ArrayList;

import static platformer.Constant.SpriteSize;

public class Stairs extends GameObj{
    public Stairs(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = false;
    }

    @Override
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if (prev instanceof Player){
            if ((Math.min(
                    prev.getPositionX() + SpriteSize - this.getPositionX(),
                    this.getPositionX() + SpriteSize - prev.getPositionX()
            ) > 15)
                    &&
            (Math.min(
                    prev.getPositionY() + SpriteSize - this.getPositionY(),
                    this.getPositionY() + SpriteSize - prev.getPositionY()
            ) > 15)
            ){
                prev.useGravity = false;
            }
        }
        else{
            super.collide(prev, player, gameGrid, movables, k+1);
        }
    }

}
