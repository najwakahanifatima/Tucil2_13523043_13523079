package errorMeasurement;

public class SSIM {
    // not final yet (determining K1 K2 and W)
    public static double computeSSIM(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int h, int w, int avgRed, int avgGreen, int avgBlue){
        // weight
        double wRed = 0.30;
        double wGreen = 0.59;
        double WBlue = 0.11;

        double ssimRed = computeSSIMCanal(red, rowTL, colTL, h, w, avgRed);
        double ssimGreen = computeSSIMCanal(green, rowTL, colTL, h, w, avgGreen);
        double ssimBlue = computeSSIMCanal(blue, rowTL, colTL, h, w, avgBlue);

        return ((wRed * ssimRed) + (wGreen * ssimGreen) + (WBlue * ssimBlue));
    }
    
    public static double computeSSIMCanal(int[][] matrix, int rowTL, int colTL, int h, int w, int avgColor){
        double K1 = 0.01, K2 = 0.03;

        double C1 = Math.pow(K1 * 255, 2);
        double C2 = Math.pow(K2 * 255, 2);

        int totElement = w * h;
        int[] flatArray = new int[totElement];

        // calculate mean and store ke flat array
        int k = 0;
        long sum = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                flatArray[k++] = matrix[i + rowTL][j + colTL];
                sum += matrix[i + rowTL][j + colTL];
            }
        }
        double meanX = (double) sum / totElement;

        // calculate standard deviation
        double sumVar = 0;
        double cov = 0; // because y is constant
        double varY = 0; 
        for (int pixel : flatArray){
            double diffX = pixel - meanX;
            sumVar += diffX * diffX;
        }
        double varX = sumVar / (totElement - 1);

        // calculate ssim
        double denom = ((2 * meanX * avgColor) + C1) * ((2 * cov) + C2);
        double nom = (Math.pow(meanX,2) + Math.pow(avgColor, 2) + C1) * (varX + varY + C2);

        return denom / nom;
    }
}

