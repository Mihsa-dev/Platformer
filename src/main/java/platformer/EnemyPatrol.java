package platformer;

import java.util.ArrayList;

import static platformer.Constant.*;

public class EnemyPatrol extends GameObj{

    private int vx;  // Скорость по X
    private int direction; // когда -1 - влево, когда 1 - вправо

    public EnemyPatrol(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = true;
    }

    @Override
    public void start() {
        vx = 2;  // Базовая скорость по горизонтали
        direction = 1;
        useGravity = false;
    }

    @Override
    public void update() {
        setPositionX(getPositionX() + vx * direction);
        isMoved = true;
    }

    @Override
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k) {
        if (prev instanceof Player) {
            if ((Math.min(
                    prev.getPositionX() + SpriteSize - this.getPositionX(),
                    this.getPositionX() + SpriteSize - prev.getPositionX()
            ) > -5)
                    &&
                    (Math.min(
                            prev.getPositionY() + SpriteSize - this.getPositionY(),
                            this.getPositionY() + SpriteSize - prev.getPositionY()
                    ) > -5)
            ) {
                GameObj.levelManager.reloadLevel();
            }
        }
        if (prev.isSolid() && prev != this || checkEndRoad(gameGrid)){
            direction *= -1;
        }
        super.collide(prev, player, gameGrid, movables, k+1);
    }

//    private boolean checkEndRoad(GameObj[][] gameGrid){
//        int yy = this.getPositionX() / SpriteSize;
//        int xx = this.getPositionY() / SpriteSize;
//
//        if (direction == 1){
//            if ((yy + 1 < ScreenWidth/SpriteSize) && (xx + 1 < ScreenHeight/SpriteSize)){
//                return gameGrid[xx + 1][yy + 1] instanceof EmptyObj;
//            }
//        }
//        else{
//            if ((yy - 1 > 0) && (xx + 1 < ScreenHeight/SpriteSize)){
//                return gameGrid[xx + 1][yy - 1] instanceof EmptyObj;
//            }
//        }
//        return false;
//    }
    private boolean checkEndRoad(GameObj[][] gameGrid){
        int yy = this.getPositionX() / SpriteSize;
        int xx = this.getPositionY() / SpriteSize;

        if (direction == 1){
            if ((yy + 1 < ScreenWidth/SpriteSize) && (xx + 1 < ScreenHeight/SpriteSize)){
                return gameGrid[xx + 1][yy + 1] instanceof EmptyObj;
            }
        }
        else{
            if ((yy - 1 > 0) && (xx + 1 < ScreenHeight/SpriteSize)){
                return gameGrid[xx + 1][yy - 1] instanceof EmptyObj;
            }
        }
        return false;
    }
}
