package imageHandler;

public class RGBMatrix {
    private Matrix redMatrix;
    private Matrix greenMatrix;
    private Matrix blueMatrix;
    private int width;
    private int height;
    
    public RGBMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.redMatrix = new Matrix(height, width);
        this.greenMatrix = new Matrix(height, width);
        this.blueMatrix = new Matrix(height, width);
    }
    
    public RGBMatrix(Matrix redMatrix, Matrix greenMatrix, Matrix blueMatrix) {
        if (redMatrix.getRows() != greenMatrix.getRows() || 
            redMatrix.getRows() != blueMatrix.getRows() ||
            redMatrix.getCols() != greenMatrix.getCols() || 
            redMatrix.getCols() != blueMatrix.getCols()) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
        
        this.height = redMatrix.getRows();
        this.width = redMatrix.getCols();
        this.redMatrix = redMatrix;
        this.greenMatrix = greenMatrix;
        this.blueMatrix = blueMatrix;
    }
    
    public RGBMatrix(int[][][] rgbArray) {
        if (rgbArray.length != 3) {
            throw new IllegalArgumentException("RGB array must have 3 channels");
        }
        
        this.height = rgbArray[0].length;
        this.width = (height > 0) ? rgbArray[0][0].length : 0;
        
        int[][] redData = new int[height][width];
        int[][] greenData = new int[height][width];
        int[][] blueData = new int[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                redData[y][x] = rgbArray[0][y][x];
                greenData[y][x] = rgbArray[1][y][x];
                blueData[y][x] = rgbArray[2][y][x];
            }
        }
        
        this.redMatrix = new Matrix(redData);
        this.greenMatrix = new Matrix(greenData);
        this.blueMatrix = new Matrix(blueData);
    }
    
    
    public int[] getRGB(int row, int col) {
        int[] rgb = new int[3];
        rgb[0] = redMatrix.get(row, col);
        rgb[1] = greenMatrix.get(row, col);
        rgb[2] = blueMatrix.get(row, col);
        return rgb;
    }

    public void setRGB(int row, int col, int r, int g, int b) {
        redMatrix.set(row, col, r);
        greenMatrix.set(row, col, g);
        blueMatrix.set(row, col, b);
    }
    
    public RGBMatrix getSubMatrix(int startRow, int startCol, int numRows, int numCols) {
        Matrix redSubMatrix = redMatrix.getSubMatrix(startRow, startCol, numRows, numCols);
        Matrix greenSubMatrix = greenMatrix.getSubMatrix(startRow, startCol, numRows, numCols);
        Matrix blueSubMatrix = blueMatrix.getSubMatrix(startRow, startCol, numRows, numCols);
        
        return new RGBMatrix(redSubMatrix, greenSubMatrix, blueSubMatrix);
    }
    
    public int[] getAverageRGBForRegion(int startRow, int startCol, int numRows, int numCols) {
        int[] avgRGB = new int[3];
        
        double avgRed = redMatrix.calculateRegionAverage(startRow, startCol, numRows, numCols);
        double avgGreen = greenMatrix.calculateRegionAverage(startRow, startCol, numRows, numCols);
        double avgBlue = blueMatrix.calculateRegionAverage(startRow, startCol, numRows, numCols);
        
        avgRGB[0] = (int) Math.round(avgRed);
        avgRGB[1] = (int) Math.round(avgGreen);
        avgRGB[2] = (int) Math.round(avgBlue);
        
        return avgRGB;
    }
   
    public int[][][] to3DArray() {
        int[][][] rgbArray = new int[3][height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgbArray[0][y][x] = redMatrix.get(y, x);
                rgbArray[1][y][x] = greenMatrix.get(y, x);
                rgbArray[2][y][x] = blueMatrix.get(y, x);
            }
        }
        
        return rgbArray;
    }
    
    public Matrix getRedMatrix() {
        return redMatrix;
    }
    
    public Matrix getGreenMatrix() {
        return greenMatrix;
    }
    
    public Matrix getBlueMatrix() {
        return blueMatrix;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
