package platformer;

import java.util.ArrayList;

import static platformer.Constant.SpriteSize;

public class EnemyStalker extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Скорость по Y
    private boolean canJump;
    private Player player;

    public EnemyStalker(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = true;
    }

    @Override
    public void start() {
        vx = 2;  // Базовая скорость по горизонтали
        vy = 20;  // Базовая скорость по вертикали
        canJump = true;
        useGravity = true;

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
        setPositionX(getPositionX() + vx);


        if (getPositionY() >= player.getPositionY() && canJump){
            addForce(0, -vy);
            canJump = false;
        }

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

    public void allowJump(){
        this.canJump = true;
    }
}
