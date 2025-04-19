package Platformer;

//import static Platformer.Constant.*;
import static Platformer.InputListener.*;

public class Player extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Сила прыжка
    private int timer;

    public Player(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
    }

    @Override
    public void start() {
        vx = 4;  // Базовая скорость по горизонтали
        vy = 40;  // Базовая скорость по вертикали
        useGravity = true;
    }

    @Override
    public void update() {

        timer -= 1;

        if (isLeft){
            addForce(-vx, 0);
        }
        if (isRight){
            addForce(vx, 0);
        }
        if (isUp && timer < 1){
            this.setVelocity( new Vector2D(this.getVelocity().getX(), 0));
            addForce(0, -vy);
            timer = 50;
        }
//        if (isDown){
//            addForce(0, getVy());
//            isMoved = true;
//        }


    }

}
