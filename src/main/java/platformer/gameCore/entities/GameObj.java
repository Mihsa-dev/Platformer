package platformer.gameCore.entities;

import platformer.gameCore.level.LevelManager;
import platformer.gameCore.math.Vector2D;
import platformer.gameCore.utils.SpriteStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static platformer.gameCore.utils.Constant.*;

public abstract class GameObj {
    public static LevelManager levelManager;

    protected SpriteStorage spriteStorage;
    //private Image sprite;                   // Базовый спрайт объекта
    private int positionX;                  // X-координата
    private int positionY;                  // Y-координата
    protected String spriteName;
    protected boolean movable;              // можно ли двигать объект
    protected boolean solid;                // пустой ли объект
    protected boolean isMoved = false;      // сдвинулся ли объект
    private Vector2D velocity = new Vector2D(0,0);        // скорость объекта
    protected boolean useGravity = false;  // использует ли объект гравитацию
    private int offset;
    protected boolean pushable;

    public void start() {}
    public void update() {}

//    public GameObj(String spriteName, int x, int y) {
//        this.setSprite(spriteName); // Загружаем спрайт
//        this.setPositionX(x);       // Устанавливаем позицию X
//        this.setPositionY(y);       // Устанавливаем позицию Y
//        velocity = new Vector2D(0, 0);
//        offset = 0;
//        pushable = movable;
//    }
    public GameObj(int x, int y, String... filenames) {
        spriteStorage = new SpriteStorage(filenames); // Загружаем спрайты
        this.setPositionX(x);       // Устанавливаем позицию X
        this.setPositionY(y);       // Устанавливаем позицию Y
        velocity = new Vector2D(0, 0);
        offset = 0;
        pushable = movable;
    }

//    // Загрузка и масштабирование спрайта
//    public void setSprite(String spriteNameFile) {
//        try{
//            this.sprite = ImageIO.read(new File(spriteNameFile))
//                .getScaledInstance(SpriteSize,SpriteSize,Image.SCALE_SMOOTH);
//        }
//        catch (IOException e){
//            System.err.println("сука, че с файлом:" + e.getMessage());
//        }
//
//    }

    // Метод для вызова проверки на пересечения с окружающими объектами
    public void collide(GameObj prev, Player player, GameObj[][] gameGrid, ArrayList<GameObj> movables, int k){
        if(k > 9  || !this.isSolid()) return;

        if(this.movable){
            if (prev != player || this!=player){
                if (pushColl(prev, player)){
                    player.collide(this, player, gameGrid, movables, k+1);
                }

            }
            // область проверки коллизий с окружающими моделями
            int min_x = 0;
            int max_x = 2;
            for (int i = min_x; i < max_x; i++){
                for (int j = min_x; j < max_x; j++){
                    int xx = (this.getPositionX() / SpriteSize) + i;
                    int yy = (this.getPositionY() / SpriteSize) + j;

                    xx = Math.max(0, Math.min(xx, 19));
                    yy = Math.max(0, Math.min(yy, 9));

                    GameObj obj = gameGrid[yy][xx];

                    // проверка, есть ли коллизия
                    boolean col = (getPositionX() < (obj.getPositionX() + SpriteSize) &&
                            getPositionX() > obj.getPositionX()) ||
                            ((getPositionX() + SpriteSize) > obj.getPositionX() &&
                                    getPositionX() < obj.getPositionX()) ||
                            (getPositionY() < (obj.getPositionY() + SpriteSize) &&
                                    getPositionY() > obj.getPositionY()) ||
                            ((getPositionY() + SpriteSize) > obj.getPositionY() &&
                                    getPositionY() < obj.getPositionY());

                    if (col){
                        obj.collide(this, player, gameGrid, movables, k+1);
                    }
                }
            }
        }
        else {
            if (pushColl(prev, player)){
                player.collide(this, player, gameGrid, movables, k+1);
            }
        }

        for (GameObj obj : movables) {
            if(obj != this) {
                if (pushColl(prev, obj)){
                    obj.collide(this, player, gameGrid, movables, k+1);
                }

            }
        }
    }

    // Функция для выталкивания объекта, если столкновение произошло
    protected boolean pushColl(GameObj prev, GameObj obj){
        boolean xxl = getPositionX() < (obj.getPositionX() + SpriteSize) && getPositionX() > obj.getPositionX();
        boolean xxr = (getPositionX() + SpriteSize) > obj.getPositionX() && getPositionX() < obj.getPositionX();
        boolean yyu = getPositionY() < (obj.getPositionY() + SpriteSize) && getPositionY() > obj.getPositionY();
        boolean yyd = (getPositionY() + SpriteSize) > obj.getPositionY() && getPositionY() < obj.getPositionY();

        int x_off = Math.abs(obj.getPositionX() - getPositionX());
        int y_off = Math.abs(obj.getPositionY() - getPositionY());

        Vector2D obj_vel = obj.getVelocity().clone();
        Vector2D result = new Vector2D(0,0);

        if(this.isMovable()) {
            if (obj instanceof  Player){
                result = obj_vel.multiply(-0.5f);
            }
        }

        if (y_off < x_off){
            return pushX(xxr, xxl, obj, new Vector2D(result.getX(), 0));
        }
        else if (x_off < y_off && obj.pushable) {
            return pushY(yyu, yyd, obj, result);
        }
        return false;
    }
    protected boolean pushX(boolean xxr, boolean xxl, GameObj obj, Vector2D result){
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
        return false;
    }
    protected boolean pushY(boolean yyu, boolean yyd, GameObj obj, Vector2D result){
        if (yyu) {
            obj.setPositionY(getPositionY() - SpriteSize);
            obj.addForce(result.getX(), -obj.velocity.getY());

            obj.setPositionX(offset + obj.getPositionX());
            obj.setOffset(offset);
            offset = 0;

            if (obj instanceof Player){
                ((Player) obj).allowJump();
            }
            if (obj instanceof EnemyStalker){
                ((EnemyStalker) obj).allowJump();
            }

            return true;
        }
        else if (yyd){
            obj.setPositionY(getPositionY() + SpriteSize);
            obj.addForce(result);
            return true;
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

        applyFrictionX(0.7f);
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
    //public Image getSprite() { return sprite; }
    public SpriteStorage getSpriteStorage(){
        return spriteStorage;
    }
    public int getPositionX() { return positionX; }
    public void setPositionX(int x) {
        positionX = x;
    }
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

    public void setOffset(int x) {
        offset = x;
    }

    public String getSpriteName(){
        return spriteName;
    }
}
