package tools;

public class Variance {
    public static double computeVariance(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int w, int h) {
        double redVariance = computeVarianceCanal(red, rowTL, colTL, w, h);
        double greenVariance = computeVarianceCanal(green, rowTL, colTL, w, h);
        double blueVariance = computeVarianceCanal(blue, rowTL, colTL, w, h);
        
        return (redVariance + greenVariance + blueVariance) / 3;
    }
    
    public static double computeVarianceCanal(int[][] matrix, int rowTL, int colTL, int w, int h) {
        if (matrix == null || w == 0 || h == 0) {
            return 0.0;
        }        
        int N = w * h;
        long sum = sum(matrix, rowTL, colTL, rowTL + h - 1, colTL + w - 1);
        double mean = (double) sum / N;
        double jumlahSelisihKuadrat = helperVariance(matrix, mean, rowTL, colTL, rowTL + h - 1, colTL + w - 1);
        
        return (double) jumlahSelisihKuadrat / N;
    }
    
    private static long sum(int[][] matrix, int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow && startCol == endCol) {
            return matrix[startRow][startCol];
        }
        if (startRow == endRow && startCol + 1 == endCol) {
            return matrix[startRow][startCol] + matrix[startRow][endCol];
        }
        if (startRow + 1 == endRow && startCol == endCol) {
            return matrix[startRow][startCol] + matrix[endRow][startCol];
        }
        int midRow = startRow + (endRow - startRow) / 2;
        int midCol = startCol + (endCol - startCol) / 2;
        long sum1 = sum(matrix, startRow, startCol, midRow, midCol);
        long sum2 = sum(matrix, startRow, midCol + 1, midRow, endCol);
        long sum3 = sum(matrix, midRow + 1, startCol, endRow, midCol);
        long sum4 = sum(matrix, midRow + 1, midCol + 1, endRow, endCol);
        
        return sum1 + sum2 + sum3 + sum4;
    }
    
    private static double helperVariance(int[][] matrix, double mean, int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow && startCol == endCol) {
            double diff = matrix[startRow][startCol] - mean;
            return diff * diff;
        }
        if (startRow == endRow && startCol + 1 == endCol) {
            double diff1 = matrix[startRow][startCol] - mean;
            double diff2 = matrix[startRow][endCol] - mean;
            return (diff1 * diff1) + (diff2 * diff2);
        }
        if (startRow + 1 == endRow && startCol == endCol) {
            double diff1 = matrix[startRow][startCol] - mean;
            double diff2 = matrix[endRow][startCol] - mean;
            return (diff1 * diff1) + (diff2 * diff2);
        }
        int midRow = startRow + (endRow - startRow) / 2;
        int midCol = startCol + (endCol - startCol) / 2;
        double sum1 = helperVariance(matrix, mean, startRow, startCol, midRow, midCol);
        double sum2 = helperVariance(matrix, mean, startRow, midCol + 1, midRow, endCol);
        double sum3 = helperVariance(matrix, mean, midRow + 1, startCol, endRow, midCol);
        double sum4 = helperVariance(matrix, mean, midRow + 1, midCol + 1, endRow, endCol);
        
        return sum1 + sum2 + sum3 + sum4;
    }
}