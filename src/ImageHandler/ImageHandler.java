package ImageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {
    public static Image loadImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        
        RGBMatrix rgbMatrix = new RGBMatrix(width, height);
        
        // Extract RGB values from the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get RGB value (packed in a single integer)
                int rgb = bufferedImage.getRGB(x, y);
                
                // Extract individual RGB components (each 0-255)
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                
                // Store values in the RGBMatrix
                rgbMatrix.setRGB(y, x, red, green, blue); // Note: y is row, x is column
            }
        }
        
        Image image = new Image(width, height, rgbMatrix);
        return image;
    }
}

class Image {
    private int width;
    private int height;
    private RGBMatrix rgbMatrix;

    public Image(int width, int height, RGBMatrix rgbMatrix) {
        this.width = width;
        this.height = height;
        this.rgbMatrix = rgbMatrix;
    }
}