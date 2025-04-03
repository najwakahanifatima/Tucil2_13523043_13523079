import java.util.ArrayList;

public class Entropy{
    public static double computeEntropy(int[][] red, int[][] green, int[][] blue){
        double entropyRed = computeEntropyCanal(red);
        double entropyGreen = computeEntropyCanal(green);
        double entropyBlue = computeEntropyCanal(blue);

        return (entropyRed + entropyGreen + entropyBlue)/3;
    }

    public static double computeEntropyCanal(int[][] matrix){
        // calculate value occurance in matrix (might use hash map) -- later
        ArrayList<Tuple2<Integer, Integer>> countPixel = new ArrayList<>();
        
        boolean found;
        for (int[] row : matrix){
            for (int value : row){
                found = false;
                for (Tuple2<Integer, Integer> pixel : countPixel){
                    if (pixel.getItem1().equals(value)){
                        pixel.setItem2(pixel.getItem2() + 1);
                        found = true;
                    }
                }
                if (!found){
                    countPixel.add(new Tuple2<>(value, 1));
                }
            }
        }

        // find probability and entropy
        double sum = 0.0;
        int numOfPixel = countPixel.size();
        for (int[] row : matrix){
            for (int value : row){
                for (Tuple2<Integer, Integer> pixel : countPixel){
                    if (pixel.getItem1().equals(value)){
                        double prob = pixel.getItem2() /  numOfPixel;
                        sum += (prob * (Math.log(prob) / Math.log(2)));
                    }
                }
            }
        }

        return (-1 * sum);
    }
}