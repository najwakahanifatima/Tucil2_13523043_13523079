package tools;

import java.io.File;
import java.util.Scanner;

public class Input {
    public static String readInputPath() {
        Scanner scanner = new Scanner(System.in);
        String fileName;

        while (true) {
            System.out.println("=================================================================");
            System.out.println("Masukkan alamat absolut file gambar yang ingin dikompresi: ");
            fileName = scanner.nextLine();
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("Error: File tidak ditemukan! Silakan masukkan kembali.");
                continue;
            }

            if (!isAbsolutePath(fileName)) {
                System.out.println("Error: Harap masukkan alamat absolut!");
                continue;
            }

            if (!file.isFile() || !isImageFile(fileName)) {
                System.out.println("Error: File bukan file gambar yang valid! Silakan masukkan kembali.");
                continue;
            }
            return fileName;
        }
    }

    public static int readInputErrorMethod(Scanner scanner) {
        String[] errorMethods = { "Variance", "Mean Absolute Deviation", "Max Pixel Difference", "Entropy", "Structural Similarity Index" };
        int choice;
    
        while (true) {
            System.out.println("=================================================================");
            System.out.println("Metode perhitungan error yang tersedia: ");
            for (int i = 0; i < errorMethods.length; i++) {
                System.out.println((i + 1) + ". " + errorMethods[i]);
            }
            System.out.print("Masukkan pilihan Anda: ");
            
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 5) {
                    return choice;
                }
                System.out.println("Error: Pilihan tidak valid! Silakan masukkan angka 1-4.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Masukkan harus berupa angka.");
            }
        }
    }
    
    public static boolean isAbsolutePath(String path) {
        File file = new File(path);
        return file.isAbsolute();
    }
    
    public static boolean isImageFile(String fileName) {
        String[] imageExtensions = { ".jpg", ".jpeg", ".png"};
        for (String extension : imageExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
    public static double readInputThreshold(Scanner scanner) {
        double threshold = 0.0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("=================================================================");
            System.out.print("Masukkan nilai ambang batas (threshold): ");
            try {
                threshold = Double.parseDouble(scanner.nextLine());
                if (threshold >= 0) {
                    validInput = true;
                } else {
                    System.out.println("Input tidak valid. Masukkan nilai ambang batas positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nilai numerik.");
            }
        }
        return threshold;
    }
    
    public static int readInputMinBlockSize(Scanner scanner) {
        int minBlockSize = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("=================================================================");
            System.out.print("Masukkan ukuran blok minimum: ");
            try {
                minBlockSize = Integer.parseInt(scanner.nextLine());
                if (minBlockSize > 0) {
                    validInput = true;
                } else {
                    System.out.println("Input tidak valid. Ukuran blok minimum harus lebih besar dari 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nilai numerik.");
            }
        }
        
        return minBlockSize;
    }

    public static double readInputTargetCompression(Scanner scanner) {
        double targetCompressionPercentage = 0.0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("=================================================================");
            System.out.print("Masukkan target persentase kompresi (0.0-1.0, 0 untuk menonaktifkan): ");
            try {
                targetCompressionPercentage = Double.parseDouble(scanner.nextLine());
                if (targetCompressionPercentage >= 0.0 && targetCompressionPercentage <= 1.0) {
                    validInput = true;
                } else {
                    System.out.println("Input tidak valid. Masukkan nilai antara 0.0 dan 1.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nilai numerik.");
            }
        }
        
        return targetCompressionPercentage;
    }
    public static String readOutputPath(Scanner scanner) {
        System.out.println("=================================================================");
        System.out.print("Masukkan alamat absolut untuk gambar hasil kompresi: ");
        return scanner.nextLine();
    }

    public static void displayWelcome() {
        System.out.println("=================================================================");
        System.out.println("                    PROGRAM KOMPRESI GAMBAR                      ");
        System.out.println("=================================================================\n\n");
    }
    // display penjelasan program setelah displaywelcome
    // display start prrogram and quit program
}