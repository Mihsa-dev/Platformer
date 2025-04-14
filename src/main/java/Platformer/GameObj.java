package Platformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static Platformer.Constant.*;

public abstract class GameObj {
    private Image sprite; // Спрайт объекта
    private int positionX; // X-координата
    private int positionY; // Y-координата
    protected boolean movable;
    protected boolean solid;
    protected boolean isMoved = false;

    public void start() {}
    public void update() {}

    public GameObj(String spriteName, int x, int y) {
        this.setSprite(spriteName); // Загружаем спрайт
        this.setPositionX(x);       // Устанавливаем позицию X
        this.setPositionY(y);       // Устанавливаем позицию Y
        start();                    // Инициализация
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

    public void Collide(GameObj[][] gameGrid, List<GameObj> movables, int k){
        if(k > 1)
            return;

        if(this.movable){

            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 2; j++){
                    int xx = (this.getPositionX() / SpriteSize) + i;
                    int yy = (this.getPositionY() / SpriteSize) + j;

                    xx = Math.max(0, Math.min(xx, 19));
                    yy = Math.max(0, Math.min(yy, 9));

                    GameObj obj = gameGrid[yy][xx];

                    if (obj.isSolid()){
                        boolean xxl = getPositionX() < (obj.getPositionX() + SpriteSize) && getPositionX() > obj.getPositionX();
                        boolean xxr = (getPositionX() + SpriteSize) > obj.getPositionX() && getPositionX() < obj.getPositionX();
                        boolean yyu = getPositionY() < (obj.getPositionY() + SpriteSize) && getPositionY() > obj.getPositionY();
                        boolean yyd = (getPositionY() + SpriteSize) > obj.getPositionY() && getPositionY() < obj.getPositionY();

                        int x_off = Math.abs(obj.getPositionX() - getPositionX());
                        int y_off = Math.abs(obj.getPositionY() - getPositionY());

                        if (y_off < x_off){
                            if (xxr){
                                setPositionX(obj.getPositionX() - SpriteSize);
                            }
                            else if (xxl){
                                setPositionX(obj.getPositionX() + SpriteSize);
                            }
                        }
                        else if (x_off < y_off){
                            if (yyu) {
                                setPositionY(obj.getPositionY() + SpriteSize);
                            }
                            else if (yyd){
                                setPositionY(obj.getPositionY() - SpriteSize);
                            }
                        }
                        obj.Collide(gameGrid, movables, k+1);


                    }
                }
            }
        }

        for (GameObj obj : movables){
            if(obj != this){
                boolean col = false;

                boolean xxl = getPositionX() < (obj.getPositionX() + SpriteSize) && getPositionX() > obj.getPositionX();
                boolean xxr = (getPositionX() + SpriteSize) > obj.getPositionX() && getPositionX() < obj.getPositionX();
                boolean yyu = getPositionY() < (obj.getPositionY() + SpriteSize) && getPositionY() > obj.getPositionY();
                boolean yyd = (getPositionY() + SpriteSize) > obj.getPositionY() && getPositionY() < obj.getPositionY();

                int x_off = Math.abs(obj.getPositionX() - getPositionX());
                int y_off = Math.abs(obj.getPositionY() - getPositionY());

                if (y_off < x_off){
                    if (xxr){
                        obj.setPositionX(getPositionX() + SpriteSize);
                    }
                    else if (xxl){
                        obj.setPositionX(getPositionX() - SpriteSize);
                    }
                }
                else if (x_off < y_off){
                    if (yyu) {
                        obj.setPositionY(getPositionY() - SpriteSize);
                    }
                    else if (yyd){
                        obj.setPositionY(getPositionY() + SpriteSize);
                    }
                }

                obj.Collide(gameGrid, movables, k+1);
            }
        }
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
}
