package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class TargettedCompressor {
    private static final double INITIAL_THRESHOLD = 10.0;
    private static final int MAX_ITERATIONS = 20;
    private static final double TOLERANCE = 0.001;
    private static double THRESHOLD_TOLERANCE = 0.01;
    
    public static double compressWithTargetRatio(String imagePath, String outputPath, int errorMethod, int minBlockSize, double targetCompression) throws IOException {
        if (targetCompression == 0.0) {
            return -1.0;
        }
        
        long startTime = System.currentTimeMillis();
        Image image = Image.loadImage(imagePath);
        File inputFile = new File(imagePath);
        long originalSize = inputFile.length();

        double lowerThreshold = 0.0;
        double upperThreshold = 500.0;
        double currentThreshold = INITIAL_THRESHOLD;
        switch (errorMethod) {
            case 1:
                upperThreshold = 500.0;
                break;
            case 2:
                // MAD
                upperThreshold = 255.0;
                break;
            case 3:
                // MPD
                upperThreshold = 255.0;
                break;
            case 4:
                // Entropy
                upperThreshold = 255.0;
                break;
            case 5:
                // SSIM (bonus)
                upperThreshold = 10.0;
                currentThreshold = 1.0;
                THRESHOLD_TOLERANCE = 0.00001;
                break;
            default:
                // case default, didn't proceed
                break;
        }
        double bestThreshold = currentThreshold;
        double bestRatio;
        boolean done;

        Quadtree quadtree = new Quadtree(errorMethod, currentThreshold, minBlockSize, image);
        quadtree.construct(image.getRed(), image.getGreen(), image.getBlue());
        BufferedImage compressedImage = image.getCompressedImage(quadtree);
        String extension = outputPath.substring(outputPath.lastIndexOf('.') + 1);
        ImageIO.write(compressedImage, extension, new File(outputPath));
        File compressedFile = new File(outputPath);
        long compressedSize = compressedFile.length();

        double currentCompressionRatio = 1.0 - ((double) compressedSize / originalSize);
        System.out.println(originalSize);
        System.out.println(compressedSize);
        System.out.println(upperThreshold-lowerThreshold);
        System.out.println("Iterasi 1: Threshold = " + 
        String.format("%.2f", currentThreshold) + ", Jumlah Node = " + 
        quadtree.getNumberOfNode() + ", Rasio Kompresi = " + 
                            String.format("%.2f", currentCompressionRatio * 100) + "%");
        bestRatio = currentCompressionRatio;
                            
        System.out.println("Threshold terbaik: " + String.format("%.2f", bestThreshold) + ", Rasio terbaik: " + String.format("%.2f", bestRatio * 100) + "%");
        
        if (Math.abs(currentCompressionRatio - targetCompression) <= TOLERANCE) {
            done = true;
        }

        if (currentCompressionRatio > targetCompression) {
            upperThreshold = currentThreshold;
            currentThreshold = (lowerThreshold + upperThreshold) / 2;
        } else {
            lowerThreshold = currentThreshold;
            currentThreshold = (lowerThreshold + upperThreshold) / 2;
        }
        
        for (int iteration = 2; iteration <= MAX_ITERATIONS; iteration++) {
            quadtree = new Quadtree(errorMethod, currentThreshold, minBlockSize, image);
            quadtree.construct(image.getRed(), image.getGreen(), image.getBlue());
            compressedImage = image.getCompressedImage(quadtree);
            extension = outputPath.substring(outputPath.lastIndexOf('.') + 1);
            ImageIO.write(compressedImage, extension, new File(outputPath));
            compressedFile = new File(outputPath);
            compressedSize = compressedFile.length();

            currentCompressionRatio = 1.0 - ((double) compressedSize / originalSize);
            System.out.println(originalSize);
            System.out.println(compressedSize);
            System.out.println(upperThreshold-lowerThreshold);
            System.out.println("Iterasi " + iteration + ": Threshold = " + 
            String.format("%.2f", currentThreshold) + ", Jumlah Node = " + 
            quadtree.getNumberOfNode() + ", Rasio Kompresi = " + 
            String.format("%.2f", currentCompressionRatio * 100) + "%");
            if (Math.abs(currentCompressionRatio - targetCompression) < 
                Math.abs(bestRatio - targetCompression)) {
                bestThreshold = currentThreshold;
                bestRatio = currentCompressionRatio;
            }
                              
            System.out.println("Threshold terbaik: " + String.format("%.2f", bestThreshold) + ", Rasio terbaik: " + String.format("%.2f", bestRatio * 100) + "%");
            
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
        
        BufferedImage finalCompressedImage = image.getCompressedImage(finalQuadtree);
        ImageIO.write(finalCompressedImage, extension, new File(outputPath));
        
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
        File finalCompressedFile = new File(outputPath);
        long compressedImageSize = finalCompressedFile.length();
        double compressionPercentage = (1.0 - ((double) compressedImageSize / originalFileSize)) * 100;
        System.out.println("Ukuran gambar terkompresi: " + compressedImageSize + " bytes");
        System.out.printf("Persentase kompresi: %.2f%%\n", compressionPercentage);
        
        System.out.println("Kedalaman pohon: " + Quadtree.getDepth(finalQuadtree.getRoot()));
        System.out.println("Banyak simpul pada pohon: " + finalQuadtree.getNumberOfNode());
        System.out.println("Gambar hasil kompresi disimpan di: \n" + outputPath);
        System.out.println("===================================================================");
        if(Math.abs(compressionPercentage - targetCompression) > TOLERANCE){
            System.out.println("Note: hasil persentase kompresi tidak dapat memenuhi\ntarget kompresi karena tidak termasuk dalam range\npersentase kompresi yang dapat dilakukan pada file ini.");
            System.out.println("Hal ini dapat terjadi jika ukuran file image asli\nterlalu kecil atau terlalu besar.");
            System.out.println("===================================================================");
        }
        boolean makeGIF = Input.readOptionGIF(new Scanner(System.in));
        if (makeGIF) {
            String outputGIFPath = Input.readOutputGIFPath(new Scanner(System.in));
            GIF.makeGIF(finalQuadtree, outputGIFPath);
        }
        System.out.println("===================================================================");
        
        return bestRatio;
    }
}