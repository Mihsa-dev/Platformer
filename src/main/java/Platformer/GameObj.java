package Platformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Platformer.Constant.*;

public abstract class GameObj {
    private Image sprite;                   // Спрайт объекта
    private int positionX;                  // X-координата
    private int positionY;                  // Y-координата
    protected boolean movable;              // можно ли двигать объект
    protected boolean solid;                // пустой ли объект
    protected boolean isMoved = false;      // сдвинулся ли объект
    private final Vector2D velocity;        // скорость объекта
    private final float mass;                     // масса объекта
    protected  boolean useGravity = true;   // использует ли объект гравитацию

    public void start() {}
    public void update() {}

    public GameObj(String spriteName, int x, int y) {
        this.setSprite(spriteName); // Загружаем спрайт
        this.setPositionX(x);       // Устанавливаем позицию X
        this.setPositionY(y);       // Устанавливаем позицию Y
        start();                    // Инициализация
        velocity = new Vector2D(0, 0);
        mass = 1;
    }

    // Загрузка и масштабирование спрайта
    public void setSprite(String spriteNameFile) {
        try{
            this.sprite = ImageIO.read(new File(spriteNameFile))
                .getScaledInstance(SpriteSize,SpriteSize,Image.SCALE_SMOOTH);
        }
        catch (IOException e){
            System.err.println("сука, че с файлом:" + e.getMessage());
        }

    }

    // Метод для вызова проверки на пересечения с окружающими объектами
    public void Collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if(k > 10)
            return;

        if(this.movable){
            if (prev != player || this!=player){
                if (pushColl(prev, player)){
                    player.Collide(this, player, gameGrid, movables, k+1);
                }

            }
            // область проверки коллизий для всех
            int min_x = 0;
            int max_x = 2;
            // у игрока больше область проверки коллизий
            if (this == player){
                min_x = -2;
                max_x = 3;
            }
            for (int i = min_x; i < max_x; i++){
                for (int j = min_x; j < max_x; j++){
                    int xx = (this.getPositionX() / SpriteSize) + i;
                    int yy = (this.getPositionY() / SpriteSize) + j;

                    xx = Math.max(0, Math.min(xx, 19));
                    yy = Math.max(0, Math.min(yy, 9));

                    GameObj obj = gameGrid[yy][xx];

                    // есть ли коллизия
                    boolean col = (getPositionX() < (obj.getPositionX() + SpriteSize) &&
                            getPositionX() > obj.getPositionX()) ||
                            ((getPositionX() + SpriteSize) > obj.getPositionX() &&
                                    getPositionX() < obj.getPositionX()) ||
                            (getPositionY() < (obj.getPositionY() + SpriteSize) &&
                                    getPositionY() > obj.getPositionY()) ||
                            ((getPositionY() + SpriteSize) > obj.getPositionY() &&
                                    getPositionY() < obj.getPositionY());

                    if (obj.isSolid() && col){
                        obj.Collide(this, player, gameGrid, movables, k+1);
                    }
                }
            }
        }
        else {
            if (pushColl(prev, player)){
                player.Collide(this, player, gameGrid, movables, k+1);
            }
        }

        for (GameObj obj : movables) {
            if(obj != this) {
                if (pushColl(prev, obj)){
                    obj.Collide(this, player, gameGrid, movables, k+1);
                }

            }
        }

    }

    // Функция для выталкивания объекта, если столкновение произошло
    private boolean pushColl(GameObj prev, GameObj obj){
        boolean xxl = getPositionX() < (obj.getPositionX() + SpriteSize) && getPositionX() > obj.getPositionX();
        boolean xxr = (getPositionX() + SpriteSize) > obj.getPositionX() && getPositionX() < obj.getPositionX();
        boolean yyu = getPositionY() < (obj.getPositionY() + SpriteSize) && getPositionY() > obj.getPositionY();
        boolean yyd = (getPositionY() + SpriteSize) > obj.getPositionY() && getPositionY() < obj.getPositionY();

        int x_off = Math.abs(obj.getPositionX() - getPositionX());
        int y_off = Math.abs(obj.getPositionY() - getPositionY());

        Vector2D this_vel = this.velocity.clone();
        Vector2D obj_vel = obj.getVelocity().clone();
        Vector2D result;
        if (this.isMovable()){
            result = this_vel.multiply(this.mass).add(obj_vel.multiply(obj.mass)).multiply(1/(this.mass + obj.mass));
            if (!prev.isMovable()) {
                result = obj_vel.multiply(-1);
            }
        }
        else {
            result = obj_vel.multiply(-1);
        }
        if (y_off < x_off){
            if (xxr){
                obj.setPositionX(getPositionX() + SpriteSize);

                if (this.movable)
                    obj.addForce(result);

                return true;
            }
            else if (xxl){
                obj.setPositionX(getPositionX() - SpriteSize);

                if (this.movable)
                    obj.addForce(result);

                return true;
            }
        }
        else if (x_off < y_off){
            if (yyu) {
                obj.setPositionY(getPositionY() - SpriteSize);

                obj.addForce(result);

                return true;
            }
            else if (yyd){
                obj.setPositionY(getPositionY() + SpriteSize);

                obj.addForce(result);

                return true;
            }
        }
        return false;
    }

    public void addForce(Vector2D force) {

        this.velocity.add(force);
    }

    public void addForce(float dx, float dy) {
        setVelocity(new Vector2D(getVelocity().getX() + dx, getVelocity().getY() + dy));
    }

    public void updater(){

        isMoved = false;

        update();

        if(this.movable && this.useGravity){
            addForce(0, 1);
        }

        applyFrictionX(0.6f);

        Threshold(0.01f);

        setPositionX(getPositionX() + (int)getVelocity().getX());
        setPositionY(getPositionY() + (int)getVelocity().getY());

        if (getVelocity().len() != 0) isMoved = true;
    }

    // метод для сопротивления среды ускорению
    public void applyFrictionX(float frictionFactor) {
        setVelocity(getVelocity().getX() * frictionFactor, getVelocity().getY() * 0.9f);

    }
    public void Threshold(float minSpeedThreshold) {
        if (Math.abs(getVelocity().getX()) < minSpeedThreshold) getVelocity().setX(0);
        if (Math.abs(getVelocity().getY()) < minSpeedThreshold) getVelocity().setY(0);
    }

    // Геттеры и сеттеры
    public Image getSprite() { return sprite; }
    public int getPositionX() { return positionX; }
    public void setPositionX(int x) { positionX = x; }
    public int getPositionY() { return positionY; }
    public void setPositionY(int y) { positionY = y; }

    public boolean isMovable() {
        return movable;
    }
    public boolean isMoved() {
        return isMoved;
    }
    public boolean isSolid() {
        return solid;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D vel) {
        this.velocity.setX(vel.getX());
        this.velocity.setY(vel.getY());
    }

    public void setVelocity(float x, float y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }
}
