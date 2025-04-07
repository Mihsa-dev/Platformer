package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import static Platformer.Constant.*;

public class Platformer extends JPanel implements KeyListener{

    private Player player;
    private Thread thread;

    // переменные показывающие, зажата ли кнопка
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;

    public Platformer() throws IOException {
        super();
        setFocusable(true);
        // Создаем игрока в левом нижнем углу
        this.player = new Player("ee86dafa1924dd4c209bcf0a2145ebab.jpg", 0, ScreenHeight - SpriteHeight);
        // Регистрируем слушатель клавиш
        this.addKeyListener(this);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Устанавливаем флаги при нажатии клавиш
        if (e.getKeyCode()==KeyEvent.VK_LEFT) isLeft = true;
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) isRight = true;
        if (e.getKeyCode()==KeyEvent.VK_UP) isUp = true;
        if (e.getKeyCode()==KeyEvent.VK_DOWN) isDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Сбрасываем флаги при отпускании клавиш
        if (e.getKeyCode()==KeyEvent.VK_LEFT) isLeft = false;
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) isRight = false;
        if (e.getKeyCode()==KeyEvent.VK_UP) isUp = false;
        if (e.getKeyCode()==KeyEvent.VK_DOWN) isDown = false;
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
    }

    public void animate() {
        int newX = player.getPositionX();
        int newY = player.getPositionY();

        // Изменяем координаты в зависимости от нажатых клавиш
        if (isLeft) newX -= player.getVx();
        if (isRight) newX += player.getVx();
        if (isUp) newY -= player.getVy();  // Исправлено: "-"
        if (isDown) newY += player.getVy();

        // Ограничение движения в пределах экрана
        newX = Math.max(0, Math.min(newX, ScreenWidth - SpriteWidth));
        newY = Math.max(0, Math.min(newY, ScreenHeight - SpriteHeight));

        // Обновляем позицию игрока и перерисовываем экран
        player.setPositionX(newX);
        player.setPositionY(newY);
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
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

