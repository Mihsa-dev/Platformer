package Platformer;

import static Platformer.Constant.*;
import static Platformer.InputListener.*;

public class Player extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Скорость по Y

    public Player(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
    }

    @Override
    public void start() {
        vx = 4;  // Базовая скорость по горизонтали
        vy = 2;  // Базовая скорость по вертикали
    }

    @Override
    public void update() {
        int newX = getPositionX();
        int newY = getPositionY();

        isMoved = false;

        if (isLeft){
            addForce(-getVx(), 0);
            isMoved = true;
        }
        if (isRight){
            addForce(getVx(), 0);
            isMoved = true;
        }
        if (isUp){
            addForce(0, -getVy());
            isMoved = true;
        }
        if (isDown){
            addForce(0, getVy());
            isMoved = true;
        }

        newX += (int)getVelocity().getX();
        newY += (int)getVelocity().getY();

        applyFriction(0.7f, 0.1f);
        if (getVelocity().len() != 0) isMoved = true;

        setPositionX(newX);
        setPositionY(newY);
    }

    // Геттеры скорости
    public int getVx() { return vx; }
    public int getVy() { return vy; }
}
