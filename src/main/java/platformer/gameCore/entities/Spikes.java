package platformer.gameCore.entities;

import java.util.ArrayList;

import static platformer.gameCore.utils.Constant.SpriteSize;

public class Spikes extends GameObj{

    public Spikes(String spriteName, int x, int y) {
        super(x, y, spriteName);
        movable = false;
        solid = true;
    }

    @Override
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k) {
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
                GameObj.levelManager.reloadLevel();
            }
        }
        else{
            super.collide(prev, player, gameGrid, movables, k+1);
        }
    }
}
