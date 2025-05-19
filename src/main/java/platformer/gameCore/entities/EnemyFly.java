package platformer.gameCore.entities;

import platformer.render.Platformer;

import java.util.ArrayList;

import static platformer.gameCore.utils.Constant.SpriteSize;

public class EnemyFly extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Скорость по Y
    private Player player;

    public EnemyFly(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = true;
    }

    @Override
    public void start() {
        vx = 2;  // Базовая скорость по горизонтали
        vy = 1;  // Базовая скорость по вертикали

        player = Platformer.player;
    }

    @Override
    public void update() {
        if (getPositionX() <= player.getPositionX()){
            vx = 2;
        }
        else{
            vx = -2;
        }
        if (getPositionY() <= player.getPositionY()){
            vy = 1;
        }
        else {
            vy = -1;
        }

        setPositionX(getPositionX() + vx);
        setPositionY(getPositionY() + vy);
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
        else{
            super.collide(prev, player, gameGrid, movables, k+1);
        }
    }
}
