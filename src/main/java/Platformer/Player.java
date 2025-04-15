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
            newX -= getVx();
            isMoved = true;
        }
        if (isRight){
            newX += getVx();
            isMoved = true;
        }
        if (isUp){
            newY -= getVy();
            isMoved = true;
        }
        if (isDown){
            newY += getVy();
            isMoved = true;
        }

        // Ограничение движения
        newX = Math.max(0, Math.min(newX, ScreenWidth - SpriteSize));
        newY = Math.max(0, Math.min(newY, ScreenHeight - SpriteSize));


        setPositionX(newX);
        setPositionY(newY);
    }

    // Геттеры скорости
    public int getVx() { return vx; }
    public int getVy() { return vy; }
}
