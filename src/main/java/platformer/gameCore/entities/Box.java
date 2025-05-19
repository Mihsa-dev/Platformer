package platformer.gameCore.entities;

public class Box extends GameObj{
    public Box(String spriteName, int x, int y) {
        super(spriteName, x, y);
        movable = true;
        solid = true;
        pushable = true;
    }
    @Override
    public  void start(){
        useGravity = true;
    }
}
