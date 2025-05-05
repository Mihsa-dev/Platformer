package platformer;

public class Vector2D {
    private float x;
    private float y;

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2D add(Vector2D vector){
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        return this;
    }

    public Vector2D multiply(float a){
        this.setX(this.getX() * a);
        this.setY(this.getY() * a);
        return this;
    }

    public float len(){
        return (float)Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    public void normalize(){
        this.setX(this.getX() / this.len());
        this.setY(this.getY() / this.len());
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(this.getX(), this.getY());
    }
}
