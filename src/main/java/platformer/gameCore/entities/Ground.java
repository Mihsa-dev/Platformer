package platformer.gameCore.entities;

public class Ground extends GameObj{
    public Ground(String spriteName, int x, int y) {
        super(x, y, spriteName);
        movable = false;
        solid = true;
    }
}
