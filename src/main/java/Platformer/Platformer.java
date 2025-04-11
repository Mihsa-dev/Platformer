package Platformer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
        this.player = new Player("ee86dafa1924dd4c209bcf0a2145ebab.jpg", 0, ScreenHeight - SpriteHeight);

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
        char[][] grid = level.getTiles();
        for (char[] chars : grid) {
            System.out.println(chars);
        }
    }

    public static void main(String[] args) throws IOException {
        var frame = new JFrame();
        var panel = new Platformer();

        panel.setSize(ScreenWidth,ScreenHeight);
        panel.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        panel.drawLevel(panel.levelManager.getCurrentLevel());
    }

    public void animate() {
        player.update();
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

