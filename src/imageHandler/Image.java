package imageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import tools.Node;
import tools.Quadtree;

public class Image {
    private int width;
    private int height;
    private int[][] red;
    private int[][] green;
    private int[][] blue;

    public Image(int width, int height, int[][] red, int[][] green, int[][] blue) {
        this.width = width;
        this.height = height;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int[][] getRed(){
        return this.red;
    }

    public int[][] getGreen(){
        return this.red;
    }

    public int[][] getBlue(){
        return this.red;
    }

    public static Image loadImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        
        int[][] red = new int[height][width];
        int[][] green = new int[height][width];
        int[][] blue = new int[height][width];
        
        // Extract RGB values from the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Get RGB value (packed in a single integer)
                int rgb = bufferedImage.getRGB(x, y);
                
                // Extract individual RGB components (each 0-255)
                int redVal = (rgb >> 16) & 0xFF;
                int greenVal = (rgb >> 8) & 0xFF;
                int blueVal = rgb & 0xFF;
                
                // Store values in the matrix
                red[y][x] = redVal;
                green[y][x] = greenVal;
                blue[y][x] = blueVal;

            }
        }
         
        Image image = new Image(width, height, red, green, blue);
        return image;
    }

    // method save image from quadtree --dibuat lebih simpel (opt)
    public BufferedImage getCompressedImage(Quadtree quadtree){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[][] newMatrix = extractCompressedMatrix(quadtree);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newImage.setRGB(j, i, newMatrix[i][j]);
            }
        }
        return newImage;
    }

    public static int[][] extractCompressedMatrix(Quadtree quadtree){
        int height = quadtree.getImage().getHeight();
        int width = quadtree.getImage().getWidth();
        int[][] newRGBMatrix = new int[height][width];  

        matrixFromQuadtree(quadtree.getRoot(), newRGBMatrix, 0, 0, height, width);
        return newRGBMatrix;
    }

    public static void matrixFromQuadtree(Node node, int[][] newImage, int rowTL, int colTL, int h, int w){
        if (node.getIsLeaf()){
            for (int i = rowTL; i < rowTL + h; i++){
                for (int j = colTL; j < colTL + w; j++){
                    int red = node.getRedValue() << 16;
                    int green = node.getGreenValue() << 8;
                    int blue = node.getBlueValue();
                    int rgb = red | green | blue;
                    newImage[i][j] = rgb;
                }
            }
        } else {
            matrixFromQuadtree(node.getTopLeft(), newImage, rowTL, colTL, h/2, w/2);
            matrixFromQuadtree(node.getTopRight(), newImage, rowTL, colTL + w/2, h/2, w/2);
            matrixFromQuadtree(node.getBotLeft(), newImage, rowTL + h/2, colTL, h/2, w/2);
            matrixFromQuadtree(node.getBotRight(), newImage, rowTL + h/2, colTL + w/2, h/2, w/2);
        }
    }
}