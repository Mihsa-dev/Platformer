package platformer.render;

import platformer.gameCore.entities.GameObj;
import platformer.gameCore.level.LevelManager;
import platformer.gameCore.entities.Player;
import platformer.input.InputListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static platformer.gameCore.utils.Constant.*;

public class Platformer extends JPanel{

    public static Player player;
    private final Thread thread;
    private final LevelManager levelManager;
    private final InputListener listener;

    public Platformer() throws IOException {
        super();
        setFocusable(true);

        this.levelManager = new LevelManager(); // Инициализация менеджера уровней
        GameObj.levelManager = this.levelManager;
        player = new Player("sprites/blackPlayer.jpg",
                levelManager.getCurrentLevel().getPlayerPosX(),
                levelManager.getCurrentLevel().getPlayerPosY());
        useAllStart();

        // Регистрируем слушатель клавиш
        listener = new InputListener();
        this.addKeyListener(listener);

        // Создаем и запускаем поток анимации
        thread = new MoveThread(this);
        thread.setDaemon(true); // Поток завершится при закрытии приложения
        thread.start();
    }

    private void useAllStart(){
        // после загрузки уровня вызываем у всех объектов start
        for (GameObj[] arr : levelManager.getCurrentLevel().getGameGrid()){
            for (GameObj obj : arr){
                obj.start();
            }
        }
        for (GameObj obj : levelManager.getCurrentLevel().getMovables()){
            obj.start();
        }
        player.start();
    }

    @Override
    public void paint(Graphics g) {
        var g2d = (Graphics2D)g;

        // Отрисовка черного фона
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,getWidth(),getHeight());


        // draw all not movable objects
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

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        Platformer platformer = new Platformer();
//        GameObj.levelManager = platformer.levelManager;

        platformer.setSize(ScreenWidth,ScreenHeight);
        platformer.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        frame.add(platformer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void animate() {
        player.updater();
        for (GameObj obj : levelManager.getCurrentLevel().getMovables()){
            obj.updater();
        }

        if (player.isMoved())
            player.collide(player, player, levelManager.getCurrentLevel().getGameGrid(),
                    levelManager.getCurrentLevel().getMovables(), 0);

        for (GameObj obj : levelManager.getCurrentLevel().getMovables()){
            if(obj.isMoved())
                obj.collide(obj, player, levelManager.getCurrentLevel().getGameGrid(),
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

