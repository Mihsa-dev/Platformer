package platformer.gameCore.entities;

import platformer.gameCore.math.Vector2D;

import java.util.ArrayList;

import static platformer.input.InputListener.*;

public class Player extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Сила прыжка
    private boolean direction; // текущее направление движения, 1 - движение вправо, 0 - движение влево
    private boolean canJump;
    private int coyoteTime;
    private int jumpBuffer;

    public Player(int x, int y, String... spriteNames) {
        super(x, y, spriteNames);
        movable = true;
        solid = true;
        pushable = true;
    }

    @Override
    public void start() {
        vx = 4;  // Базовая скорость по горизонтали
        vy = 23;  // Базовая скорость по вертикали
        useGravity = true;
        spriteStorage.setCurrentIndex(0);
        direction = true;
    }

    @Override
    public void update() {

        if (coyoteTime > -1) coyoteTime -= 1;

        if (jumpBuffer > -1) jumpBuffer -= 1;

        if (isLeft){
            addForce(-vx, 0);
            if (direction){
                spriteStorage.increaseCurrentIndex();
            }
            direction = false;
        }
        if (isRight){
            addForce(vx, 0);
            if (!direction){
                spriteStorage.increaseCurrentIndex();
            }
            direction = true;
        }


        if(useGravity) {
            if (isUp){
                jumpBuffer = 4;
            }

            if (jumpBuffer > 0 && canJump && coyoteTime > 0) {
                this.setVelocity( new Vector2D(this.getVelocity().getX(), 0));
                addForce(0, -vy);
                canJump = false;
                jumpBuffer = 0;
            }
        }
        else {
            if (isUp) {
                setVelocity(new Vector2D(this.getVelocity().getX() * 0.5f, 0));
                setPositionY(getPositionY() - 3);
                isMoved = true;
            }
            if (isDown) {
                setVelocity(new Vector2D(this.getVelocity().getX() * 0.5f, 0));
                setPositionY(getPositionY() + 3);
                isMoved = true;
            }
        }

    }

    public void allowJump(){
        this.canJump = true;
        coyoteTime = 5;
    }

    @Override
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if (!(prev instanceof Stairs)){
            useGravity = true;
        }
        super.collide(prev, player, gameGrid, movables, k+1);
    }

}
