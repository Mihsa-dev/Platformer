package Platformer;

import java.util.ArrayList;

import static Platformer.Constant.SpriteSize;

public class Platform_hor extends GameObj{
    private int vx;  // Скорость по X
    private int direction; // когда -1 - влево, когда 1 - вправо

    public Platform_hor(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = false;
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
        setOffset(2 * vx * direction);
        isMoved = true;
    }

    @Override
    public void Collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k) {

        if (prev.isSolid() && prev != player && prev != this){
            direction *= -1;
        }
        super.Collide(prev, player, gameGrid, movables, k+1);
    }
}
