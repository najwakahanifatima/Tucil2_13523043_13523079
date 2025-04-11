import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import tools.GIF;
import tools.Image;
import tools.Input;
import tools.Quadtree;
import tools.TargettedCompressor;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Input.displayWelcome();

        // get input
        String inputImagePath = Input.readInputPath();
        int errorMethod = Input.readInputErrorMethod(scanner);
        double threshold = Input.readInputThreshold(scanner, errorMethod);
        try{
            Image image = Image.loadImage(inputImagePath);
            int maxBlockSize = Math.min(image.getWidth(), image.getHeight());
            int minBlockSize = Input.readInputMinBlockSize(scanner, maxBlockSize);
            double targetCompressionPercentage = Input.readInputTargetCompression(scanner);
            String outputImagePath = Input.readOutputPath(scanner);
            
            long startTime = System.currentTimeMillis();
    
            if (targetCompressionPercentage > 0){
                try {
                    TargettedCompressor.compressWithTargetRatio(inputImagePath, outputImagePath, errorMethod, minBlockSize, targetCompressionPercentage);
                } catch (IOException e) {
                    System.out.println("Error compressing image: " + e.getMessage());
                }
            } else{
                try {                
                    // call quadtree algorithm
                    Quadtree compressor = new Quadtree(errorMethod, threshold, minBlockSize, image);
                    compressor.construct(image.getRed(), image.getGreen(), image.getBlue()); //this will set the root of quadtree
                    
                    // create compressed image
                    BufferedImage newImage = image.getCompressedImage(compressor);
                    String extension = outputImagePath.substring(outputImagePath.lastIndexOf('.') + 1);
                    ImageIO.write(newImage, extension, new File(outputImagePath));                
                    
                    // result
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;     
                    
                    System.out.println("\n===================================================================");
                    System.out.println("                    HASIL KOMPRESI                                ");
                    System.out.println("===================================================================");
                    System.out.println("Waktu eksekusi: " + duration + " ms");
                    
                    File originaFile = new File(inputImagePath);
                    long originalSize = originaFile.length();
                    File compressedFile = new File(outputImagePath);
                    long compressedSize = compressedFile.length();
                    System.out.println("Ukuran gambar asli: " + originalSize + " bytes");
                    System.out.println("Ukuran gambar terkompresi: " + compressedSize + " bytes");
                    double compressionPercentage = (1.0 - ((double) compressedSize / originalSize)) * 100;
                    System.out.printf("Persentase kompresi: %.2f%%\n", compressionPercentage);
                    
                    System.out.println("Kedalaman pohon: " + Quadtree.getDepth(compressor.getRoot()));
                    System.out.println("Banyak simpul pada pohon: " + compressor.getNumberOfNode());
                    System.out.println("Gambar hasil kompresi disimpan di: \n" + outputImagePath);
                    System.out.println("===================================================================");
                    
                    boolean yesGIF = Input.readOptionGIF(scanner);
                    if(yesGIF){
                        String outputGIF = Input.readOutputGIFPath(scanner);
                        GIF.makeGIF(compressor, outputGIF);
                    }
                    System.out.println("===================================================================");
                } catch (IOException e) {
                    System.out.println("Error compressing image: " + e.getMessage());
                }
            }            
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

}