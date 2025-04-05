package errorMeasurement;

public class MAD {
    public static double computeMAD(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int h, int w){
        double redMAD = computeMADCanal(red, rowTL, colTL, h, w);
        double greenMAD = computeMADCanal(green, rowTL, colTL, h, w);
        double blueMAD = computeMADCanal(blue, rowTL, colTL, h, w);        
        return (redMAD + greenMAD + blueMAD)/3;
    }

    public static double computeMADCanal(int[][] matrix, int rowTL, int colTL, int h, int w){
        // adjust range index
        int totElement = w * h;
        int[] flatArray = new int[totElement];
        int k = 0, sum = 0;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                flatArray[k++] = matrix[i + rowTL][j + colTL];
                sum += matrix[i + rowTL][j + colTL];
            }
        }

        double mean = sum / totElement;

        double sumDev = 0;
        for (int value : flatArray){
            sumDev += Math.abs(value - mean);
        }

        return sumDev / totElement;
    }
}
