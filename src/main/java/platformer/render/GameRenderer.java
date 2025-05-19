package platformer.render;

import platformer.gameCore.level.LevelManager;
import platformer.gameCore.entities.Player;
import java.awt.*;

public class GameRenderer {
    private final LevelManager levelManager;
    private final Player player;

    public GameRenderer(LevelManager levelManager, Player player) {
        this.levelManager = levelManager;
        this.player = player;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Отрисовка черного фона
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, g2d.getClipBounds().width, g2d.getClipBounds().height);

        // Отрисовка статических объектов
        renderStaticObjects(g2d);

        // Отрисовка подвижных объектов
        renderMovableObjects(g2d);

        // Отрисовка игрока
        renderPlayer(g2d);
    }

    private void renderStaticObjects(Graphics2D g2d) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 20; x++) {
                var obj = levelManager.getCurrentLevel().getGameGrid()[y][x];
                g2d.drawImage(
                        obj.getSprite(),
                        obj.getPositionX(),
                        obj.getPositionY(),
                        null
                );
            }
        }
    }

    private void renderMovableObjects(Graphics2D g2d) {
        for (var obj : levelManager.getCurrentLevel().getMovables()) {
            g2d.drawImage(
                    obj.getSprite(),
                    obj.getPositionX(),
                    obj.getPositionY(),
                    null
            );
        }
    }

    private void renderPlayer(Graphics2D g2d) {
        g2d.drawImage(
                player.getSprite(),
                player.getPositionX(),
                player.getPositionY(),
                null
        );
    }
}