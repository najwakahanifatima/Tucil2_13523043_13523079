package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TargettedCompressor {
    private static final double INITIAL_THRESHOLD = 10.0;
    private static final int MAX_ITERATIONS = 20;
    private static final double TOLERANCE = 0.001;
    private static final double THRESHOLD_TOLERANCE = 0.01;
    
    public static double compressWithTargetRatio(String imagePath, String outputPath, int errorMethod, int minBlockSize, double targetCompression) throws IOException {
        if (targetCompression == 0.0) {
            return -1.0;
        }
        
        long startTime = System.currentTimeMillis();
        Image image = Image.loadImage(imagePath);
        double originalSize = image.getWidth() * image.getHeight() * 3;
        
        double lowerThreshold = 0.1;
        double upperThreshold = 500.0;
        double currentThreshold = INITIAL_THRESHOLD;
        double currentCompressionRatio = 0.0;
        double bestThreshold = currentThreshold;
        double bestRatio = 0.0;
        
        for (int iteration = 1; iteration <= MAX_ITERATIONS; iteration++) {
            Quadtree quadtree = new Quadtree(errorMethod, currentThreshold, minBlockSize, image);
            quadtree.construct(image.getRed(), image.getGreen(), image.getBlue());
            
            // setiap node leaf menyimpan posisi (2 int) dan 3 nilai warna (3 byte)
            double compressedSize = quadtree.getNumberOfNode() * (2 * 4 + 3); // 2 ints (x,y) dan 3 bytes (RGB)
            currentCompressionRatio = 1.0 - (compressedSize / originalSize);

            System.out.println("Iterasi " + iteration + ": Threshold = " + 
                              String.format("%.2f", currentThreshold) + ", Jumlah Node = " + 
                              quadtree.getNumberOfNode() + ", Rasio Kompresi = " + 
                              String.format("%.2f", currentCompressionRatio * 100) + "%");

            // jika rasio paling mendekati target, simpan threshold
            if (Math.abs(currentCompressionRatio - targetCompression) < 
                Math.abs(bestRatio - targetCompression)) {
                bestThreshold = currentThreshold;
                bestRatio = currentCompressionRatio;
            }
            
            // stop jika sudah cukup dekat dengan target
            if (Math.abs(currentCompressionRatio - targetCompression) <= TOLERANCE) {
                break;
            }

            // stop jika upper dan lower threshold sama
            if(upperThreshold - lowerThreshold < THRESHOLD_TOLERANCE){
                break;
            }

            // sesuaikan threshold untuk iterasi berikutnya
            if (currentCompressionRatio > targetCompression) {
                upperThreshold = currentThreshold;
                currentThreshold = (lowerThreshold + upperThreshold) / 2;
            } else {
                lowerThreshold = currentThreshold;
                currentThreshold = (lowerThreshold + upperThreshold) / 2;
            }
        }

        Quadtree finalQuadtree = new Quadtree(errorMethod, bestThreshold, minBlockSize, image);
        finalQuadtree.construct(image.getRed(), image.getGreen(), image.getBlue());
        
        BufferedImage compressedImage = image.getCompressedImage(finalQuadtree);
        String extension = outputPath.substring(outputPath.lastIndexOf('.') + 1);
        ImageIO.write(compressedImage, extension, new File(outputPath));
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;   
        System.out.println("\n===================================================================");
        System.out.println("                    HASIL KOMPRESI                                ");
        System.out.println("===================================================================");
        System.out.println("Waktu eksekusi: " + duration + " ms");
        System.out.println("Threshold yang digunakan: " + String.format("%.2f", bestThreshold));
        System.out.println("Ukuran gambar asli: " + originalSize + " bytes");

        File originaFile = new File(imagePath);
        long originalFileSize = originaFile.length();
        File compressedFile = new File(outputPath);
        long compressedImageSize = compressedFile.length();
        double compressionPercentage = (1.0 - ((double) compressedImageSize / originalFileSize)) * 100;
        System.out.println("Ukuran gambar terkompresi: " + compressedImageSize + " bytes");
        System.out.printf("Persentase kompresi: %.2f%%\n", compressionPercentage);
        
        System.out.println("Kedalaman pohon: " + Quadtree.getDepth(finalQuadtree.getRoot()));
        System.out.println("Banyak simpul pada pohon: " + finalQuadtree.getNumberOfNode());
        System.out.println("Gambar hasil kompresi disimpan di: \n" + outputPath);
        
        return bestRatio;
    }
}