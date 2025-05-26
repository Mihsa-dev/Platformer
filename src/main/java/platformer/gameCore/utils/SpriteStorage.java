package platformer.gameCore.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static platformer.gameCore.utils.Constant.SpriteSize;

public class SpriteStorage {
    private Image[] images;
    private int currentIndex;
    private int timeToSwapImage;

    public SpriteStorage(String... filenames){
        images = new Image[4];
        currentIndex = 0;
        timeToSwapImage = 10;
        for (int i = 0; i < filenames.length; i++) {
            if (filenames[i] != null) {
                loadImage(filenames[i], i);
            }
        }
    }

    private void loadImage(String filename, int index){
        try{
            images[index] = ImageIO.read(new File(filename))
                    .getScaledInstance(SpriteSize,SpriteSize,Image.SCALE_SMOOTH);
        }
        catch (IOException e){
            System.err.println("сука, че с файлом:" + e.getMessage());
        }
    }

    public void increaseCurrentIndex(){
        currentIndex = (currentIndex + 1) % images.length;
    }
    public  void decreaseCurrentIndex(){
        currentIndex = (currentIndex - 1 + images.length) % images.length;
    }
    public void setCurrentIndex(int index){
        if (index >= 0 && index < images.length) {
            currentIndex = index;
        }
    }

    public Image getImage(){
        if (timeToSwapImage > -1) timeToSwapImage -= 1;

        int oldIndex = currentIndex;

        int newIndex = (currentIndex + 2) % 4;
        if (images[newIndex] != null && timeToSwapImage <= 0){
            currentIndex = newIndex;
            timeToSwapImage = 10;
        }
        return images[oldIndex];
    }

    public void setImageByIndex(int index, Image image) {
        if (index >= 0 && index < images.length) {
            images[index] = image;
        }
    }

    public Image getImageByIndex(int index) {
        if (index >= 0 && index < images.length) {
            return images[index];
        }
        return null;
    }
}
