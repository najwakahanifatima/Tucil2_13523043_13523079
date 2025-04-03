public class MAD {
    public static double computeMAD(int[][] red, int[][] green, int[][] blue){
        double redMAD = computeMADCanal(red);
        double greenMAD = computeMADCanal(green);
        double blueMAD = computeMADCanal(blue);
        
        return (redMAD + greenMAD + blueMAD)/3;
    }

    public static double computeMADCanal(int[][] matrix){
        int totElement = matrix.length * matrix[0].length;
        int[] flatArray = new int[totElement];
        int i = 0, sum = 0;

        for (int[] row : matrix){
            for (int value : row){
                flatArray[i++] = value;
                sum += value;
            }
        }
        double mean = sum / totElement;

        int sumDev = 0;
        for (int value : flatArray){
            sumDev += Math.abs(value - mean);
        }

        return (double) sumDev / totElement;
    }
}
