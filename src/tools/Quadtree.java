package tools;
import errorMeasurement.*;
import imageHandler.Image;

public class Quadtree {
    private final int method;
    private final double threshold;
    private final int minBlockSize;
    private int numNode;
    private Image image;
    private Node root;

    public Quadtree(){
        this.method = 0;
        this.threshold = 0;
        this.minBlockSize = 0;
        this.numNode = 0;
        this.image = null;
        this.root = null;
    }

    public Quadtree(int _method, double _threshold, int _minBlockSize, Image _image){
        this.method = _method;
        this.threshold = _threshold;
        this.minBlockSize = _minBlockSize;
        this.numNode = 0;
        this.image = _image;
        this.root = null;
    }

    public void construct(int[][] red, int[][] green, int[][] blue){
        System.err.println("h image " + image.getHeight());
        System.err.println("w image " + image.getWidth());
        Node quadtreeRoot = checker(red, green, blue, 0, 0, image.getHeight(), image.getWidth());
        this.root = quadtreeRoot;
    }

    public Node checker(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int h, int w){
        int avgRed = avgPixel(red, rowTL, colTL, h, w);
        int avgGreen = avgPixel(green, rowTL, colTL, h, w);
        int avgBlue = avgPixel(blue, rowTL, colTL, h, w);
        Node node = new Node(avgRed, avgGreen, avgBlue, false, rowTL, colTL, h, w);
        this.numNode ++;

        if (!isDivide(red, green, blue, rowTL, colTL, h, w)){ //stop dividing, current block becomes leaf
            node.setIsLeaf(true);
            return node;
        }

        Node nodeTL = checker(red, green, blue, rowTL, colTL, h/2, w/2);
        Node nodeTR = checker(red, green, blue, rowTL, colTL + w/2, h/2, w/2);
        Node nodeBL = checker(red, green, blue, rowTL + h/2, colTL, h/2, w/2);
        Node nodeBR = checker(red, green, blue, rowTL + h/2, colTL + w/2, h/2, w/2);

        node.setTopLeft(nodeTL);
        node.setTopRight(nodeTR);
        node.setBotLeft(nodeBL);
        node.setBotRight(nodeBR);
        
        return node;
    }

    public boolean isDivide(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, int h, int w){
        double blockArea = w * h;
        double subBlockArea = (w/2) * (h/2);

        // based on block size (bener ga < or <= --cek spek)?
        if (blockArea < minBlockSize || subBlockArea < minBlockSize){
            return false;
        }

        // based on error measurement method
        double value = 0;
        switch (method){
            case 1 -> {
                // variance
            }
            case 2 -> {
                // MAD
                value = MAD.computeMAD(red, green, blue, rowTL, colTL, h, w);
                break;
            }
            case 3 -> {
                // MPD
            }
            case 4 -> {
                // Entropy
                value = Entropy.computeEntropy(red, green, blue, rowTL, colTL, h, w);
                break;
            }
            case 5 -> {
                // SSIM (bonus)
            }
            default -> {
                // case dafault, didnt proceed
            }
        }

        return value >= threshold; //true if able to be divided
    }

    // might needed
    public int avgPixel(int[][] matrix, int rowTL, int colTL, int h, int w){
        if (rowTL < 0 || colTL < 0 || 
            rowTL + h > matrix.length || colTL + w > matrix[0].length) {
            System.out.println("row " + rowTL);
            System.out.println("col " + colTL);
            System.out.println("rowTL + h " + (rowTL + h));
            System.out.println("colTL + w " + (colTL + w));
            System.out.println("rows " + (matrix.length));
            System.out.println("cols " + (matrix[0].length));
            throw new IndexOutOfBoundsException("Region indices out of bounds");
        }
        
        int sum = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sum += matrix[rowTL + i][colTL + j];
            }
        }
        return (int) sum / (h * w);
    }

    public int getNumberOfNode(){
        return this.numNode;
    }

    public Image getImage(){
        return this.image;
    }

    public Node getRoot(){
        return this.root;
    }

    public int getDepth(Node root){
        if (root == null){
            return -1;
        }

        int heightTL = getDepth(root.getTopLeft());
        int heightTR = getDepth(root.getTopRight());
        int heightBL = getDepth(root.getBotLeft());
        int heightBR = getDepth(root.getBotRight());

        return findMax(heightTL, heightTR, heightBL, heightBR) + 1;
    }

    public int findMax(int hTL, int hTR, int hBL, int hBR){
        int max = hTL;
        if (hTR > max) { max = hTR; }
        if (hBL > max) { max = hBL; }
        if (hBR > max) { max = hBR; }
        return max;
    }
}
