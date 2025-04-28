package Platformer;

//import static Platformer.Constant.*;
import static Platformer.InputListener.*;

public class Player extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Сила прыжка
    private boolean canJump;
    private int coyoteTime;
    private int jumpBuffer;

    public Player(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = true;
    }

    @Override
    public void start() {
        vx = 4;  // Базовая скорость по горизонтали
        vy = 23;  // Базовая скорость по вертикали
        useGravity = true;
    }

    @Override
    public void update() {

        if (coyoteTime > -1) coyoteTime -= 1;

        if (jumpBuffer > -1) jumpBuffer -= 1;

        if (isLeft){
            addForce(-vx, 0);
        }
        if (isRight){
            addForce(vx, 0);
        }
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

    public void allowJump(){
        this.canJump = true;
        coyoteTime = 5;
    }
}
