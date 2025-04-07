package Platformer;


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

    // Геттеры скорости
    public int getVx() { return vx; }
    public int getVy() { return vy; }
}
