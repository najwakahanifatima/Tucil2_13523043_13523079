package tools;

public class MPD {
    public static double computeMPD(int[][] red, int[][] green, int[][] blue){
        double redMPD = computeMPDCanal(red);
        double greenMPD = computeMPDCanal(green);
        double blueMPD = computeMPDCanal(blue);
        
        return (redMPD + greenMPD + blueMPD)/3;
    }

    public static double computeMPDCanal(int[][] matrix){
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0.0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        int[] result = findMinMax(matrix, 0, 0, rows - 1, cols - 1);
        int min = result[0];
        int max = result[1];
        
        return max - min;
    }

    private static int[] findMinMax(int[][] matrix, int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow && startCol == endCol) {
            return new int[] { matrix[startRow][startCol], matrix[startRow][startCol] };
        }
        
        if (startRow == endRow && startCol + 1 == endCol) {
            int min = Math.min(matrix[startRow][startCol], matrix[startRow][endCol]);
            int max = Math.max(matrix[startRow][startCol], matrix[startRow][endCol]);
            return new int[] { min, max };
        }
        
        if (startRow + 1 == endRow && startCol == endCol) {
            int min = Math.min(matrix[startRow][startCol], matrix[endRow][startCol]);
            int max = Math.max(matrix[startRow][startCol], matrix[endRow][startCol]);
            return new int[] { min, max };
        }
        int midRow = startRow + (endRow - startRow) / 2;
        int midCol = startCol + (endCol - startCol) / 2;
        
        int[] q1 = findMinMax(matrix, startRow, startCol, midRow, midCol);
        int[] q2 = findMinMax(matrix, startRow, midCol + 1, midRow, endCol);
        int[] q3 = findMinMax(matrix, midRow + 1, startCol, endRow, midCol);
        int[] q4 = findMinMax(matrix, midRow + 1, midCol + 1, endRow, endCol);
        
        int min = Math.min(Math.min(q1[0], q2[0]), Math.min(q3[0], q4[0]));
        int max = Math.max(Math.max(q1[1], q2[1]), Math.max(q3[1], q4[1]));
        
        return new int[] { min, max };
    }
}