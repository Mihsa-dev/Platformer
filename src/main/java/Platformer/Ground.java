package Platformer;

public class Ground extends GameObj{
    public Ground(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = false;
        solid = true;
    }
}
