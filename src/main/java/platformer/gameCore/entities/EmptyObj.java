package platformer.gameCore.entities;

public class EmptyObj extends GameObj{
    public EmptyObj(String spriteName, int x, int y) {
        super(x, y, spriteName);
        movable = false;
        solid = false;
    }
}
