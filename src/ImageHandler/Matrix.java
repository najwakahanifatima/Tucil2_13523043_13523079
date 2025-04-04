package ImageHandler;

public class Matrix {
    private int[][] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }
    
    public Matrix(int[][] data) {
        this.rows = data.length;
        this.cols = (rows > 0) ? data[0].length : 0;
        this.data = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }
    
    public int get(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Matrix indices out of bounds");
        }
        return data[row][col];
    }
    public void set(int row, int col, int value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Matrix indices out of bounds");
        }
        data[row][col] = value;
    }

    public Matrix getSubMatrix(int startRow, int startCol, int numRows, int numCols) {
        if (startRow < 0 || startCol < 0 || 
            startRow + numRows > rows || startCol + numCols > cols) {
            throw new IndexOutOfBoundsException("Submatrix indices out of bounds");
        }
        
        Matrix subMatrix = new Matrix(numRows, numCols);
        
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                subMatrix.set(i, j, data[startRow + i][startCol + j]);
            }
        }
        
        return subMatrix;
    }
    public double calculateAverage() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
            }
        }
        return sum / (rows * cols);
    }
    public double calculateRegionAverage(int startRow, int startCol, int numRows, int numCols) {
        if (startRow < 0 || startCol < 0 || 
            startRow + numRows > rows || startCol + numCols > cols) {
            throw new IndexOutOfBoundsException("Region indices out of bounds");
        }
        
        double sum = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                sum += data[startRow + i][startCol + j];
            }
        }
        return sum / (numRows * numCols);
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
}