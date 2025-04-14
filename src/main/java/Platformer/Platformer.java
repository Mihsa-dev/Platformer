package Platformer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

import static Platformer.Constant.*;

public class Platformer extends JPanel{

    private Player player;
    private Thread thread;
    private LevelManager levelManager;
    private InputListener listener;

    public Platformer() throws IOException {
        super();
        setFocusable(true);
        this.levelManager = new LevelManager(); // Инициализация менеджера уровней
        // Создаем игрока в левом нижнем углу
        this.player = new Player("ee86dafa1924dd4c209bcf0a2145ebab.jpg", levelManager.getCurrentLevel().getPlayerPosX(), levelManager.getCurrentLevel().getPlayerPosY());
        levelManager.getCurrentLevel().addMovables(player);
        // Регистрируем слушатель клавиш
        listener = new InputListener();
        this.addKeyListener(listener);

        // Создаем и запускаем поток анимации
        thread = new MoveThread(this);
        thread.setDaemon(true); // Поток завершится при закрытии приложения
        thread.start();
    }

    @Override
    public void paint(Graphics g) {
        var g2d = (Graphics2D)g;

        // Отрисовка черного фона
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,getWidth(),getHeight());


        // draw all objects
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 20; x++){
                GameObj obj = levelManager.getCurrentLevel().getGameGrid()[y][x];
                g2d.drawImage(
                        obj.getSprite(),
                        obj.getPositionX(),
                        obj.getPositionY(),
                        null
                );
            }
        }
        // draw movable objects
        for (GameObj obj : levelManager.getCurrentLevel().getMovables()){
            g2d.drawImage(
                    obj.getSprite(),
                    obj.getPositionX(),
                    obj.getPositionY(),
                    null
            );
        }
        // Отрисовка спрайта игрока
        g2d.drawImage(
                player.getSprite(),
                player.getPositionX(),
                player.getPositionY(),
                null
        );
    }

    private void drawLevel(Level level) {
        // Реализация отрисовки уровня?
        char[][] grid = level.getGrid();
        for (char[] chars : grid) {
            System.out.println(chars);
        }
    }

    public static void main(String[] args) throws IOException {
        var frame = new JFrame();
        var platformer = new Platformer();

        platformer.setSize(ScreenWidth,ScreenHeight);
        platformer.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        frame.add(platformer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        platformer.drawLevel(platformer.levelManager.getCurrentLevel());
    }

    public void animate() {
        player.update();

        for (GameObj obj : levelManager.getCurrentLevel().getMovables()){
            if(obj.isMoved())
                obj.Collide(levelManager.getCurrentLevel().getGameGrid(),
                        levelManager.getCurrentLevel().getMovables(), 0);
        }
        repaint();
    }

    private static class MoveThread extends Thread{
        Platformer platformer;
        public MoveThread(Platformer platformer) {
            super("MoveThread");
            this.platformer = platformer;
        }
        public void run(){
            while(true) {
                platformer.animate();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

