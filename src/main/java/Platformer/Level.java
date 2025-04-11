package Platformer;

//хранит данные уровня
public class Level {
    private char[][] grid;
    private String name;

    public Level(char[][] tiles, String name) {
        this.grid = tiles;
        this.name = name;
    }

    public char[][] getTiles() {
        return grid;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }
}
