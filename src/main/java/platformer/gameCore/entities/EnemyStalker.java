package platformer.gameCore.entities;

import platformer.render.Platformer;

import java.util.ArrayList;

import static platformer.gameCore.utils.Constant.SpriteSize;

public class EnemyStalker extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Скорость по Y
    private boolean direction; // 1 - движение вправо, 0 - движение влево
    private boolean canJump;
    private Player player;

    public EnemyStalker(int x, int y, String... spriteNames) {
        super(x, y, spriteNames);
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

        if (getPositionX() < player.getPositionX()) {
            spriteStorage.setCurrentIndex(0);
            direction = true;
        }
        else{
            spriteStorage.setCurrentIndex(1);
            direction = false;
        }
    }

    @Override
    public void update() {
        if (player.getPositionX() - getPositionX() > 2){
            vx = 2;
            if (!direction){
                spriteStorage.increaseCurrentIndex();
            }
            direction = true;
        }
        else if (getPositionX() - player.getPositionX() > 2){
            vx = -2;
            if (direction){
                spriteStorage.decreaseCurrentIndex();
            }
            direction = false;
        }
        else{
            vx = 0;
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
