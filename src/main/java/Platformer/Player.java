package Platformer;

import static Platformer.Constant.*;

public class Player extends GameObj{

    private int vx;  // Скорость по X
    private int vy;  // Скорость по Y

    public Player(String spriteName, int x, int y) {
        super(spriteName, x, y);
    }

    @Override
    public void start() {
        vx = 2;  // Базовая скорость по горизонтали
        vy = 1;  // Базовая скорость по вертикали
    }

    public void update(boolean isLeft, boolean isRight, boolean isUp, boolean isDown) {
        int newX = getPositionX();
        int newY = getPositionY();

        if (isLeft) newX -= getVx();
        if (isRight) newX += getVx();
        if (isUp) newY -= getVy();
        if (isDown) newY += getVy();

        // Ограничение движения
        newX = Math.max(0, Math.min(newX, ScreenWidth - SpriteWidth));
        newY = Math.max(0, Math.min(newY, ScreenHeight - SpriteHeight));

        setPositionX(newX);
        setPositionY(newY);
    }

    // Геттеры скорости
    public int getVx() { return vx; }
    public int getVy() { return vy; }
}
