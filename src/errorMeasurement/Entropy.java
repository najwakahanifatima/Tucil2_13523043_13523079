package errorMeasurement;
import java.util.ArrayList;
import tools.Tuple2;

public class Entropy{
    public static double computeEntropy(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int h, int w){
        double entropyRed = computeEntropyCanal(red, rowTL, colTL, h, w);
        double entropyGreen = computeEntropyCanal(green, rowTL, colTL, h, w);
        double entropyBlue = computeEntropyCanal(blue, rowTL, colTL, h, w);

        return (entropyRed + entropyGreen + entropyBlue)/3;
    }

    public static double computeEntropyCanal(int[][] matrix, int rowTL, int colTL, int h, int w){
        // calculate value occurance in matrix (might use hash map) -- later
        ArrayList<Tuple2<Integer, Integer>> countPixel = new ArrayList<>();
        
        boolean found;

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                found = false;
                for (Tuple2<Integer, Integer> pixel : countPixel){
                    if (pixel.getItem1().equals(matrix[i + rowTL][j + colTL])){
                        int pixelOcc = pixel.getItem2() + 1;
                        pixel.setItem2(pixelOcc);
                        found = true;
                    }
                }
                if (!found){
                    countPixel.add(new Tuple2<>(matrix[i + rowTL][j + colTL], 1));
                }
            }
        }

        // find probability and entropy
        double sum = 0.0;
        int numOfPixel = countPixel.size();
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                for (Tuple2<Integer, Integer> pixel : countPixel){
                    if (pixel.getItem1().equals(matrix[i + rowTL][j + colTL])){
                        double prob = pixel.getItem2() /  numOfPixel;
                        sum += (prob * (Math.log(prob) / Math.log(2)));
                    }
                }
            }
        }

        return (-1 * sum);
    }
}