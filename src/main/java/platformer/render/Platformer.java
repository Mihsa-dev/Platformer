package platformer.render;

import platformer.gameCore.level.LevelManager;
import platformer.gameCore.entities.Player;
import platformer.gameCore.entities.GameObj;
import platformer.input.InputListener;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static platformer.gameCore.utils.Constant.*;

public class Platformer extends JPanel {
    public static Player player;
    private final Thread thread;
    private final LevelManager levelManager;
    private final InputListener listener;
    private final GameRenderer gameRenderer;

    public Platformer() throws IOException {
        super();
        setFocusable(true);

        this.levelManager = new LevelManager();
        GameObj.levelManager = this.levelManager;
        player = new Player("sprites/blackPlayer.png",
                levelManager.getCurrentLevel().getPlayerPosX(),
                levelManager.getCurrentLevel().getPlayerPosY());

        useAllStart();
        this.gameRenderer = new GameRenderer(levelManager, player);

        listener = new InputListener();
        this.addKeyListener(listener);

        thread = new MoveThread(this);
        thread.setDaemon(true);
        thread.start();
    }

    private void useAllStart() {
        for (var arr : levelManager.getCurrentLevel().getGameGrid()) {
            for (var obj : arr) {
                obj.start();
            }
        }
        for (var obj : levelManager.getCurrentLevel().getMovables()) {
            obj.start();
        }
        player.start();
    }

    @Override
    public void paint(Graphics g) {
        gameRenderer.render(g);
    }

    public void animate() {
        updateGameState();
        checkCollisions();
        repaint();
    }

    private void updateGameState() {
        player.updater();
        for (var obj : levelManager.getCurrentLevel().getMovables()) {
            obj.updater();
        }
    }

    private void checkCollisions() {
        if (player.isMoved()) {
            player.collide(player, player, levelManager.getCurrentLevel().getGameGrid(),
                    levelManager.getCurrentLevel().getMovables(), 0);
        }

        for (var obj : levelManager.getCurrentLevel().getMovables()) {
            if (obj.isMoved()) {
                obj.collide(obj, player, levelManager.getCurrentLevel().getGameGrid(),
                        levelManager.getCurrentLevel().getMovables(), 0);
            }
        }
    }

    private static class MoveThread extends Thread {
        Platformer platformer;

        public MoveThread(Platformer platformer) {
            super("MoveThread");
            this.platformer = platformer;
        }

        public void run() {
            while (true) {
                platformer.animate();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        Platformer platformer = new Platformer();

        platformer.setSize(ScreenWidth, ScreenHeight);
        platformer.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        frame.add(platformer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}