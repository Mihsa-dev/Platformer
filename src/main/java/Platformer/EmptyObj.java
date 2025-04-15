package Platformer;

public class EmptyObj extends GameObj{
    public EmptyObj(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = false;
    }
}
