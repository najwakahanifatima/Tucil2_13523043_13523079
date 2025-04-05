import imageHandler.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import tools.Input;
import tools.Quadtree;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Input.displayWelcome();

        // get input
        String imagePath = Input.readInputPath();
        System.out.println("File ditemukan: " + imagePath);
        int errorMethod = Input.readInputErrorMethod(scanner);
        double threshold = Input.readInputThreshold(scanner); //cek apakah range threshold harus sesuai denga metode error yang dipilih
        int minBlockSize = Input.readInputMinBlockSize(scanner);
        double targetCompressionPercentage = Input.readInputTargetCompression(scanner);
        String outputImagePath = Input.readOutputPath(scanner);
        long startTime = System.currentTimeMillis();

        // start image compression
        try {
            // process image to matrix
            Image image = Image.loadImage(imagePath);
            
            // call quadtree algorithm
            Quadtree compressor = new Quadtree(errorMethod, threshold, minBlockSize, image);
            compressor.construct(image.getRed(), image.getGreen(), image.getBlue()); //this will set the root of quadtree
            BufferedImage newImage = image.getCompressedImage(compressor);
            ImageIO.write(newImage, "jpg", new File("compressed.jpg"));

            // result
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;         

            System.out.println("\n===================================================================");
            System.out.println("                    HASIL KOMPRESI                                ");
            System.out.println("===================================================================");
            System.out.println("Waktu eksekusi: " + duration + " ms");
            // System.out.println("Ukuran gambar asli: " + stats.getOriginalSize() + " bytes");
            // System.out.println("Ukuran gambar terkompresi: " + stats.getCompressedSize() + " bytes");
            // System.out.printf("Persentase kompresi: %.2f%%\n", stats.getCompressionPercentage() * 100);
            System.out.println("Kedalaman pohon: " + compressor.getDepth(compressor.getRoot()));
            System.out.println("Banyak simpul pada pohon: " + compressor.getNumberOfNode());
            // System.out.println("Gambar hasil kompresi disimpan di: " + outputImagePath);
        
        } catch (IOException e) {
            System.out.println("Error compressing image: " + e.getMessage());
        }
            
    }

}