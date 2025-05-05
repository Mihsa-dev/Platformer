package platformer;

import java.util.ArrayList;

public class PlatformHor extends GameObj{
    private int vx;  // Скорость по X
    private int direction; // когда -1 - влево, когда 1 - вправо
    private boolean reverse_direction;

    public PlatformHor(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = false;
        reverse_direction = true;
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
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k) {
        if (prev instanceof Player){
          prev.getVelocity().setX(prev.getVelocity().getX() * -1f);
        }
        if (prev.isSolid() && prev != this){
            direction *= -1;
        }
        super.collide(prev, player, gameGrid, movables, k+1);
    }
}
