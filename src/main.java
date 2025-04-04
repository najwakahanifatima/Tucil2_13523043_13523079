import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import tools.Input;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Input.displayWelcome();
        String imagePath = Input.readInputPath();
        System.out.println("File ditemukan: " + imagePath);
        // 1. Variance
        // 2. Mean Absolute Deviation
        // 3. Max Pixel Difference
        // 4. Entropy
        int errorMethod = Input.readInputErrorMethod(scanner);
        double threshold = Input.readInputThreshold(scanner);
        int minBlockSize = Input.readInputMinBlockSize(scanner);
        double targetCompressionPercentage = Input.readInputTargetCompression(scanner);
        String outputImagePath = Input.readOutputPath(scanner);
        long startTime = System.currentTimeMillis();
        // try {
        //     //compressImage(imagePath, errorMethod, threshold, minBlockSize, targetCompressionPercentage, outputImagePath);
        //     long endTime = System.currentTimeMillis();
        //     long duration = endTime - startTime;
        //     stats = QuadTree.getStats
        //     System.out.println("\n===================================================================");
        //     System.out.println("                    HASIL KOMPRESI                                ");
        //     System.out.println("===================================================================");
        //     System.out.println("Waktu eksekusi: " + duration + " ms");
        //     System.out.println("Ukuran gambar asli: " + stats.getOriginalSize() + " bytes");
        //     System.out.println("Ukuran gambar terkompresi: " + stats.getCompressedSize() + " bytes");
        //     System.out.printf("Persentase kompresi: %.2f%%\n", stats.getCompressionPercentage() * 100);
        //     System.out.println("Kedalaman pohon: " + stats.getTreeDepth());
        //     System.out.println("Banyak simpul pada pohon: " + stats.getNodeCount());
        //     System.out.println("Gambar hasil kompresi disimpan di: " + outputImagePath);
        // } catch (IOException e) {
        //     System.out.println("Error compressing image: " + e.getMessage());
        // }
            
    }

}